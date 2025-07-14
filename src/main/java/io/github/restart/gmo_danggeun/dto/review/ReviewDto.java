package io.github.restart.gmo_danggeun.dto.review;

public class ReviewDto {

    private Long id;

    private Long tradeId;

    private Long buyerId;

    private Long sellerId;

    private String content;

    private Short rating;

    private String createdAt;

    private String updatedAt;

    private Boolean isSellerWriter;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
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

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsSellerWriter() {
        return isSellerWriter;
    }

    public void setIsSellerWriter(Boolean isSellerWriter) {
        this.isSellerWriter = isSellerWriter;
    }

}