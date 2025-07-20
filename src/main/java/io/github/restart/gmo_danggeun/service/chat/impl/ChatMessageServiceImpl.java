package io.github.restart.gmo_danggeun.service.chat.impl;


import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.entity.ChatMessage;
import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatMessageRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomRepository;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import io.github.restart.gmo_danggeun.util.MessageConverter;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final UserRepository userRepository;
  private final EntityManager entityManager;

  @Autowired
  public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository,
      ChatRoomRepository chatRoomRepository,
      UserRepository userRepository, EntityManager entityManager) {
    this.chatMessageRepository = chatMessageRepository;
    this.chatRoomRepository = chatRoomRepository;
    this.userRepository = userRepository;
    this.entityManager = entityManager;
  }


  @Override
  public ChatMessageDto sendMessage(ChatMessageDto dto) {
    if (!chatRoomRepository.existsById(dto.getChatRoomId())) {
      throw new IllegalArgumentException("채팅방이 존재하지 않습니다");
    }

    if (!userRepository.existsById(dto.getSenderId())) {
      throw new IllegalArgumentException("보낸 사람 정보가 없습니다");
    }

    ChatRoom roomRef = entityManager.getReference(ChatRoom.class, dto.getChatRoomId());
    User writerRef = entityManager.getReference(User.class, dto.getSenderId());

    ChatMessage message = new ChatMessage();
    message.setChatRoom(roomRef);
    message.setWriter(writerRef);
    message.setContent(dto.getContent());
    message.setCreatedAt(LocalDateTime.now());
    message.setReadOrNot(false);

    ChatMessage saved = chatMessageRepository.save(message);

    return MessageConverter.toDto(saved, writerRef.getId());
  }


  @Override
  public List<ChatMessageDto> getMessagesWithDateLabels(Long chatRoomId, Long currentUserId) {
    List<ChatMessage> messages = chatMessageRepository.findMessagesByChatRoomId(chatRoomId);

    if (messages == null || messages.isEmpty()) {
      return Collections.emptyList();
    }

    List<ChatMessageDto> result = new ArrayList<>();
    LocalDate previousDate = null;

    for (ChatMessage msg : messages) {
      LocalDateTime created = msg.getCreatedAt();
      if (created == null) {
        continue; // ✅ null 방어
      }
      LocalDate currentDate = created.toLocalDate();

      if (!currentDate.equals(previousDate)) {
        result.add(MessageConverter.toDateLabel(created));
        previousDate = currentDate;
      }

      result.add(MessageConverter.toDto(msg, currentUserId));
    }

    return result;
  }


  @Override
  @Transactional
  public void markMessagesAsRead(Long chatRoomId, Long userId) {
    chatMessageRepository.markUnreadMessagesAsRead(chatRoomId, userId);
  }

  @Override
  public List<Long> getLastReadMessageIds(Long chatRoomId, Long readerId) {
    return chatMessageRepository.findMessageIdsByChatRoomIdAndReader(
        chatRoomId, readerId
    );
  }

}
