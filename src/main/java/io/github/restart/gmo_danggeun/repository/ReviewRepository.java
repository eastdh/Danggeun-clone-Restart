package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findBySellerId(Long sellerId);
}