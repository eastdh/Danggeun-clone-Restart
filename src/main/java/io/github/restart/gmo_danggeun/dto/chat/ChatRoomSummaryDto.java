package io.github.restart.gmo_danggeun.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChatRoomSummaryDto extends ChatRoomBaseDto {

  // 내 정보
  private Long meId;
  private String meNickname;

  // 마지막 메시지
  private String lastMessageContent;
  private String lastMessageTime;

  // 안 읽은 메시지 수
  private int unreadCount;

}