package io.github.restart.gmo_danggeun.dto.chat;

public class ChatRoomDetailDto {

  private Long chatRoomId;

  // 상대방 정보
  private String partnerNickname;
  private Double partnerTemperature;

  // 거래 정보
  private Long productId;
  private String productThumbnailUrl;
  private String productName;
  private Integer productPrice;
  private boolean isClosed; // 거래 완료 여부

  public ChatRoomDetailDto() {
  }

  public ChatRoomDetailDto(Long chatRoomId, String partnerNickname, Double partnerTemperature, Long productId,
      String productThumbnailUrl, String productName, Integer productPrice, boolean isClosed) {
    this.chatRoomId = chatRoomId;
    this.partnerNickname = partnerNickname;
    this.partnerTemperature = partnerTemperature;
    this.productId = productId;
    this.productThumbnailUrl = productThumbnailUrl;
    this.productName = productName;
    this.productPrice = productPrice;
    this.isClosed = isClosed;
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public String getPartnerNickname() {
    return partnerNickname;
  }

  public void setPartnerNickname(String partnerNickname) {
    this.partnerNickname = partnerNickname;
  }

  public Double getPartnerTemperature() {
    return partnerTemperature;
  }

  public void setPartnerTemperature(Double partnerTemperature) {
    this.partnerTemperature = partnerTemperature;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductThumbnailUrl() {
    return productThumbnailUrl;
  }

  public void setProductThumbnailUrl(String productThumbnailUrl) {
    this.productThumbnailUrl = productThumbnailUrl;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Integer getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(Integer productPrice) {
    this.productPrice = productPrice;
  }

  public boolean isClosed() {
    return isClosed;
  }

  public void setClosed(boolean closed) {
    isClosed = closed;
  }
}
