package io.github.restart.gmo_danggeun.service.impl;

import io.github.restart.gmo_danggeun.dto.review.ReviewDto;
import io.github.restart.gmo_danggeun.entity.*;
import io.github.restart.gmo_danggeun.repository.*;
import io.github.restart.gmo_danggeun.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewCategoryRepository reviewCategoryRepository;
    private final ReviewReviewCategoryRepository reviewReviewCategoryRepository;
    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Transactional
    @Override
    public Review createReview(Long tradeId, Long writerId, Long partnerId,
                               String content, Short rating,
                               boolean isSellerWriter, List<Long> categoryIds) {

        // 거래, 작성자, 상대방 불러오기
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("거래를 찾을 수 없습니다."));
        User writer = userRepository.findById(writerId)
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));
        User partner = userRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("상대방을 찾을 수 없습니다."));

        // buyer/seller 명확하게 설정
        User buyer = isSellerWriter ? partner : writer;
        User seller = isSellerWriter ? writer : partner;

        log.info("[리뷰 작성] writer={}, partner={}, buyer={}, seller={}, rating={}",
                writer.getId(), partner.getId(), buyer.getId(), seller.getId(), rating);

        // 리뷰 엔티티 생성
        Review review = new Review();
        review.setTrade(trade);
        review.setBuyer(buyer);
        review.setSeller(seller);
        review.setContent(content);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        review.setSellerWriter(isSellerWriter);

        Review savedReview = reviewRepository.save(review);

        // 카테고리 연관 저장
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                ReviewCategory category = reviewCategoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("리뷰 카테고리를 찾을 수 없습니다."));
                ReviewReviewCategory relation =
                        new ReviewReviewCategory(savedReview, category, category.getReviewTarget());
                reviewReviewCategoryRepository.save(relation);
            }
        }

        // 매너온도 갱신 (리뷰 받은 사람)
        updateMannerScore(partner, rating);

        return savedReview;
    }

    private void updateMannerScore(User user, Short rating) {
        if (user.getMannerScore() == null) {
            user.setMannerScore(BigDecimal.ZERO);
        }

        // 0~10 기준, 10=+1℃, 0=-1℃
        BigDecimal delta = BigDecimal.valueOf((rating - 5) * 0.2);
        BigDecimal updated = user.getMannerScore().add(delta);

        if (updated.compareTo(BigDecimal.ZERO) < 0) updated = BigDecimal.ZERO;
        if (updated.compareTo(BigDecimal.valueOf(99)) > 0) updated = BigDecimal.valueOf(99);

        user.setMannerScore(updated);
        userRepository.saveAndFlush(user);

        log.info("[매너온도] 대상={}, 별점={}, delta={}, 최종={}", user.getId(), rating, delta, updated);
    }

    @Override
    public List<Review> getReviewsForSeller(Long sellerId) {
        return reviewRepository.findBySellerId(sellerId);
    }

    @Override
    public List<Review> getReviewsForBuyer(Long buyerId) {
        return reviewRepository.findAll()
                .stream()
                .filter(r -> r.getBuyer().getId().equals(buyerId))
                .toList();
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
    }

    @Override
    public double getAverageRatingForUser(Long userId) {
        List<Review> reviews = reviewRepository.findBySellerId(userId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, Long requesterId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (!review.getBuyer().getId().equals(requesterId) && !review.getSeller().getId().equals(requesterId)) {
            throw new SecurityException("리뷰 작성자만 삭제할 수 있습니다.");
        }
        reviewRepository.delete(review);
    }

    @Override
    public ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setTradeId(review.getTrade().getId());
        dto.setBuyerId(review.getBuyer().getId());
        dto.setSellerId(review.getSeller().getId());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt().toString());
        dto.setUpdatedAt(review.getUpdatedAt() != null ? review.getUpdatedAt().toString() : null);
        dto.setIsSellerWriter(review.getSellerWriter());

        List<String> categoryNames = reviewReviewCategoryRepository.findByReviewId(review.getId())
                .stream()
                .map(rel -> rel.getReviewCategory().getName())
                .collect(Collectors.toList());
        dto.setCategories(categoryNames);

        return dto;
    }
}
