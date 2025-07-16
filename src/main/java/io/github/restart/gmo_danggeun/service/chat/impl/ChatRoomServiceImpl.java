package io.github.restart.gmo_danggeun.service.chat.impl;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import io.github.restart.gmo_danggeun.entity.ChatRoom;
import io.github.restart.gmo_danggeun.service.chat.ChatRoomService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatRoomServiceImpl implements ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final ChatRoomConverter converter;

  @Autowired
  public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository,
      ProductRepository productRepository,
      UserRepository userRepository,
      ChatRoomConverter converter) {
    this.chatRoomRepository = chatRoomRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.converter = converter;
  }


  @Override
  public List<ChatRoomSummaryDto> getChatRoomSummaries(Long userId, boolean showUnreadOnly) {
    List<ChatRoom> rooms = chatRoomRepository.findByUser(userId);

    return rooms.stream()
        .filter(room -> !showUnreadOnly || room.hasUnreadMessages(userId))
        .map(converter::toSummaryDto)
        .collect(Collectors.toList());

  }

  @Override
  public ChatRoomDetailDto getChatRoomDetail(Long chatRoomId, Long userId) {
    ChatRoom room = chatRoomRepository.findById(chatRoomId)
        .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다"));

    Product product = productRepository.findById(room.getProduct().getId())
        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다"));

    User partner = userRepository.findById(room.getPartnerId(userId))
        .orElseThrow(() -> new IllegalArgumentException("상대방 정보를 찾을 수 없습니다"));

    return converter.toDetailDto(room, product, partner, userId);

  }
}
