package io.github.restart.gmo_danggeun.dto.chat;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadReceiptDto {

  private Long chatRoomId;
  private Long readerId;
  private List<Long> messageIds;
  private LocalDateTime readAt;

}
