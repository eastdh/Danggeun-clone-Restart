package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.config.PageConfig;
import io.github.restart.gmo_danggeun.dto.FilterDto;
import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import io.github.restart.gmo_danggeun.service.TradeService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TradeController {

  private TradeService tradeService;

  public TradeController(TradeService tradeService) {
    this.tradeService = tradeService;
  }

  @GetMapping("/trade")
  public String trade(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = true, defaultValue = "서울특별시 종로구") String location,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String category,
      @RequestParam(name = "price", required = false) String priceRange,
      Model model) {

    Integer priceLowLimit = null;
    Integer priceHighLimit = null;

    if (priceRange != null) {
      priceLowLimit = Integer.parseInt(priceRange.split("_")[0]);
      priceHighLimit = Integer.parseInt(priceRange.split("_")[1]);
    }

    Pageable pageable = PageRequest.of(page, PageConfig.TRADELIST_PAGE_SIZE);
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
  public String tradeDetail(@PathVariable Long id, Model model) {
    Optional<TradeDetail> trade = tradeService.findById(id);
    if (!trade.isEmpty()) {
      TradeDetail tradeDetail = trade.get();
      Pageable userTradePageable = PageRequest.of(0, PageConfig.TRADEDETAIL_USER_TRADE_PAGE_SIZE);
      Pageable categoryTradePageable = PageRequest.of(0, PageConfig.TRADEDETAIL_CATEGORY_TRADE_PAGE_SIZE);

      Page<TradeList> sellerTrades = tradeService.findAllByUserId(tradeDetail.getUserId(), userTradePageable);
      Page<TradeList> categoryTrades = tradeService.searchTrades("", tradeDetail.getLocation(), tradeDetail.getCategoryName(), null,
          null, null, categoryTradePageable);

      List<TradeImageList> images = tradeService.findAllImageById(tradeDetail.getTradeId());

      model.addAttribute("trade", tradeDetail);
      model.addAttribute("images", images);
      model.addAttribute("userTrade", sellerTrades);
      model.addAttribute("categoryTrade", categoryTrades);
      return "trade/trade_post";
    } else {
      model.addAttribute("error", "거래글 없음");
      // Todo : edit redirect to error page
      return "trade/trade_post";
    }
  }

  @GetMapping("/trade/new")
  public String tradeWrite() {
    return "trade/trade_write";
  }

  @GetMapping("/trade/{id}/edit")
  public String tradeEdit(@PathVariable Long id, Model model) {
    Optional<TradeDetail> trade = tradeService.findById(id);
    model.addAttribute("trade", trade);
    return "trade/trade_edit";
  }
}
