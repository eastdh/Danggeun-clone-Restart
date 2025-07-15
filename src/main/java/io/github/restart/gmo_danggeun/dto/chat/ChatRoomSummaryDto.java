package io.github.restart.gmo_danggeun.dto.chat;

public class ChatRoomSummaryDto {

  // 내 정보
  private Long meId;
  private String meNickname;

  // 채팅방 및 거래글 식별자
  private Long chatRoomId;
  private Long tradeId;

  // 거래 정보
  private String productName;
  private Integer productPrice;
  private String tradeStatus;
  private String productThumbnailUrl;

  // 상대 정보
  private Long partnerId;
  private String partnerNickname;
  private String partnerLocation;
  private Double partnerTemperature;

  // 메시지 요약
  private String lastMessageContent;
  private String lastMessageTime;

  // 안 읽은 메시지 수
  private int unreadCount;

  public ChatRoomSummaryDto() {
  }

  public ChatRoomSummaryDto(Long meId, String meNickname, Long chatRoomId, Long tradeId, String productName,
      Integer productPrice, String tradeStatus, String productThumbnailUrl, Long partnerId, String partnerNickname,
      String partnerLocation, Double partnerTemperature, String lastMessageContent, String lastMessageTime,
      int unreadCount) {
    this.meId = meId;
    this.meNickname = meNickname;
    this.chatRoomId = chatRoomId;
    this.tradeId = tradeId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.tradeStatus = tradeStatus;
    this.productThumbnailUrl = productThumbnailUrl;
    this.partnerId = partnerId;
    this.partnerNickname = partnerNickname;
    this.partnerLocation = partnerLocation;
    this.partnerTemperature = partnerTemperature;
    this.lastMessageContent = lastMessageContent;
    this.lastMessageTime = lastMessageTime;
    this.unreadCount = unreadCount;
  }

  public Long getMeId() {
    return meId;
  }

  public void setMeId(Long meId) {
    this.meId = meId;
  }

  public String getMeNickname() {
    return meNickname;
  }

  public void setMeNickname(String meNickname) {
    this.meNickname = meNickname;
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public void setTradeId(Long tradeId) {
    this.tradeId = tradeId;
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

  public String getTradeStatus() {
    return tradeStatus;
  }

  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }

  public String getProductThumbnailUrl() {
    return productThumbnailUrl;
  }

  public void setProductThumbnailUrl(String productThumbnailUrl) {
    this.productThumbnailUrl = productThumbnailUrl;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public String getPartnerNickname() {
    return partnerNickname;
  }

  public void setPartnerNickname(String partnerNickname) {
    this.partnerNickname = partnerNickname;
  }

  public String getPartnerLocation() {
    return partnerLocation;
  }

  public void setPartnerLocation(String partnerLocation) {
    this.partnerLocation = partnerLocation;
  }

  public Double getPartnerTemperature() {
    return partnerTemperature;
  }

  public void setPartnerTemperature(Double partnerTemperature) {
    this.partnerTemperature = partnerTemperature;
  }

  public String getLastMessageContent() {
    return lastMessageContent;
  }

  public void setLastMessageContent(String lastMessageContent) {
    this.lastMessageContent = lastMessageContent;
  }

  public String getLastMessageTime() {
    return lastMessageTime;
  }

  public void setLastMessageTime(String lastMessageTime) {
    this.lastMessageTime = lastMessageTime;
  }

  public int getUnreadCount() {
    return unreadCount;
  }

  public void setUnreadCount(int unreadCount) {
    this.unreadCount = unreadCount;
  }

  @Override
  public String toString() {
    return "ChatRoomSummaryDto{" +
        "meId=" + meId +
        ", meNickname='" + meNickname + '\'' +
        ", chatRoomId=" + chatRoomId +
        ", tradeId=" + tradeId +
        ", productName='" + productName + '\'' +
        ", productPrice=" + productPrice +
        ", tradeStatus='" + tradeStatus + '\'' +
        ", productThumbnailUrl='" + productThumbnailUrl + '\'' +
        ", partnerId=" + partnerId +
        ", partnerNickname='" + partnerNickname + '\'' +
        ", partnerLocation='" + partnerLocation + '\'' +
        ", partnerTemperature=" + partnerTemperature +
        ", lastMessageContent='" + lastMessageContent + '\'' +
        ", lastMessageTime='" + lastMessageTime + '\'' +
        ", unreadCount=" + unreadCount +
        '}';
  }
}
