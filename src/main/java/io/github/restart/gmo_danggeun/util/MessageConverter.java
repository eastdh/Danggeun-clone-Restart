package io.github.restart.gmo_danggeun.util;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.MessageType;
import io.github.restart.gmo_danggeun.entity.ChatMessage;
import io.github.restart.gmo_danggeun.entity.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageConverter {

  /**
   * 일반 텍스트 메시지용 DTO 변환
   */
  public static ChatMessageDto toDto(ChatMessage message, Long currentUserId) {
    ChatMessageDto dto = new ChatMessageDto();

    dto.setMessageId(message.getId());

    User writer = message.getWriter();
    dto.setSenderId(writer.getId());

    // 현재 로그인 유저와 비교해 senderType 결정
    dto.setSenderType(writer.getId().equals(currentUserId) ? "ME" : "PARTNER");

    dto.setContent(message.getContent());
    dto.setTimestamp(TimeFormatUtil.formatForChat(message.getCreatedAt())); // "오후 3:13"
    dto.setRead(message.getReadOrNot());

    // ✅ enum 기반으로 타입 지정
    dto.setMessageType(MessageType.TEXT);

    return dto;
  }

  /**
   * 날짜 라벨 메시지 생성
   */
  public static ChatMessageDto toDateLabel(LocalDateTime dateTime) {
    ChatMessageDto dto = new ChatMessageDto();

    dto.setMessageType(MessageType.DATE_LABEL);
    dto.setSenderType(MessageType.DATE_LABEL.name());

    // 예: "2024년 7월 17일 수요일"
    dto.setContent(dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일")));
    dto.setTimestamp(dateTime.toLocalDate().toString());

    return dto;
  }
}
