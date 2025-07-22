package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.dto.chat.ReadReceiptDto;
import io.github.restart.gmo_danggeun.entity.readonly.ChatRoomView;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomViewRepository;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import io.github.restart.gmo_danggeun.util.TimeFormatUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

  private final SimpMessagingTemplate messagingTemplate;
  private final ChatMessageService chatMessageService;
  private final ChatRoomViewRepository chatRoomViewRepository;

  @Autowired
  public ChatMessageController(SimpMessagingTemplate messagingTemplate,
      ChatMessageService chatMessageService,
      ChatRoomViewRepository chatRoomViewRepository) {
    this.messagingTemplate = messagingTemplate;
    this.chatMessageService = chatMessageService;
    this.chatRoomViewRepository = chatRoomViewRepository;
  }

  @MessageMapping("/send")
  public void handleMessage(ChatMessageDto incoming) {
    // DB 저장후 브로드캐스트
    ChatMessageDto saved = chatMessageService.sendMessage(incoming);
    messagingTemplate.convertAndSend("/topic/chat/" + saved.chatRoomId(), saved);

    // 양쪽 참여자에게 채팅 리스트 업데이트 이벤트 발송
    updateChatListForAllParticipants(saved.chatRoomId());
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

    updateChatListForAllParticipants(incoming.getChatRoomId());

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

  private void updateChatListForAllParticipants(Long chatRoomId) {
    // ChatRoomView에서 이 방에 참여하는 두 명의 요약 정보 조회
    List<ChatRoomView> rows = chatRoomViewRepository.findAllByChatRoomId(chatRoomId);

    rows.forEach(row -> {
      // SuperBuilder 기반 DTO 생성
      ChatRoomSummaryDto summary = ChatRoomSummaryDto.builder()
          // 🏷 BaseDto 필드
          .chatRoomId(row.getChatRoomId())
          .tradeId(row.getTradeId())
          .tradeTitle(row.getTradeTitle())
          .tradePrice(row.getTradePrice())
          .tradeStatus(row.getTradeStatus())
          .tradeThumbnailUrl(row.getTradeThumbnailUrl())
          .partnerNickname(row.getPartnerNickname())
          .partnerLocation(row.getPartnerLocation())
          .partnerTemperature(row.getPartnerTemperature())
          // 🏷 Me 정보
          .meId(row.getMeId())
          .meNickname(row.getMeNickname())
          // 🏷 Summary 전용 필드
          .lastMessageContent(row.getLastMessageContent())
          .lastMessageTime(TimeFormatUtil.formatElapsedTime(row.getLastMessageTime()))
          .unreadCount(row.getUnreadCount())
          .build();

      // /user/{meId}/queue/chat-list 로 해당 사용자에게만 푸시
      messagingTemplate.convertAndSendToUser(
          row.getMeId().toString(),
          "/queue/chat-list",
          summary
      );
    });
  }
}
