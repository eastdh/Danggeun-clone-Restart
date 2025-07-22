package io.github.restart.gmo_danggeun.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id", nullable = false)
  private ChatRoom chatRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id", nullable = false)
  private User writer;

  @Column(length = 200)
  private String content;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "read_or_not", nullable = false)
  private Boolean readOrNot;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatMessage that = (ChatMessage) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}