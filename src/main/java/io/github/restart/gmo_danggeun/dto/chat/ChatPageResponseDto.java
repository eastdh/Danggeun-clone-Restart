package io.github.restart.gmo_danggeun.dto.chat;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 채팅 페이지를 총괄하는 DTO
 */
@Data
@AllArgsConstructor
public class ChatPageResponseDto {

  // 로그인한 사용자 정보
  private Long userId;
  private String userNickname;
  private boolean showUnreadOnly;

  // 좌측: 채팅방 목록
  private List<ChatRoomSummaryDto> chatRooms;

  // 우측: 선택된 채팅방 정보 및 메시지
  private boolean isSeller;
  private ChatRoomDetailDto selectedRoom;                  // 선택된 채팅방 정보
  private List<ChatMessageDto> messages;

}
