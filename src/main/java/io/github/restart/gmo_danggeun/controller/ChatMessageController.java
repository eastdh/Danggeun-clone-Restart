package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.ReadReceiptDto;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

  private final SimpMessagingTemplate messagingTemplate;
  private final ChatMessageService chatMessageService;

  public ChatMessageController(SimpMessagingTemplate messagingTemplate,
      ChatMessageService chatMessageService) {
    this.messagingTemplate = messagingTemplate;
    this.chatMessageService = chatMessageService;
  }

  @MessageMapping("/send")
  public void handleMessage(ChatMessageDto incoming) {
    ChatMessageDto saved = chatMessageService.sendMessage(incoming);
    ChatMessageDto forOtherSide = adjustSenderType(saved, incoming.getSenderId());  // 브로드캐스트 시 senderType을 상대 기준으로 보정
    messagingTemplate.convertAndSend("/topic/chat/" + saved.getChatRoomId(), forOtherSide);
  }

  private ChatMessageDto adjustSenderType(ChatMessageDto dto, Long currentViewerId) {
    dto.setSenderType(dto.getSenderId().equals(currentViewerId) ? "ME" : "PARTNER");
    return dto;
  }

  @MessageMapping("/read")
  public void handleReadReceipt(ReadReceiptDto incoming) {
    // DB에 읽음 처리
    chatMessageService.markMessagesAsRead(
        incoming.getChatRoomId(),
        incoming.getReaderId()
    );

    // 실제 읽음 처리된 메시지 ID 목록 조회 (optional)
    List<Long> ids = chatMessageService.getLastReadMessageIds(
        incoming.getChatRoomId(),
        incoming.getReaderId()
    );

    // ReadReceiptDto에 ID 목록과 시각 추가
    ReadReceiptDto receipt = new ReadReceiptDto(
        incoming.getChatRoomId(),
        incoming.getReaderId(),
        ids,
        LocalDateTime.now()
    );

    // 구독자(상대방)에게 푸시
    messagingTemplate.convertAndSend(
        "/topic/chat/" + incoming.getChatRoomId() + "/read",
        receipt
    );
  }

}
