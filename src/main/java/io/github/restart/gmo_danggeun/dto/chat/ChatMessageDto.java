package io.github.restart.gmo_danggeun.dto.chat;

public class ChatMessageDto {

  private Long messageId;
  private Long senderId;
  private String senderType; // "ME" 또는 "PARTNER"
  private String content;
  private String timestamp;
  private boolean isRead;
  private String messageType = "TEXT";  // 🔥 선택: TEXT, IMAGE, SYSTEM 등


  public ChatMessageDto() {
  }

  public ChatMessageDto(Long messageId, Long senderId, String senderType, String content, String timestamp,
      boolean isRead, String messageType) {
    this.messageId = messageId;
    this.senderId = senderId;
    this.senderType = senderType;
    this.content = content;
    this.timestamp = timestamp;
    this.isRead = isRead;
    this.messageType = messageType;
  }

  public Long getMessageId() {
    return messageId;
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public String getSenderType() {
    return senderType;
  }

  public void setSenderType(String senderType) {
    this.senderType = senderType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean read) {
    isRead = read;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }
}