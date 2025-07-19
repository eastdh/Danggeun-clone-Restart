package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.config.TradeConfig;
import io.github.restart.gmo_danggeun.dto.trade.FilterDto;
import io.github.restart.gmo_danggeun.dto.trade.TradeDto;
import io.github.restart.gmo_danggeun.dto.trade.TradeEditDto;
import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import io.github.restart.gmo_danggeun.service.TradeService;
import io.github.restart.gmo_danggeun.util.UserMannerUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TradeController {

  private final TradeService tradeService;

  public TradeController(TradeService tradeService) {
    this.tradeService = tradeService;
  }

  @GetMapping("/trade")
  public String trade(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String location,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String category,
      @RequestParam(name = "price", required = false) String priceRange,
      RedirectAttributes redirectAttributes,
      @AuthenticationPrincipal CustomUserDetails principal,
      Model model) {

    User user = (principal != null) ? principal.getUser() : null;

    String resolvedLocation = location;

    if (resolvedLocation == null || resolvedLocation.isBlank()) {
      if (user == null) {
        resolvedLocation = TradeConfig.DEFAULT_LOCATION;
      } else {
        resolvedLocation = user.getLocation();
        if (resolvedLocation == null || resolvedLocation.isBlank()) {
          resolvedLocation = TradeConfig.DEFAULT_LOCATION;
        }
      }
      redirectAttributes.addAttribute("location", resolvedLocation);
      if (page != 0) redirectAttributes.addAttribute("page", page);
      if (keyword != null) redirectAttributes.addAttribute("keyword", keyword);
      if (status != null) redirectAttributes.addAttribute("status", status);
      if (category != null) redirectAttributes.addAttribute("category", category);
      if (priceRange != null) redirectAttributes.addAttribute("price", priceRange);
      return "redirect:/trade";
    }

    Integer priceLowLimit = null;
    Integer priceHighLimit = null;

    if (priceRange != null) {
      priceLowLimit = Integer.parseInt(priceRange.split("_")[0]);
      priceHighLimit = Integer.parseInt(priceRange.split("_")[1]);
    }

    Pageable pageable = PageRequest.of(page, TradeConfig.TRADELIST_PAGE_SIZE);
    Page<TradeList> tradePage = tradeService.searchTrades(keyword, location, category, priceLowLimit, priceHighLimit, status, pageable);
    List<Category> categories = tradeService.findAllCategories();

    FilterDto filter = new FilterDto(category, priceLowLimit, priceHighLimit, status);

    model.addAttribute("trades", tradePage);
    model.addAttribute("categories", categories);
    model.addAttribute("page", page);
    model.addAttribute("keyword", keyword);
    model.addAttribute("location", location);
    model.addAttribute("filters", filter);
    return "trade/trade";
  }

  @GetMapping("/trade/{id}")
  public String tradeDetail(
      @PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails principal,
      Model model) {
    Optional<TradeDetail> trade = tradeService.findTradeViewById(id);
    Long userId = (principal != null) ? principal.getUser().getId() : null;

    if (!trade.isEmpty() || !trade.get().getHidden()) {
      TradeDetail tradeDetail = trade.get();
      Pageable userTradePageable = PageRequest.of(0, TradeConfig.TRADEDETAIL_USER_TRADE_PAGE_SIZE);
      Pageable categoryTradePageable = PageRequest.of(0, TradeConfig.TRADEDETAIL_CATEGORY_TRADE_PAGE_SIZE);

      Page<TradeList> sellerTrades = tradeService.findAllByUserId(tradeDetail.getUserId(), userTradePageable);
      Page<TradeList> categoryTrades = tradeService.searchTrades(tradeDetail.getTitle(), tradeDetail.getLocation(),
          tradeDetail.getCategoryName(), null,
          null, "available", categoryTradePageable);

      List<TradeImageList> images = tradeService.findAllImageById(tradeDetail.getTradeId());

      String emojiFileName = UserMannerUtil.setEmoji(tradeDetail.getMannerScore());

      if (userId != null && userId.equals(tradeDetail.getUserId())) {
        model.addAttribute("owner", "true");
      }

      model.addAttribute("trade", tradeDetail);
      model.addAttribute("images", images);
      model.addAttribute("emojiFileName", emojiFileName);
      model.addAttribute("userTrades", sellerTrades);
      model.addAttribute("categoryTrades", categoryTrades);
      return "trade/trade_post";
    } else {
      model.addAttribute("error", "거래글 없음");
      // Todo : edit redirect to error page
      return "trade/trade_post";
    }
  }

  @GetMapping("/trade/new")
  public String tradeWritePage(Model model) {
    List<Category> categories = tradeService.findAllCategories();
    model.addAttribute("categories", categories);
    model.addAttribute("tradeDto", new TradeDto());
    return "trade/trade_write";
  }

  @GetMapping("/trade/{id}/edit")
  public String tradeEditPage(
      @PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails principal,
      Model model) {
    Optional<Trade> optionalTrade = tradeService.findById(id);
    User user = (principal != null) ? principal.getUser() : null;

    if (!optionalTrade.isEmpty()) {
      Trade trade = optionalTrade.get();

      if (user.getId().equals(trade.getUser().getId())) {
        TradeEditDto tradeEditDto = trade.entityToEditDto();
        model.addAttribute("tradeEditDto", tradeEditDto);

        List<Category> categories = tradeService.findAllCategories();
        model.addAttribute("categories", categories);
        return "trade/trade_edit";
      }
      // Todo : edit redirect to error page
      return "error";
    } else {
      model.addAttribute("error", "거래글 없음");
      // Todo : edit redirect to error page
      return "error";
    }
  }

  @PostMapping("/trade/new")
  @Transactional
  public String writeTrade(
      @Valid @ModelAttribute TradeDto tradeDto,
      BindingResult bindingResult,
      @AuthenticationPrincipal CustomUserDetails principal,
      Model model
  ) {
    User currentUser = (principal != null) ? principal.getUser() : null;

    List<Category> categories = tradeService.findAllCategories();
    model.addAttribute("categories", categories);
    model.addAttribute("tradeDto", tradeDto);

    if (tradeDto == null)
      return "trade/trade_write";

    if (bindingResult.hasErrors())
      return "trade/trade_write";

    // 이미지 파일 저장
    // Todo : add image upload with Amazon S3

    Category category = categories.stream()
        .filter(c -> c.getId().equals(tradeDto.getCategoryId()))
        .findAny()
        .orElseGet(() ->
          categories.stream()
              .filter(c -> c.getId().equals(19L))
              .findAny()
              .orElse(null)
        );

    Trade savedTrade = tradeService.save(currentUser, tradeDto, category);
    // Todo : add error page
    if (savedTrade == null) return "error";
    return "redirect:/trade/" + savedTrade.getId();
  }

  @PostMapping("/trade/{id}/edit")
  public String editTrade(
      @Valid @ModelAttribute TradeEditDto tradeEditDto,
      @PathVariable Long id,
      BindingResult bindingResult,
      @AuthenticationPrincipal CustomUserDetails principal,
      Model model
  ) {
    User currentUser = (principal != null) ? principal.getUser() : null;

    Trade original = tradeService.findById(id).orElseThrow(()->new EntityNotFoundException("trade not found :" + id));

    // Todo : add error page
    if (!id.equals(tradeEditDto.getId())) return "error";
    if (!original.getUser().getId().equals(currentUser.getId()))
      return "error";

    List<Category> categories = tradeService.findAllCategories();
    model.addAttribute("categories", categories);
    model.addAttribute("tradeEditDto", tradeEditDto);

    if (tradeEditDto == null)
      return "trade/trade_write";

    if (bindingResult.hasErrors())
      return "trade/trade_write";

    // 이미지 파일 저장
    // Todo : add image upload with Amazon S3

    Category category = categories.stream()
        .filter(c -> c.getId().equals(tradeEditDto.getCategoryId()))
        .findAny()
        .orElse(null);

    Trade savedTrade = tradeService.edit(original, tradeEditDto, category);
    // Todo : add error page
    if (savedTrade == null) return "error";
    return "redirect:/trade/" + savedTrade.getId();
  }
}
