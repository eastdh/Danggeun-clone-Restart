package io.github.restart.gmo_danggeun.repository.chat;

import io.github.restart.gmo_danggeun.entity.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  // 채팅방의 메시지를 생성 시간 기준으로 정렬하여 조회
  List<ChatMessage> findByChatRoomIdOrderByCreatedAt(Long chatRoomId);

  // 읽지 않은 메시지를 읽음 처리 (작성자가 아닌 유저 기준)
  @Modifying
  @Query("""
      UPDATE ChatMessage m
         SET m.readOrNot = true
       WHERE m.chatRoom.id = :chatRoomId
         AND m.writer.id <> :userId
         AND m.readOrNot = false
      """)
  void markUnreadMessagesAsRead(@Param("chatRoomId") Long chatRoomId,
      @Param("userId") Long userId);
}

