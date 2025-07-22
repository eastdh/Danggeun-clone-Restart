package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.ImageTrade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageTradeRepository extends JpaRepository<ImageTrade, Long> {
  List<ImageTrade> findByTradeId(Long tradeId);
}
