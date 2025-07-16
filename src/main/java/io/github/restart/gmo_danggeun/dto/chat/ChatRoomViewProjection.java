package io.github.restart.gmo_danggeun.dto.chat;

import java.time.LocalDateTime;

public interface ChatRoomViewProjection {

  Long getMeId();
  String getMeNickname();

  Long getPartnerId();
  String getPartnerNickname();
  String getPartnerLocation();
  Double getPartnerTemperature();

  Long getChatRoomId();
  Long getTradeId();
  String getTradeTitle();
  Integer getTradePrice();
  String getTradeStatus();
  String getTradeThumbnailUrl();

  String getLastMessageContent();
  LocalDateTime getLastMessageTime();

  Integer getUnreadCount(); // 혹은 int
}
