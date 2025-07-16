package io.github.restart.gmo_danggeun.repository.chat;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomViewProjection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ChatRoomQueryRepository extends Repository<Object, Long> {

  @Query(value = """
      SELECT me_id AS meId,
             me_nickname AS meNickname,
             partner_id AS partnerId,
             partner_nickname AS partnerNickname,
             partner_location AS partnerLocation,
             partner_temperature AS partnerTemperature,
             chat_room_id AS chatRoomId,
             trade_id AS tradeId,
             trade_title AS tradeTitle,
             trade_price AS tradePrice,
             trade_status AS tradeStatus,
             trade_thumbnail_url AS tradeThumbnailUrl,
             last_message_content AS lastMessageContent,
             last_message_time AS lastMessageTime,
             unread_count AS unreadCount
      FROM chat_room_view
      WHERE me_id = :userId
      ORDER BY lastMessageTime DESC
      """, nativeQuery = true)
  List<ChatRoomViewProjection> findAllByUserId(@Param("userId") Long userId);
}

