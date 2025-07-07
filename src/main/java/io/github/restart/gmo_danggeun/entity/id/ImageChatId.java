package io.github.restart.gmo_danggeun.entity.id;
import java.io.Serializable;
import java.util.Objects;

public class ImageChatId implements Serializable {

  private Long message;
  private Long image;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ImageChatId that = (ImageChatId) o;
    return Objects.equals(message, that.message) &&
        Objects.equals(image, that.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, image);
  }
}