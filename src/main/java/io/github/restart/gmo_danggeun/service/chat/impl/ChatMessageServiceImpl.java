package io.github.restart.gmo_danggeun.service.chat.impl;


import io.github.restart.gmo_danggeun.dto.chat.ChatMessageDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatSendRequestDto;
import io.github.restart.gmo_danggeun.entity.ChatMessage;
import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatMessageRepository;
import io.github.restart.gmo_danggeun.repository.chat.ChatRoomRepository;
import io.github.restart.gmo_danggeun.service.chat.ChatMessageService;
import io.github.restart.gmo_danggeun.util.MessageConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final UserRepository userRepository;

  @Autowired
  public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository,
      ChatRoomRepository chatRoomRepository,
      UserRepository userRepository) {
    this.chatMessageRepository = chatMessageRepository;
    this.chatRoomRepository = chatRoomRepository;
    this.userRepository = userRepository;
  }


  @Override
  public ChatMessageDto sendMessage(ChatSendRequestDto request) {
    ChatRoom room = chatRoomRepository.findById(request.getChatRoomId())
        .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다"));

    User writer = userRepository.findById(request.getSenderId())
        .orElseThrow(() -> new IllegalArgumentException("보낸 사람 정보가 없습니다"));

    ChatMessage message = new ChatMessage();
    message.setChatRoom(room);
    message.setWriter(writer);
    message.setContent(request.getContent());
    message.setCreatedAt(LocalDateTime.now());
    message.setReadOrNot(false);

    ChatMessage saved = chatMessageRepository.save(message);

    return MessageConverter.toDto(saved, writer.getId());
  }


  @Override
  public List<ChatMessageDto> getMessagesWithDateLabels(Long chatRoomId, Long currentUserId) {
    List<ChatMessage> messages = chatMessageRepository.findByChatRoomIdOrderByCreatedAt(chatRoomId);

    if (messages == null || messages.isEmpty()) {
      return Collections.emptyList();
    }

    List<ChatMessageDto> result = new ArrayList<>();
    LocalDate previousDate = null;

    for (ChatMessage msg : messages) {
      LocalDateTime created = msg.getCreatedAt();
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

}
