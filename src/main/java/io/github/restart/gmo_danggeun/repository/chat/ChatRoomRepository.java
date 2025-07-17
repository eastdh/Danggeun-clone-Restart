package io.github.restart.gmo_danggeun.repository.chat;

import io.github.restart.gmo_danggeun.entity.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  // 거래글 기준 + 사용자 참여 기준으로 기존 채팅방 있는지 조회
  @Query("""
      SELECT cr FROM ChatRoom cr
      JOIN ChatUser cu ON cu.chatRoom.id = cr.id
      WHERE cr.trade.id = :tradeId AND cu.user.id IN :userIds
      GROUP BY cr.id
      HAVING COUNT(DISTINCT cu.user.id) = 2
      """)
  Optional<ChatRoom> findByTradeIdAndUserIds(@Param("tradeId") Long tradeId,
      @Param("userIds") List<Long> userIds);
}

