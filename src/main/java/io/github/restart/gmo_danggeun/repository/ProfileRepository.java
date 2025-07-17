package io.github.restart.gmo_danggeun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.restart.gmo_danggeun.entity.User;

import java.util.List;

public interface ProfileRepository extends JpaRepository<User, Long> {

    @Query(value = """
        SELECT
            u.id AS userId,
            u.nickname AS nickname,
            u.location AS location,
            u.manner_score AS mannerScore,

            r.id AS reviewId,
            r.buyer_id AS buyerId,
            r.seller_id AS sellerId,
            r.content AS content,
            r.rating AS rating,
            r.created_at AS reviewCreatedAt,
            CASE WHEN r.seller_id = :userId THEN TRUE ELSE FALSE END AS isSellerWriter,

            rr.review_category_id AS reviewCategoryId,
            rc.name AS reviewCategoryName,
            rr.review_target AS reviewTarget,

            t.id AS tradeId,
            t.title AS title,
            t.price AS price,
            t.status AS status,
            t.created_at AS tradeCreatedAt,

            l.trade_id AS likedTradeId

        FROM "user" u
        LEFT JOIN review r ON u.id = r.seller_id OR u.id = r.buyer_id
        LEFT JOIN review_review_category rr ON r.id = rr.review_id
        LEFT JOIN review_category rc ON rr.review_category_id = rc.id
        LEFT JOIN trade t ON u.id = t.user_id
        LEFT JOIN "like" l ON u.id = l.user_id

        WHERE u.id = :userId
        """, nativeQuery = true)
    List<Object[]> findRawProfileDataByUserId(@Param("userId") Long userId);
}