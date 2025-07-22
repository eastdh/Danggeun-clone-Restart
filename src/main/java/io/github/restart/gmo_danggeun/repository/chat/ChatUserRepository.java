package io.github.restart.gmo_danggeun.repository.chat;

import io.github.restart.gmo_danggeun.entity.ChatUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

  // 채팅방 내에서 내가 아닌 상대방 조회
  Optional<ChatUser> findByChatRoomIdAndUserIdNot(Long chatRoomId, Long userId);

  // 사용자 기준 모든 참여 채팅방 ID 목록 조회
  @Query("""
      SELECT cu.chatRoom.id FROM ChatUser cu
      WHERE cu.user.id = :userId
      """)
  List<Long> findChatRoomIdsByUserId(@Param("userId") Long userId);

  // 특정 채팅방에서 두 명의 참여자가 모두 포함되어 있는지 체크
  @Query("""
      SELECT COUNT(cu) FROM ChatUser cu
      WHERE cu.chatRoom.id = :chatRoomId AND cu.user.id IN :userIds
      """)
  Long countUsersInChatRoom(@Param("chatRoomId") Long chatRoomId,
      @Param("userIds") List<Long> userIds);
}

