package io.github.restart.gmo_danggeun.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chat_room")
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "trade_id", nullable = false)
  private Trade trade;

  public ChatRoom() {
  }

  public ChatRoom(Long id, Trade trade) {
    this.id = id;
    this.trade = trade;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    ChatRoom chatRoom = (ChatRoom) o;
    return Objects.equals(id, chatRoom.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}