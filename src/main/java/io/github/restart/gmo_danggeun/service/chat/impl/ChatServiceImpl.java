package io.github.restart.gmo_danggeun.service.chat.impl;

import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatPageResponseDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.entity.ChatUser;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatUserRepository;
import io.github.restart.gmo_danggeun.service.TradeService;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import io.github.restart.gmo_danggeun.service.chat.ChatService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

  private final ChatRoomService chatRoomService;
  private final ChatMessageService chatMessageService;
  private final TradeService tradeService;

  private final UserRepository userRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatUserRepository chatUserRepository;

  @Autowired
  public ChatServiceImpl(ChatRoomService chatRoomService, ChatMessageService chatMessageService,
      TradeService tradeService, UserRepository userRepository, ChatRoomRepository chatRoomRepository,
      ChatUserRepository chatUserRepository) {
    this.chatRoomService = chatRoomService;
    this.chatMessageService = chatMessageService;
    this.tradeService = tradeService;
    this.userRepository = userRepository;
    this.chatRoomRepository = chatRoomRepository;
    this.chatUserRepository = chatUserRepository;
  }

  @Override
  public ChatRoom createChatRoom(Long tradeId, Long senderId, Long receiverId) {
    // 이미 동일한 거래에 대해 채팅방이 있는지 확인
    Optional<ChatRoom> existingRoom = chatRoomRepository
        .findByTradeIdAndUserIds(tradeId, List.of(senderId, receiverId)); // 커스텀 쿼리 필요

    if (existingRoom.isPresent()) {
      return existingRoom.get(); // 이미 있으면 반환
    }

    // 채팅방 생성
    Trade trade = tradeService.findTradeEntityById(tradeId)
        .orElseThrow(() -> new RuntimeException("거래글이 존재하지 않습니다"));

    ChatRoom chatRoom = new ChatRoom();
    chatRoom.setTrade(trade);
    chatRoom = chatRoomRepository.save(chatRoom); // 채팅방 저장

    // 채팅 참여자 등록
    User sender = userRepository.findById(senderId)
        .orElseThrow(() -> new RuntimeException("사용자(sender) 없음"));
    User receiver = userRepository.findById(receiverId)
        .orElseThrow(() -> new RuntimeException("사용자(receiver) 없음"));

    ChatUser senderUser = new ChatUser(chatRoom, sender);
    ChatUser receiverUser = new ChatUser(chatRoom, receiver);
    chatUserRepository.saveAll(List.of(senderUser, receiverUser)); // 두 명 저장

    return chatRoom;
  }


  @Override
  public ChatPageResponseDto buildChatPage(Long userId) {
    // 채팅방 목록 조회
    List<ChatRoomSummaryDto> chatRooms = chatRoomService.getChatRoomSummaries(userId, false);

    // 최근 메시지 기준으로 정렬 -> 가장 최신 채팅방 선택
    ChatRoomSummaryDto selectedSummary = chatRooms.stream()
        .min(Comparator.comparing(ChatRoomSummaryDto::getLastMessageTime,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .orElse(null);

    // 선택된 채팅방 상세 정보 & 메시지 목록 구성
    ChatRoomDetailDto selectedRoom = null;
    List<ChatMessageDto> messages = Collections.emptyList();
    boolean isSeller = false;

    if (selectedSummary != null) {
      selectedRoom = chatRoomService.getChatRoomDetail(selectedSummary.getChatRoomId(), userId);
      messages = chatMessageService.getMessagesWithDateLabels(selectedSummary.getChatRoomId(), userId);
      isSeller = selectedRoom.isSeller();
    }

    // 사용자 정보 구성
    User me = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다"));

    return new ChatPageResponseDto(
        me.getId(),
        me.getNickname(),
        false, // showUnreadOnly 기본값 false
        chatRooms,
        isSeller,
        selectedRoom,
        messages
    );
  }

}
