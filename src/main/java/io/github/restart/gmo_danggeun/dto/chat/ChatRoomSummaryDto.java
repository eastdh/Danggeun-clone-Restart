package io.github.restart.gmo_danggeun.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
  private String lastMessageTime;

  // 안 읽은 메시지 수
  private int unreadCount;

}