package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.LikeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "\"like\"")
@IdClass(LikeId.class)
public class Like {

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "trade_id")
  private Trade trade;

  public Like() {
  }

  public Like(User user, Trade trade) {
    this.user = user;
    this.trade = trade;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Trade getTrade() {
    return trade;
  }

  public void setTrade(Trade trade) {
    this.trade = trade;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Like like = (Like) o;
    return Objects.equals(user, like.user) && Objects.equals(trade, like.trade);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, trade);
  }
}
