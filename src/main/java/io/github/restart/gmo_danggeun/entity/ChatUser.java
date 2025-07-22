package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.ChatUserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_user")
@IdClass(ChatUserId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUser {

  @Id
  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

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