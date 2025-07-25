package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomFilterDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.dto.chat.ConfirmTradeDto;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public ChatController(ChatService chatService,
      ChatRoomService chatRoomService,
      ChatMessageService chatMessageService,
      TradeService tradeService,
      SimpMessagingTemplate messagingTemplate) {
    this.chatService = chatService;
    this.chatRoomService = chatRoomService;
    this.chatMessageService = chatMessageService;
    this.tradeService = tradeService;
    this.messagingTemplate = messagingTemplate;
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
  public ResponseEntity<List<ChatRoomSummaryDto>> getChatList(@RequestBody ChatRoomFilterDto request) {
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


  @PostMapping("/api/chat/confirm-trade")
  public ResponseEntity<Void> confirmTrade(
      @RequestBody ConfirmTradeDto request,
      @AuthenticationPrincipal CustomUserDetails user) {

    Long tradeId = request.tradeId();
    Long chatRoomId = request.chatRoomId();
    Long sellerId = user.getId();

    tradeService.confirmTrade(request.tradeId());

    // 시스템 메시지 생성
    String content = "거래가 확정되었습니다. 서로에 대한 후기를 작성해주세요!";
    String buttonText = "후기 작성하기";

    ChatRoomDetailDto detail = chatRoomService.getChatRoomDetail(chatRoomId, sellerId);
    Long buyerId = detail.getPartnerId();
    String buttonUrl = String.format(
        "/review/write?trade_id=%d&seller_id=%d&buyer_id=%d&chat_room_id=%d",
        tradeId, sellerId, buyerId, chatRoomId
    );
    ChatMessageDto sysMsg = chatMessageService.createSystemMessage(
        chatRoomId, sellerId, content, buttonText, buttonUrl
    );

    // WebSocket으로 거래 완료 알림 발행 (기존 chat 로직)
    messagingTemplate.convertAndSend(
        "/topic/chat/" + chatRoomId,
        sysMsg
    );

    // 빈 바디 204 No Content 반환
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/api/chat/message/read")
  public ResponseEntity<?> markMessagesAsRead(@RequestBody Map<String, Object> request) {
    Long chatRoomId = Long.valueOf(request.get("chatRoomId").toString());
    Long userId = Long.valueOf(request.get("userId").toString());

    chatMessageService.markMessagesAsRead(chatRoomId, userId);

    return ResponseEntity.ok().build();
  }
}
