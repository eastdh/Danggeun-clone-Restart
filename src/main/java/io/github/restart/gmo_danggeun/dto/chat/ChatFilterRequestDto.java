package io.github.restart.gmo_danggeun.dto.chat;

import lombok.Data;

@Data
public class ChatFilterRequestDto {
  private Long userId;             // 로그인한 사용자 ID
  private boolean showUnreadOnly;  // 읽지 않은 항목만 보기 여부
}
