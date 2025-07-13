package io.github.restart.gmo_danggeun.dto;

public class ImageTradeDto {
    private Long tradeId;

    private Long imageId;

    public ImageTradeDto() {
    }

    public ImageTradeDto(Long tradeId, Long imageId) {
        this.tradeId = tradeId;
        this.imageId = imageId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

}