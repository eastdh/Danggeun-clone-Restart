package io.github.restart.gmo_danggeun.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
}
