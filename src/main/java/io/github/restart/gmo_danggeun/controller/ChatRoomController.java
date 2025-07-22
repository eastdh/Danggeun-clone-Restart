package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomRequestDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomResponseDto;
import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import io.github.restart.gmo_danggeun.service.CustomUserDetailsService;
import io.github.restart.gmo_danggeun.service.chat.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/room")
public class ChatRoomController {

  private final ChatService chatService;
  private final CustomUserDetailsService userDetailsService;

  public ChatRoomController(ChatService chatService,
      CustomUserDetailsService userDetailsService) {
    this.chatService = chatService;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping
  public ResponseEntity<ChatRoomResponseDto> createOrGetRoom(
      @RequestBody ChatRoomRequestDto dto,
      @AuthenticationPrincipal CustomUserDetails principal) {

    Long senderId = principal.getId();
    ChatRoom room = chatService.createChatRoom(
        dto.tradeId(),
        senderId,
        dto.receiverId()
    );
    return ResponseEntity.ok(ChatRoomResponseDto.of(room));
  }
}

