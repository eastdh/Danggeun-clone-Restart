package io.github.restart.gmo_danggeun.dto.chat;

import java.util.List;

/** 채팅 페이지를 총괄하는 DTO */
public class ChatPageDto {

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

  public ChatPageDto() {
  }

  public ChatPageDto(Long userId, String userNickname, boolean showUnreadOnly, List<ChatRoomSummaryDto> chatRooms,
      boolean isSeller, ChatRoomDetailDto selectedRoom, List<ChatMessageDto> messages) {
    this.userId = userId;
    this.userNickname = userNickname;
    this.showUnreadOnly = showUnreadOnly;
    this.chatRooms = chatRooms;
    this.isSeller = isSeller;
    this.selectedRoom = selectedRoom;
    this.messages = messages;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserNickname() {
    return userNickname;
  }

  public void setUserNickname(String userNickname) {
    this.userNickname = userNickname;
  }

  public boolean isShowUnreadOnly() {
    return showUnreadOnly;
  }

  public void setShowUnreadOnly(boolean showUnreadOnly) {
    this.showUnreadOnly = showUnreadOnly;
  }

  public List<ChatRoomSummaryDto> getChatRooms() {
    return chatRooms;
  }

  public void setChatRooms(List<ChatRoomSummaryDto> chatRooms) {
    this.chatRooms = chatRooms;
  }

  public boolean isSeller() {
    return isSeller;
  }

  public void setSeller(boolean seller) {
    isSeller = seller;
  }

  public ChatRoomDetailDto getSelectedRoom() {
    return selectedRoom;
  }

  public void setSelectedRoom(ChatRoomDetailDto selectedRoom) {
    this.selectedRoom = selectedRoom;
  }

  public List<ChatMessageDto> getMessages() {
    return messages;
  }

  public void setMessages(List<ChatMessageDto> messages) {
    this.messages = messages;
  }
}
