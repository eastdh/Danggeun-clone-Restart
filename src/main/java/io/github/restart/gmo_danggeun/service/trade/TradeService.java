package io.github.restart.gmo_danggeun.service.trade;

import io.github.restart.gmo_danggeun.dto.trade.TradeDto;
import io.github.restart.gmo_danggeun.dto.trade.TradeEditDto;
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

  Trade save(User user, TradeDto tradeDto, Category category);

  Trade edit(Trade trade, TradeEditDto tradeEditDto, Category category);

  void delete(Long id);

  void confirmTrade(Long tradeId);

  String bumpTrade(Long id, Long userId);

  String alterStatus(Long id, String status);

  String addLike(Long id, User user);

  String removeLike(Long id, Long userId);

  String getEmojiFileName(TradeDetail tradeDetail);

  String getStatusText(TradeDetail tradeDetail);
}
