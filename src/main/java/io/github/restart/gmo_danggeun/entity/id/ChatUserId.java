package io.github.restart.gmo_danggeun.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class ChatUserId implements Serializable {

  private Long chatRoom;
  private Long user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ChatUserId that = (ChatUserId) o;
    return Objects.equals(chatRoom, that.chatRoom) &&
        Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatRoom, user);
  }
}