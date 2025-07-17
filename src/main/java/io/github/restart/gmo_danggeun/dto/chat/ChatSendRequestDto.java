package io.github.restart.gmo_danggeun.dto.chat;

public class ChatSendRequestDto {

  private Long chatRoomId;   // 메시지를 보낼 채팅방 ID
  private Long senderId;     // 메시지를 보낸 사용자 ID
  private String content;    // 메시지 내용

  public ChatSendRequestDto() {
  }

  public ChatSendRequestDto(Long chatRoomId, Long senderId, String content) {
    this.chatRoomId = chatRoomId;
    this.senderId = senderId;
    this.content = content;
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
