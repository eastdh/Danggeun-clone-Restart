package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.ImageChatId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "image_chat")
@IdClass(ImageChatId.class)
public class ImageChat {

  @Id
  @ManyToOne
  @JoinColumn(name = "message_id")
  private ChatMessage message;

  @Id
  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  public ImageChat() {
  }

  public ImageChat(ChatMessage message, Image image) {
    this.message = message;
    this.image = image;
  }

  public ChatMessage getMessage() {
    return message;
  }

  public void setMessage(ChatMessage message) {
    this.message = message;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageChat imageChat = (ImageChat) o;
    return Objects.equals(message, imageChat.message) && Objects.equals(image, imageChat.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, image);
  }
}