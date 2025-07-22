package io.github.restart.gmo_danggeun.service.chat;

import io.github.restart.gmo_danggeun.entity.ChatRoom;

public interface ChatService {

  public ChatRoom createChatRoom(Long tradeId, Long senderId, Long receiverId);

}

