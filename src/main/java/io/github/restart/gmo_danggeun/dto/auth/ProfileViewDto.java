package io.github.restart.gmo_danggeun.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
public class ProfileViewDto {

    // 사용자 기본 정보
    private Long userId;
    private String nickname;
    private String location;
    private BigDecimal mannerScore;

    // 매너 평가 항목 (ex. “시간약속을 잘 지켜요” 10명)
    private List<MannerFeedbackDto> mannerFeedbacks;

    // 받은 후기
    private List<ReviewDto> reviews;

    // 판매한 상품
    private List<TradeDto> sales;

    // 찜한 상품
    private List<TradeDto> likedTrades;

    public ProfileViewDto(Long aLong, String s, String s1, BigDecimal bigDecimal, Long aLong1, Long aLong2, Long aLong3, String s2, Integer integer, Timestamp timestamp, boolean b, Long aLong4, String s3, String s4, Long aLong5, String s5, Integer integer1, String s6, Timestamp timestamp1, Long aLong6) {
    }

    public ProfileViewDto() {

    }

    // 생성자, getter/setter 생략 가능 (lombok 사용 가능)

    // 내부 DTO 클래스들
    @Getter
    @Setter
    public static class MannerFeedbackDto {
        private String categoryName;
        private Long count;

    }
    @Getter
    @Setter
    public static class ReviewDto {
        private Long reviewId;
        private String role; // 구매자 or 판매자
        private String reviewerNickname;
        private String location;
        private String content;
        private Integer rating;
        private Timestamp createdAt;

        public void setRating(Short rating) {
        }
    }
    @Getter
    @Setter
    public static class TradeDto {
        private Long tradeId;
        private String title;
        private Integer price;
        private String status;
        private String thumbnailUrl;
        private Timestamp createdAt;
    }
}