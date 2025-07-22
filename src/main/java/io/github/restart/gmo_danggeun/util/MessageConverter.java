package io.github.restart.gmo_danggeun.util;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.MessageType;
import io.github.restart.gmo_danggeun.dto.chat.SenderType;
import io.github.restart.gmo_danggeun.entity.ChatMessage;
import io.github.restart.gmo_danggeun.entity.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageConverter {

  /**
   * 일반 텍스트 메시지용 DTO 변환
   */
  public static ChatMessageDto toDto(ChatMessage message, Long currentUserId) {
    Long roomId = message.getChatRoom().getId();
    Long msgId = message.getId();
    Long senderId = message.getWriter().getId();
    boolean isMe = senderId.equals(currentUserId);
    MessageType mt = MessageType.TEXT;
    SenderType st = isMe ? SenderType.ME : SenderType.PARTNER;
    String raw = message.getContent();
    String ts = TimeFormatUtil.formatForChat(message.getCreatedAt());
    Boolean isRead = message.getReadOrNot();

    if (raw != null && raw.startsWith("SYSTEM<") && raw.endsWith(">")) {
      // 내부 메타 문자열만 추출
      String meta = raw.substring("SYSTEM<".length(), raw.length() - 1);
      // content, buttonText, buttonUrl 순으로 분리 ("," 2개까지)
      String[] parts = meta.split(",", 3);
      String content    = parts.length > 0 ? parts[0] : "";
      String buttonText = parts.length > 1 ? parts[1] : "";
      String buttonUrl  = parts.length > 2 ? parts[2] : "";

      return new ChatMessageDto(
          roomId,
          msgId,
          senderId,
          content,           // 실제 보여줄 content
          ts,
          isRead,
          MessageType.SYSTEM,
          SenderType.SYSTEM,
          buttonText,        // 버튼 텍스트
          buttonUrl          // 버튼 URL
      );
    }

    String content = raw != null ? raw : "";
    // buttonText, buttonUrl 필드는 일반 메시지에선 빈 문자열로 설정
    return new ChatMessageDto(
        roomId,
        msgId,
        senderId,
        content,
        ts,
        isRead,
        mt,
        st,
        "",      // buttonText
        ""       // buttonUrl
    );
  }

  /**
   * 날짜 라벨 메시지 생성
   */
  public static ChatMessageDto toDateLabel(Long chatRoomId, LocalDateTime dateTime) {
    String label = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일"));
    String ts = dateTime.toLocalDate().toString();

    return new ChatMessageDto(
        chatRoomId,
        null,             // messageId
        null,             // senderId
        label,
        ts,
        true,             // 읽음 여부: 날짜 라벨은 항상 읽은 것으로 처리
        MessageType.DATE_LABEL,
        SenderType.SYSTEM,
        "",               // buttonText
        ""                // buttonUrl
    );
  }

  /**
   * 시스템 메시지를 위한 전용 DTO 변환기
   */
  public static ChatMessageDto toSystemDto(
      ChatMessage msg,
      Long currentUserId,
      String buttonText,
      String buttonUrl
  ) {
    // 기존 toDto 로 뽑아오되…
    ChatMessageDto base = toDto(msg, currentUserId);
    // …messageType·senderType을 SYSTEM 으로, 버튼 정보도 채워서 반환
    return new ChatMessageDto(
        base.chatRoomId(),
        base.messageId(),
        base.senderId(),
        base.content(),
        base.timestamp(),
        base.isRead(),
        MessageType.SYSTEM,
        SenderType.SYSTEM,
        buttonText,
        buttonUrl
    );
  }
}
