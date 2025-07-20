package io.github.restart.gmo_danggeun.service.chat.impl;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.entity.readonly.ChatRoomView;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomViewRepository;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import io.github.restart.gmo_danggeun.util.LocationUtil;
import io.github.restart.gmo_danggeun.util.TimeFormatUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {

  private final ChatRoomViewRepository chatRoomViewRepository;

  @Autowired
  public ChatRoomServiceImpl(ChatRoomViewRepository chatRoomViewRepository) {
    this.chatRoomViewRepository = chatRoomViewRepository;
  }

  @Override
  public List<ChatRoomSummaryDto> getChatRoomSummaries(Long userId, boolean showUnreadOnly) {
    return chatRoomViewRepository.findAllByMeId(userId).stream()
        .filter(row -> !showUnreadOnly || row.getUnreadCount() > 0)
        .map(row -> ChatRoomSummaryDto
            .builder()
            // -- BaseDto 필드 --
            .chatRoomId(row.getChatRoomId())
            .tradeId(row.getTradeId())
            .tradeTitle(row.getTradeTitle())
            .tradePrice(row.getTradePrice())
            .tradeStatus(row.getTradeStatus())
            .tradeThumbnailUrl(row.getTradeThumbnailUrl())
            .partnerNickname(row.getPartnerNickname())
            .partnerLocation(LocationUtil.extractRepresentativeLocation(row.getPartnerLocation()))
            .partnerTemperature(row.getPartnerTemperature())
            // -- SummaryDto 필드 --
            .meId(row.getMeId())
            .meNickname(row.getMeNickname())
            .lastMessageContent(row.getLastMessageContent())
            .lastMessageTime(TimeFormatUtil.formatElapsedTime(row.getLastMessageTime()))
            .unreadCount(row.getUnreadCount())
            .build()
        )
        .collect(Collectors.toList());
  }

  @Override
  public ChatRoomDetailDto getChatRoomDetail(Long chatRoomId, Long userId) {
    ChatRoomView row = chatRoomViewRepository
        .findByChatRoomIdAndMeId(chatRoomId, userId)
        .orElseThrow(() -> new RuntimeException("해당 채팅방 정보가 없습니다."));

    return ChatRoomDetailDto
        .builder()
        // -- BaseDto 필드 --
        .chatRoomId(row.getChatRoomId())
        .tradeId(row.getTradeId())
        .tradeTitle(row.getTradeTitle())
        .tradePrice(row.getTradePrice())
        .tradeStatus(row.getTradeStatus())
        .tradeThumbnailUrl(row.getTradeThumbnailUrl())
        .partnerNickname(row.getPartnerNickname())
        .partnerLocation(LocationUtil.extractRepresentativeLocation(row.getPartnerLocation()))
        .partnerTemperature(row.getPartnerTemperature())
        .isSeller(Boolean.TRUE.equals(row.getIsSeller()))
        // -- DetailDto 필드 --
        .isClosed("completed".equalsIgnoreCase(row.getTradeStatus()))
        .build();
  }
}
