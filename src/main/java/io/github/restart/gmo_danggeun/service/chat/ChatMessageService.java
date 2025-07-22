package io.github.restart.gmo_danggeun.service.chat;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import java.util.List;

public interface ChatMessageService {

  ChatMessageDto sendMessage(ChatMessageDto dto);

  List<ChatMessageDto> getMessagesWithDateLabels(Long chatRoomId, Long currentUserId);

  void markMessagesAsRead(Long chatRoomId, Long userId);

  List<Long> getLastReadMessageIds(Long chatRoomId, Long readerId);

  ChatMessageDto createSystemMessage(
      Long chatRoomId,
      Long senderId,
      String content,
      String buttonText,
      String buttonUrl);

}
