package io.github.restart.gmo_danggeun.service.chat.impl;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.entity.readonly.ChatRoomView;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomViewRepository;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

  private final ChatRoomViewRepository chatRoomViewRepository;

  @Autowired
  public ChatRoomServiceImpl(ChatRoomViewRepository chatRoomViewRepository) {
    this.chatRoomViewRepository = chatRoomViewRepository;
  }

  @Override
  public List<ChatRoomSummaryDto> getChatRoomSummaries(Long userId, boolean showUnreadOnly) {
    List<ChatRoomView> rows = chatRoomViewRepository.findAllByMeId(userId);

    return rows.stream()
        .filter(row -> !showUnreadOnly || row.getUnreadCount() > 0)
        .map(row -> new ChatRoomSummaryDto(
            row.getMeId(),
            row.getMeNickname(),
            row.getPartnerId(),
            row.getPartnerNickname(),
            row.getPartnerLocation(),
            row.getPartnerTemperature(),
            row.getChatRoomId(),
            row.getTradeId(),
            row.getTradeTitle(),
            row.getTradePrice(),
            row.getTradeStatus(),
            row.getTradeThumbnailUrl(),
            row.getLastMessageContent(),
            row.getLastMessageTime(),
            row.getUnreadCount()
        ))
        .collect(Collectors.toList());
  }

  @Override
  public ChatRoomDetailDto getChatRoomDetail(Long chatRoomId, Long userId) {
    ChatRoomView row = chatRoomViewRepository
        .findByChatRoomIdAndMeId(chatRoomId, userId)
        .orElseThrow(() -> new RuntimeException("해당 채팅방 정보가 없습니다."));

    return new ChatRoomDetailDto(
        row.getChatRoomId(),
        row.getPartnerNickname(),
        row.getPartnerTemperature(),
        row.getTradeId(),
        row.getTradeThumbnailUrl(),
        row.getTradeTitle(),
        row.getTradePrice(),
        Boolean.TRUE.equals(row.getIsSeller()),
        "completed".equalsIgnoreCase(row.getTradeStatus())
    );
  }
}