package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatFilterRequestDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatSendRequestDto;
import io.github.restart.gmo_danggeun.dto.chat.TradeConfirmRequestDto;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import io.github.restart.gmo_danggeun.service.trade.TradeService;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import io.github.restart.gmo_danggeun.service.chat.ChatService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {

  private final ChatService chatService;
  private final ChatRoomService chatRoomService;
  private final ChatMessageService chatMessageService;
  private final TradeService tradeService;

  @Autowired
  public ChatController(ChatService chatService,
      ChatRoomService chatRoomService,
      ChatMessageService chatMessageService,
      TradeService tradeService) {
    this.chatService = chatService;
    this.chatRoomService = chatRoomService;
    this.chatMessageService = chatMessageService;
    this.tradeService = tradeService;
  }

  @GetMapping("/chat")
  public String chatPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
    Long userId = userDetails.getId();

    // 채팅방 리스트만 조회
    List<ChatRoomSummaryDto> chatRooms = chatRoomService.getChatRoomSummaries(userId, false);

    // 사용자 정보는 첫 채팅방에서 추출
    Long meId = chatRooms.isEmpty() ? userId : chatRooms.get(0).getMeId();
    String meNickname = chatRooms.isEmpty() ? "나" : chatRooms.get(0).getMeNickname();

    model.addAttribute("chatRooms", chatRooms);
    model.addAttribute("userId", meId);
    model.addAttribute("userNickname", meNickname);
    model.addAttribute("showUnreadOnly", false); // 기본값

    return "chat/chat_page";
  }

  @PostMapping("/api/chat/list")
  public ResponseEntity<List<ChatRoomSummaryDto>> getChatList(@RequestBody ChatFilterRequestDto request) {
    List<ChatRoomSummaryDto> chatRooms = chatRoomService.getChatRoomSummaries(request.getUserId(),
        request.isShowUnreadOnly());
    return ResponseEntity.ok(chatRooms);
  }

  @GetMapping("/api/chat/room/{chatRoomId}")
  public ResponseEntity<Map<String, Object>> getChatRoomDetail(@PathVariable Long chatRoomId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    Long userId = userDetails.getId();

    ChatRoomDetailDto detail = chatRoomService.getChatRoomDetail(chatRoomId, userId);
    List<ChatMessageDto> messages = chatMessageService.getMessagesWithDateLabels(chatRoomId, userId);

    Map<String, Object> response = new HashMap<>();
    response.put("detail", detail);
    response.put("messages", messages);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/chat/message")
  public ResponseEntity<ChatMessageDto> sendMessage(@RequestBody ChatSendRequestDto request) {
    ChatMessageDto savedMessage = chatMessageService.sendMessage(request);
    return ResponseEntity.ok(savedMessage);
  }

  @PostMapping("/api/chat/confirm-trade")
  public ResponseEntity<?> confirmTrade(@RequestBody TradeConfirmRequestDto request) {
    tradeService.confirmTrade(request.getTradeId());

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/api/chat/message/read")
  public ResponseEntity<?> markMessagesAsRead(@RequestBody Map<String, Object> request) {
    Long chatRoomId = Long.valueOf(request.get("chatRoomId").toString());
    Long userId = Long.valueOf(request.get("userId").toString());

    chatMessageService.markMessagesAsRead(chatRoomId, userId);

    return ResponseEntity.ok().build();
  }
}
