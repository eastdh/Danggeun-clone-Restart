package io.github.restart.gmo_danggeun.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class ImageTradeId implements Serializable {

  private Long trade;
  private Long image;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ImageTradeId that = (ImageTradeId) o;
    return Objects.equals(trade, that.trade) &&
        Objects.equals(image, that.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trade, image);
  }
}