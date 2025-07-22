package io.github.restart.gmo_danggeun.service.chat.impl;

import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.entity.ChatUser;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.TradeRepository;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatUserRepository;
import io.github.restart.gmo_danggeun.service.trade.TradeService;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import io.github.restart.gmo_danggeun.service.chat.ChatService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {


  private final UserRepository userRepository;
  private final TradeRepository tradeRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatUserRepository chatUserRepository;
  private final EntityManager entityManager;

  @Autowired
  public ChatServiceImpl(UserRepository userRepository, TradeRepository tradeRepository, ChatRoomRepository chatRoomRepository,
      ChatUserRepository chatUserRepository, EntityManager entityManager) {
    this.tradeRepository = tradeRepository;
    this.userRepository = userRepository;
    this.chatRoomRepository = chatRoomRepository;
    this.chatUserRepository = chatUserRepository;
    this.entityManager = entityManager;
  }

  @Override
  public ChatRoom createChatRoom(Long tradeId, Long senderId, Long receiverId) {
    if (senderId.equals(receiverId)) {
      throw new IllegalArgumentException("자기 자신과 채팅을 생성할 수 없습니다");
    }
    Trade trade = entityManager.find(Trade.class, tradeId, LockModeType.PESSIMISTIC_WRITE);
    if (trade == null) {
      throw new IllegalArgumentException("존재하지 않는 거래글입니다");
    }
    if(!userRepository.existsById(senderId)) {
      throw new IllegalArgumentException("사용자(sender) 없음");
    }
    if(!userRepository.existsById(receiverId)) {
      throw new IllegalArgumentException("사용자(receiver) 없음");
    }

    // 이미 동일한 거래에 대해 채팅방이 있는지 확인
    Optional<ChatRoom> existingRoom = chatRoomRepository
        .findByTradeIdAndUserIds(tradeId, List.of(senderId, receiverId)); // 커스텀 쿼리 필요

    if (existingRoom.isPresent()) {
      return existingRoom.get(); // 이미 있으면 반환
    }

    // 채팅방 생성


    ChatRoom chatRoom = new ChatRoom();
    chatRoom.setTrade(trade);
    chatRoom = chatRoomRepository.save(chatRoom); // 채팅방 저장

    // 채팅 참여자 등록
    User sender = entityManager.getReference(User.class, senderId);
    User receiver = entityManager.getReference(User.class, receiverId);

    ChatUser senderUser = new ChatUser(chatRoom, sender);
    ChatUser receiverUser = new ChatUser(chatRoom, receiver);
    chatUserRepository.saveAll(List.of(senderUser, receiverUser)); // 두 명 저장

    return chatRoom;
  }

}
