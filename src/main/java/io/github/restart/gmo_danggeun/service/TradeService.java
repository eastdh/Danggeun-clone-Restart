package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.dto.trade.TradeDto;
import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeService {
  Page<TradeList> searchTrades(String keyword, String location,
      String category, Integer priceLowLimit, Integer priceHighLimit,
      String status, Pageable pageable);

  Optional<Trade> findById(Long id);

  Optional<TradeDetail> findTradeViewById(Long id);

  List<TradeImageList> findAllImageById(Long id);

  Page<TradeList> findAllByUserId(Long userId, Pageable pageable);

  Optional<Category> findCategoryById(Long id);

  List<Category> findAllCategories();

  Trade save(User user, TradeDto tradeDto, Category category);

  Trade edit(Trade trade, TradeDto tradeDto, Category category);

  void delete(Long id);
}
