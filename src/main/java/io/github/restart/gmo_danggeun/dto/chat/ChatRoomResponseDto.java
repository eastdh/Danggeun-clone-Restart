package io.github.restart.gmo_danggeun.dto.chat;

import io.github.restart.gmo_danggeun.entity.ChatRoom;

public record ChatRoomResponseDto(
    Long chatRoomId
) {
  public static ChatRoomResponseDto of(ChatRoom cr) {
    return new ChatRoomResponseDto(cr.getId());
  }
}

