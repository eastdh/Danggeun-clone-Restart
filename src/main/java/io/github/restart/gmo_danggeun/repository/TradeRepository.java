package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByUserId(Long userId);
}