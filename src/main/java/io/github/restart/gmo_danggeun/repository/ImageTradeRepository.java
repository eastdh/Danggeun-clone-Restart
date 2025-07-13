package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.ImageTrade;
import io.github.restart.gmo_danggeun.entity.id.ImageTradeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ImageTradeRepository extends JpaRepository<ImageTrade, ImageTradeId> {
  List<ImageTrade> findByIdTradeId(@Param("trade_id")Long tradeId);
}
