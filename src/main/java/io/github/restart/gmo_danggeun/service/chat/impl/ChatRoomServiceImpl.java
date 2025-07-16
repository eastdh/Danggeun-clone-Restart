package io.github.restart.gmo_danggeun.service.chat.impl;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomViewProjection;
import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.entity.ChatUser;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomQueryRepository;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

  private final ChatRoomQueryRepository chatRoomQueryRepository;

  @Autowired
  public ChatRoomServiceImpl(ChatRoomQueryRepository chatRoomQueryRepository) {
    this.chatRoomQueryRepository = chatRoomQueryRepository;
  }

  @Override
  public List<ChatRoomSummaryDto> getChatRoomSummaries(Long userId, boolean showUnreadOnly) {
    List<ChatRoomViewProjection> rows = chatRoomQueryRepository.findAllByUserId(userId);

    return rows.stream()
        .filter(row -> !showUnreadOnly || row.getUnreadCount() > 0) // 읽지 않은 메시지만 보기
        .map(row -> {
          ChatRoomSummaryDto dto = new ChatRoomSummaryDto();
          dto.setMeId(row.getMeId());
          dto.setMeNickname(row.getMeNickname());

          dto.setPartnerId(row.getPartnerId());
          dto.setPartnerNickname(row.getPartnerNickname());
          dto.setPartnerLocation(row.getPartnerLocation());
          dto.setPartnerTemperature(row.getPartnerTemperature());

          dto.setChatRoomId(row.getChatRoomId());
          dto.setTradeId(row.getTradeId());
          dto.setTradeTitle(row.getTradeTitle());
          dto.setTradePrice(row.getTradePrice());
          dto.setTradeStatus(row.getTradeStatus());
          dto.setTradeThumbnailUrl(row.getTradeThumbnailUrl());

          dto.setLastMessageContent(row.getLastMessageContent());
          dto.setLastMessageTime(row.getLastMessageTime());
          dto.setUnreadCount(row.getUnreadCount());

          return dto;
        })
        .collect(Collectors.toList());
  }

  @Override
  public ChatRoomDetailDto getChatRoomDetail(Long chatRoomId, Long userId) {
    ChatRoomViewProjection row = chatRoomQueryRepository
        .findByChatRoomIdAndUserId(chatRoomId, userId)
        .orElseThrow(() -> new RuntimeException("해당 채팅방 정보가 없습니다."));

    ChatRoomDetailDto dto = new ChatRoomDetailDto();
    dto.setChatRoomId(row.getChatRoomId());
    dto.setPartnerNickname(row.getPartnerNickname());
    dto.setPartnerTemperature(row.getPartnerTemperature());

    dto.setTradeId(row.getTradeId());
    dto.setTradeTitle(row.getTradeTitle());
    dto.setTradePrice(row.getTradePrice());
    dto.setTradeThumbnailUrl(row.getTradeThumbnailUrl());
    dto.setSeller(Boolean.TRUE.equals(row.getIsSeller()));
    dto.setClosed("completed".equalsIgnoreCase(row.getTradeStatus()));

    return dto;
  }



}

