package io.github.restart.gmo_danggeun.dto.chat;

public class TradeConfirmRequestDto {

  private Long chatRoomId;   // 거래가 확정될 채팅방의 ID
  private Long tradeId;      // 거래글의 ID

  public TradeConfirmRequestDto() {
  }

  public TradeConfirmRequestDto(Long chatRoomId, Long tradeId) {
    this.chatRoomId = chatRoomId;
    this.tradeId = tradeId;
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
}
