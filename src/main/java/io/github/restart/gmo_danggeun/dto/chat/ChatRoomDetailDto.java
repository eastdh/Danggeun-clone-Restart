package io.github.restart.gmo_danggeun.dto.chat;

public class ChatRoomDetailDto {

  private Long chatRoomId;

  // 상대방 정보
  private String partnerNickname;
  private Double partnerTemperature;

  // 거래 정보
  private Long tradeId;
  private String tradeThumbnailUrl;
  private String tradeTitle;
  private Integer tradePrice;
  private boolean isSeller;
  private boolean isClosed; // 거래 완료 여부

  public ChatRoomDetailDto() {
  }

  public ChatRoomDetailDto(Long chatRoomId, String partnerNickname, Double partnerTemperature, Long tradeId,
      String tradeThumbnailUrl, String tradeTitle, Integer tradePrice, boolean isSeller, boolean isClosed) {
    this.chatRoomId = chatRoomId;
    this.partnerNickname = partnerNickname;
    this.partnerTemperature = partnerTemperature;
    this.tradeId = tradeId;
    this.tradeThumbnailUrl = tradeThumbnailUrl;
    this.tradeTitle = tradeTitle;
    this.tradePrice = tradePrice;
    this.isSeller = isSeller;
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

  public Long getTradeId() {
    return tradeId;
  }

  public void setTradeId(Long tradeId) {
    this.tradeId = tradeId;
  }

  public String getTradeThumbnailUrl() {
    return tradeThumbnailUrl;
  }

  public void setTradeThumbnailUrl(String tradeThumbnailUrl) {
    this.tradeThumbnailUrl = tradeThumbnailUrl;
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

  public boolean isSeller() {
    return isSeller;
  }

  public void setSeller(boolean seller) {
    isSeller = seller;
  }

  public boolean isClosed() {
    return isClosed;
  }

  public void setClosed(boolean closed) {
    isClosed = closed;
  }
}
