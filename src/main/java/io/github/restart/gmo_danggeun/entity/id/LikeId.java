package io.github.restart.gmo_danggeun.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class LikeId implements Serializable {

  private Long user;
  private Long trade;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LikeId that = (LikeId) o;
    return Objects.equals(user, that.user) &&
        Objects.equals(trade, that.trade);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, trade);
  }
}