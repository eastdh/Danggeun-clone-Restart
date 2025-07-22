package io.github.restart.gmo_danggeun.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
abstract class ChatRoomBaseDto {

  Long chatRoomId;
  Long tradeId;
  String tradeTitle;
  Integer tradePrice;
  String tradeStatus;
  String tradeThumbnailUrl;

  Long partnerId;            // 상대방 유저 ID 추가
  String partnerNickname;
  String partnerLocation;
  Double partnerTemperature;
}
