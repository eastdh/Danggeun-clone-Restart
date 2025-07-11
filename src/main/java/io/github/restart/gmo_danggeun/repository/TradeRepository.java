package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Trade;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
//  @Query("SELECT t FROM Trade t WHERE " +
//      "(LOWER(t.location) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
//      "ORDER BY c.updatedAt DESC")
//  Page<Trade> findAllByLocation(@Param("location") String location, Pageable pageable);
//
//  Page<Trade> findAllByFilters(
//      @Param("keyword") String keyword,
//      @Param("location") String location,
//      @Param("category") String category,
//      @Param("priceLowLimit") int priceLowLimit,
//      @Param("priceHighLimit") int priceHighLimit,
//      Pageable pageable);
//
//  List<Trade> findAllByUserId(@Param("userId") Long userId);
}
