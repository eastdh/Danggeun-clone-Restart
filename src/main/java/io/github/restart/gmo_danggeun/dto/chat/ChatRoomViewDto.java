package io.github.restart.gmo_danggeun.dto.chat;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChatRoomViewDto {

  // 로그인한 사용자 정보
  private Long meId;
  private String meNickname;

  // 상대방 정보
  private Long partnerId;
  private String partnerNickname;
  private String partnerLocation;
  private Double partnerTemperature;

  // 채팅방 정보
  private Long chatRoomId;
  private Long tradeId;

  // 거래글 정보
  private String tradeTitle;
  private Integer tradePrice;
  private String tradeStatus;
  private String tradeThumbnailUrl;

  // 판매자 여부
  private Boolean isSeller;

  // 마지막 메시지 정보
  private String lastMessageContent;
  private LocalDateTime lastMessageTime;

  // 안 읽은 메시지 수
  private Integer unreadCount;

}
