package io.github.restart.gmo_danggeun.dto.chat;

import lombok.Data;

@Data
public class ChatSendRequestDto {

  private Long chatRoomId;   // 메시지를 보낼 채팅방 ID
  private Long senderId;     // 메시지를 보낸 사용자 ID
  private String content;    // 메시지 내용

}
