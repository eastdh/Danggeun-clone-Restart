package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.entity.Trade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeService {
  Page<Trade> findAllByLocation(String location, Pageable pageable);
  Page<Trade> searchTrades(String keyword, String location, String category, int priceLowLimit, int priceHighLimit, Pageable pageable);

  Optional<Trade> findById(Long id);

  List<Trade> findAllByUserId(Long userId);
}
