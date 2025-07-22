package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 판매자 리뷰만 조회
    List<Review> findBySellerId(Long sellerId);

    // 특정 거래에서 특정 유저(buyer/seller)가 이미 리뷰 작성했는지 확인
    Optional<Review> findByTradeIdAndBuyerIdOrTradeIdAndSellerId(Long tradeId, Long buyerId,
                                                                 Long tradeId2, Long sellerId);

    @Query("SELECT r FROM Review r " +
            "WHERE (r.trade.id = :tradeId AND r.buyer.id = :userId) " +
            "   OR (r.trade.id = :tradeId AND r.seller.id = :userId)")
    Optional<Review> findByTradeIdAndUserId(
            @Param("tradeId") Long tradeId,
            @Param("userId") Long userId);

    // 특정 유저가 buyer 또는 seller로 참여한 모든 리뷰 조회
    List<Review> findByBuyerIdOrSellerId(Long buyerId, Long sellerId);
}