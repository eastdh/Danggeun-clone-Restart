package io.github.restart.gmo_danggeun.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChatRoomDetailDto extends ChatRoomBaseDto {

  private boolean isSeller;
  private boolean isClosed; // 거래 완료 여부
}
