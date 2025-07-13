package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Trade;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}
