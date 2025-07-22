package io.github.restart.gmo_danggeun.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import lombok.Data;

public record ChatMessageDto(
    Long chatRoomId,
    Long messageId,
    Long senderId,
    String content,
    String timestamp,
    Boolean isRead,
    MessageType messageType,
    SenderType senderType,
    String buttonText,
    String buttonUrl
) {
  // 기존 ofBotReply 팩토리는 static 생성 메서드로 재정의

  public static ChatMessageDto ofBotReply(Long chatRoomId, String replyText) {
    return new ChatMessageDto(
        chatRoomId,
        null,       // messageId
        null,       // senderId (챗봇)
        replyText,
        Instant.now().toString(),
        false,      // isRead
        MessageType.TEXT,
        SenderType.CHAT_BOT,
        "",         // buttonText
        ""          // buttonUrl
    );
  }
}
