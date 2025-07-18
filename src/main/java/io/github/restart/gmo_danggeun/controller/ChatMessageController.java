package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
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


}
