package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.ChatUserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chat_user")
@IdClass(ChatUserId.class)
public class ChatUser {

  @Id
  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public ChatUser() {
  }

  public ChatUser(ChatRoom chatRoom, User user) {
    this.chatRoom = chatRoom;
    this.user = user;
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatUser chatUser = (ChatUser) o;
    return Objects.equals(chatRoom, chatUser.chatRoom) && Objects.equals(user, chatUser.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatRoom, user);
  }
}