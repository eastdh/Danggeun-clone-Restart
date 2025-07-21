package io.github.restart.gmo_danggeun.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import lombok.Data;

@Data
public class ChatMessageDto {

  private Long chatRoomId;
  private Long messageId;
  private Long senderId;
  private String senderType; // ME, PARTNER, CHAT_BOT, DATE_LABEL
  private String content;
  private String timestamp;
  private Boolean isRead;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private MessageType messageType;
  // TEXT, IMAGE, SYSTEM, DATE_LABEL 등


  /**
   * 챗봇 응답 전용 팩토리 메서드
   *
   * @param chatRoomId 대화방 ID
   * @param replyText  챗봇이 생성한 텍스트
   * @return ChatMessageDto 인스턴스
   */
  public static ChatMessageDto ofBotReply(Long chatRoomId, String replyText) {
    ChatMessageDto dto = new ChatMessageDto();
    dto.setChatRoomId(chatRoomId);
    dto.setMessageId(null);              // DB에서 채팅 저장 후 채워질 수 있도록 null 로 둡니다
    dto.setSenderId(null);               // 챗봇에는 별도 사용자 ID 없으므로 null 또는 0 으로 설정
    dto.setSenderType("CHAT_BOT");       // 상수로 관리 중이라면 SENDER_TYPES.CHAT_BOT
    dto.setContent(replyText);
    dto.setTimestamp(Instant.now().toString());
    dto.setIsRead(false);                // 사용자가 아직 읽지 않은 상태
    dto.setMessageType(MessageType.TEXT);
    return dto;
  }
}