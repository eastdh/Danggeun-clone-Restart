package io.github.restart.gmo_danggeun.dto.chat;

import java.time.LocalDateTime;

public class ChatRoomSummaryDto {

  // 내 정보
  private Long meId;
  private String meNickname;

  // 상대방 정보
  private Long partnerId;
  private String partnerNickname;
  private String partnerLocation;
  private Double partnerTemperature;

  // 채팅방 및 거래글 정보
  private Long chatRoomId;
  private Long tradeId;
  private String tradeTitle;
  private Integer tradePrice;
  private String tradeStatus;
  private String tradeThumbnailUrl;

  // 마지막 메시지
  private String lastMessageContent;
  private LocalDateTime lastMessageTime;

  // 안 읽은 메시지 수
  private int unreadCount;

  public ChatRoomSummaryDto() {
  }

  public ChatRoomSummaryDto(Long meId, String meNickname, Long partnerId, String partnerNickname,
      String partnerLocation,
      Double partnerTemperature, Long chatRoomId, Long tradeId, String tradeTitle, Integer tradePrice,
      String tradeStatus,
      String tradeThumbnailUrl, String lastMessageContent, LocalDateTime lastMessageTime, int unreadCount) {
    this.meId = meId;
    this.meNickname = meNickname;
    this.partnerId = partnerId;
    this.partnerNickname = partnerNickname;
    this.partnerLocation = partnerLocation;
    this.partnerTemperature = partnerTemperature;
    this.chatRoomId = chatRoomId;
    this.tradeId = tradeId;
    this.tradeTitle = tradeTitle;
    this.tradePrice = tradePrice;
    this.tradeStatus = tradeStatus;
    this.tradeThumbnailUrl = tradeThumbnailUrl;
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

  public String getTradeTitle() {
    return tradeTitle;
  }

  public void setTradeTitle(String tradeTitle) {
    this.tradeTitle = tradeTitle;
  }

  public Integer getTradePrice() {
    return tradePrice;
  }

  public void setTradePrice(Integer tradePrice) {
    this.tradePrice = tradePrice;
  }

  public String getTradeStatus() {
    return tradeStatus;
  }

  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }

  public String getTradeThumbnailUrl() {
    return tradeThumbnailUrl;
  }

  public void setTradeThumbnailUrl(String tradeThumbnailUrl) {
    this.tradeThumbnailUrl = tradeThumbnailUrl;
  }

  public String getLastMessageContent() {
    return lastMessageContent;
  }

  public void setLastMessageContent(String lastMessageContent) {
    this.lastMessageContent = lastMessageContent;
  }

  public LocalDateTime getLastMessageTime() {
    return lastMessageTime;
  }

  public void setLastMessageTime(LocalDateTime lastMessageTime) {
    this.lastMessageTime = lastMessageTime;
  }

  public int getUnreadCount() {
    return unreadCount;
  }

  public void setUnreadCount(int unreadCount) {
    this.unreadCount = unreadCount;
  }
}