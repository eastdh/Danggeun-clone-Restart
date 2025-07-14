package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.trade.FilterDTO;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.service.TradeService;
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
      @RequestParam(required = true) String location,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) int priceLowLimit,
      @RequestParam(required = false) int priceHighLimit,
      Model model) {

    Pageable pageable = PageRequest.of(page, 10);
    Page<Trade> tradePage = tradeService.searchTrades(keyword, location, category, priceLowLimit, priceHighLimit, pageable);

    FilterDTO filterDTO = new FilterDTO(location, category, priceLowLimit, priceHighLimit);

    model.addAttribute("trade", tradePage);
    model.addAttribute("keyword", keyword);
    model.addAttribute("filter", filterDTO);
    return "trade/trade";
  }

  @GetMapping("/trade/{id}")
  public String tradeDetail(@PathVariable Long id, Model model) {
    Optional<Trade> trade = tradeService.findById(id);
    model.addAttribute("trade", trade);
    return "trade/trade_post";
  }

  @GetMapping("/trade/new")
  public String tradeWrite() {
    return "trade/trade_write";
  }

  @GetMapping("/trade/{id}/edit")
  public String tradeEdit(@PathVariable Long id, Model model) {
    Optional<Trade> trade = tradeService.findById(id);
    model.addAttribute("trade", trade);
    return "trade/trade_edit";
  }
}
