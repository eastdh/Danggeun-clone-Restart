package io.github.restart.gmo_danggeun.repository.readonly;

import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface TradeImageListRepository extends ReadOnlyRepository<TradeImageList, Long> {
  List<TradeImageList> findAllByTradeId(@Param("trade_id")Long tradeId);
}
