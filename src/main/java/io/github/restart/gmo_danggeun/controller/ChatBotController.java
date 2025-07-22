package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatBotMessageRequestDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.service.chat.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/bot")
public class ChatBotController {

  private final ChatBotService chatBotService;
  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public ChatBotController(ChatBotService chatBotService, SimpMessagingTemplate messagingTemplate) {
    this.chatBotService = chatBotService;
    this.messagingTemplate = messagingTemplate;
  }

  @PostMapping
  public ChatMessageDto chat(@RequestBody ChatBotMessageRequestDto requestDto) {
    String reply = chatBotService.generateReply(requestDto.getContent());
    ChatMessageDto dto = ChatMessageDto.ofBotReply(requestDto.getChatRoomId(), reply);
    messagingTemplate.convertAndSend("/topic/chat/bot/" + requestDto.getChatRoomId(), dto);
    return dto;
  }
}
