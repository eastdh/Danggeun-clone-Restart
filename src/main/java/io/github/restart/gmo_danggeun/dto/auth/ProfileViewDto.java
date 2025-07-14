package io.github.restart.gmo_danggeun.dto.auth;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProfileViewDto {
    private Long userId;
    private String nickname;
    private BigDecimal mannerScore;

    private Long reviewId;
    private Long buyerId;
    private Long sellerId;
    private String content;
    private Integer rating;
    private Timestamp reviewCreatedAt;

    private Long reviewCategoryId;
    private String reviewCategoryName;
    private String reviewTarget;

    private Long tradeId;
    private Long tradeUserId;
    private String title;
    private Integer price;
    private String status;
    private Timestamp tradeCreatedAt;

    private Long likeUserId;
    private Long likedTradeId;

    public ProfileViewDto() {
    }

    public ProfileViewDto(Long userId, String nickname, BigDecimal mannerScore,
                          Long reviewId, Long buyerId, Long sellerId, String content, Integer rating, Timestamp reviewCreatedAt,
                          Long reviewCategoryId, String reviewCategoryName, String reviewTarget,
                          Long tradeId, Long tradeUserId, String title, Integer price, String status, Timestamp tradeCreatedAt,
                          Long likeUserId, Long likedTradeId) {
        this.userId = userId;
        this.nickname = nickname;
        this.mannerScore = mannerScore;
        this.reviewId = reviewId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.content = content;
        this.rating = rating;
        this.reviewCreatedAt = reviewCreatedAt;
        this.reviewCategoryId = reviewCategoryId;
        this.reviewCategoryName = reviewCategoryName;
        this.reviewTarget = reviewTarget;
        this.tradeId = tradeId;
        this.tradeUserId = tradeUserId;
        this.title = title;
        this.price = price;
        this.status = status;
        this.tradeCreatedAt = tradeCreatedAt;
        this.likeUserId = likeUserId;
        this.likedTradeId = likedTradeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BigDecimal getMannerScore() {
        return mannerScore;
    }

    public void setMannerScore(BigDecimal mannerScore) {
        this.mannerScore = mannerScore;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Timestamp getReviewCreatedAt() {
        return reviewCreatedAt;
    }

    public void setReviewCreatedAt(Timestamp reviewCreatedAt) {
        this.reviewCreatedAt = reviewCreatedAt;
    }

    public Long getReviewCategoryId() {
        return reviewCategoryId;
    }

    public void setReviewCategoryId(Long reviewCategoryId) {
        this.reviewCategoryId = reviewCategoryId;
    }

    public String getReviewCategoryName() {
        return reviewCategoryName;
    }

    public void setReviewCategoryName(String reviewCategoryName) {
        this.reviewCategoryName = reviewCategoryName;
    }

    public String getReviewTarget() {
        return reviewTarget;
    }

    public void setReviewTarget(String reviewTarget) {
        this.reviewTarget = reviewTarget;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getTradeUserId() {
        return tradeUserId;
    }

    public void setTradeUserId(Long tradeUserId) {
        this.tradeUserId = tradeUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTradeCreatedAt() {
        return tradeCreatedAt;
    }

    public void setTradeCreatedAt(Timestamp tradeCreatedAt) {
        this.tradeCreatedAt = tradeCreatedAt;
    }

    public Long getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(Long likeUserId) {
        this.likeUserId = likeUserId;
    }

    public Long getLikedTradeId() {
        return likedTradeId;
    }

    public void setLikedTradeId(Long likedTradeId) {
        this.likedTradeId = likedTradeId;
    }

    @Override
    public String toString() {
        return "ProfileViewDto{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", mannerScore=" + mannerScore +
                ", reviewId=" + reviewId +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", reviewCreatedAt=" + reviewCreatedAt +
                ", reviewCategoryId=" + reviewCategoryId +
                ", reviewCategoryName='" + reviewCategoryName + '\'' +
                ", reviewTarget='" + reviewTarget + '\'' +
                ", tradeId=" + tradeId +
                ", tradeUserId=" + tradeUserId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", tradeCreatedAt=" + tradeCreatedAt +
                ", likeUserId=" + likeUserId +
                ", likedTradeId=" + likedTradeId +
                '}';
    }
}