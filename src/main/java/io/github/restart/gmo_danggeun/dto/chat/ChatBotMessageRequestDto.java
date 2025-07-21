package io.github.restart.gmo_danggeun.dto.chat;

import lombok.Data;

@Data
public class ChatBotMessageRequestDto {
  private Long chatRoomId;
  private Long senderId;
  private String content;
}
