package io.github.restart.gmo_danggeun.service.chat;

import io.github.restart.gmo_danggeun.dto.chat.ChatPageResponseDto;
import io.github.restart.gmo_danggeun.entity.ChatRoom;

public interface ChatService {

  public ChatRoom createChatRoom(Long tradeId, Long senderId, Long receiverId);

  ChatPageResponseDto buildChatPage(Long userId);
}

