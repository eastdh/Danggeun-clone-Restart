package io.github.restart.gmo_danggeun.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ChatMessageDto {

  private Long messageId;
  private Long senderId;
  private String senderType; // ME, PARTNER, CHAT_BOT, DATE_LABEL
  private String content;
  private String timestamp;
  private boolean isRead;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private MessageType messageType;
  // TEXT, IMAGE, SYSTEM, DATE_LABEL 등

}