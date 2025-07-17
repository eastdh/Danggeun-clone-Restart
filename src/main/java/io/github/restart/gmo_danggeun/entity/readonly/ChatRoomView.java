package io.github.restart.gmo_danggeun.entity.readonly;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Table(name = "chat_room_view")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomView {
  @Id
  private Long chatRoomId;

  private Long meId;
  private String meNickname;

  private Long partnerId;
  private String partnerNickname;
  private String partnerLocation;
  private Double partnerTemperature;

  private Long tradeId;
  private String tradeTitle;
  private Integer tradePrice;
  private String tradeStatus;
  private String tradeThumbnailUrl;

  private Boolean isSeller;

  private String lastMessageContent;
  private LocalDateTime lastMessageTime;

  private Integer unreadCount;

}
