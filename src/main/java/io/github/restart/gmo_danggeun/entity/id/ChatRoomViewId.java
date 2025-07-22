package io.github.restart.gmo_danggeun.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomViewId implements Serializable {
  private Long chatRoomId;
  private Long meId;
}