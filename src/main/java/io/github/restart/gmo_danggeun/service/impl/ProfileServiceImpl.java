package io.github.restart.gmo_danggeun.service.impl;

import io.github.restart.gmo_danggeun.dto.auth.ProfileViewDto;
import io.github.restart.gmo_danggeun.dto.auth.ProfileViewDto.MannerFeedbackDto;
import io.github.restart.gmo_danggeun.dto.auth.ProfileViewDto.ReviewDto;
import io.github.restart.gmo_danggeun.dto.auth.ProfileViewDto.TradeDto;
import io.github.restart.gmo_danggeun.entity.*;
import io.github.restart.gmo_danggeun.repository.*;
import io.github.restart.gmo_danggeun.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReviewCategoryRepository reviewReviewCategoryRepository;
    private final TradeRepository tradeRepository;
    private final LikeRepository likeRepository;

    @Override
    public ProfileViewDto getProfileDataByUserId(Long userId) {
        // 1. 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 2. 후기 조회 (판매자 기준)
        List<Review> reviews = reviewRepository.findBySellerId(userId);

        // 2-1. 각 리뷰에 해당하는 카테고리 조회 및 매핑
        List<ReviewDto> reviewDtos = new ArrayList<>();
        Map<String, Long> feedbackCountMap = new HashMap<>();

        for (Review review : reviews) {
            User buyer = review.getBuyer();

            List<ReviewReviewCategory> rrCategories = reviewReviewCategoryRepository.findByReviewId(review.getId());

            for (ReviewReviewCategory rr : rrCategories) {
                String categoryName = rr.getReviewCategory().getName();
                feedbackCountMap.put(categoryName, feedbackCountMap.getOrDefault(categoryName, 0L) + 1);
            }

            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(review.getId());
            reviewDto.setRole("구매자"); // 항상 구매자 기준
            reviewDto.setReviewerNickname(buyer.getNickname());
            reviewDto.setLocation(buyer.getLocation());
            reviewDto.setContent(review.getContent());
            reviewDto.setRating(review.getRating());
            reviewDto.setCreatedAt(Timestamp.valueOf(review.getCreatedAt()));

            reviewDtos.add(reviewDto);
        }

        // 3. 매너 평가 DTO 생성 - 정렬 후 상위 3개만 추출
        List<MannerFeedbackDto> mannerFeedbacks = feedbackCountMap.entrySet().stream()
                .map(entry -> {
                    MannerFeedbackDto dto = new MannerFeedbackDto();
                    dto.setCategoryName(entry.getKey());
                    dto.setCount(entry.getValue());
                    return dto;
                })
                .sorted(Comparator.comparingLong(MannerFeedbackDto::getCount).reversed())
                .limit(3)
                .collect(Collectors.toList());

        // 4. 사용자가 판매한 거래글
        List<TradeDto> sales = tradeRepository.findByUserId(userId).stream()
                .map(trade -> {
                    TradeDto dto = new TradeDto();
                    dto.setTradeId(trade.getId());
                    dto.setTitle(trade.getTitle());
                    dto.setPrice(trade.getPrice());
                    dto.setStatus(trade.getStatus());
                    dto.setThumbnailUrl("/assets/icon/default_product_img.svg"); // 썸네일 추후 구현
                    dto.setCreatedAt(Timestamp.valueOf(trade.getCreatedAt()));
                    return dto;
                })
                .collect(Collectors.toList());

        // 5. 찜한 상품 (trade_id만 있음)
        List<TradeDto> likedTrades = likeRepository.findByUserId(userId).stream()
                .map(like -> like.getTrade())
                .map(trade -> {
                    TradeDto dto = new TradeDto();
                    dto.setTradeId(trade.getId());
                    dto.setTitle(trade.getTitle());
                    dto.setPrice(trade.getPrice());
                    dto.setStatus(trade.getStatus());
                    dto.setThumbnailUrl("/assets/icon/default_product_img.svg"); // 썸네일 추후 구현
                    dto.setCreatedAt(Timestamp.valueOf(trade.getCreatedAt()));
                    return dto;
                })
                .collect(Collectors.toList());

        // 6. 최종 ProfileViewDto 생성
        ProfileViewDto profile = new ProfileViewDto();
        profile.setUserId(user.getId());
        profile.setNickname(user.getNickname());
        profile.setLocation(user.getLocation());
        profile.setMannerScore(user.getMannerScore());
        profile.setMannerFeedbacks(mannerFeedbacks);
        profile.setReviews(reviewDtos);
        profile.setSales(sales);
        profile.setLikedTrades(likedTrades);

        return profile;
    }
    @Override
    @Transactional
    public void updateNickname(Long userId, String newNickname) {
        // 닉네임 중복 체크 먼저
        if (userRepository.existsByNickname(newNickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setNickname(newNickname);
        userRepository.save(user); // @Transactional이 있으면 save 생략 가능
    }

    @Override
    public boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}