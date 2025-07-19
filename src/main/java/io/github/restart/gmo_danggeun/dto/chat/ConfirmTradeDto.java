package io.github.restart.gmo_danggeun.dto.chat;

import lombok.Data;

@Data
public class ConfirmTradeDto {

  private Long chatRoomId;   // 거래가 확정될 채팅방의 ID
  private Long tradeId;      // 거래글의 ID

}
