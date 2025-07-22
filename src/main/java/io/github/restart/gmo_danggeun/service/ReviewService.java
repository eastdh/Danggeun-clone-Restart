package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.dto.review.ReviewDto;
import io.github.restart.gmo_danggeun.entity.Review;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    Review createReview(Long tradeId, Long writerId, Long partnerId,
                        String content, Short rating,
                        boolean isSellerWriter, List<Long> categoryIds);

    List<Review> getReviewsForSeller(Long sellerId);
    List<Review> getReviewsForBuyer(Long buyerId);
    Review getReviewById(Long reviewId);
    double getAverageRatingForUser(Long userId);
    void deleteReview(Long reviewId, Long requesterId);
    ReviewDto convertToDto(Review review);
}
