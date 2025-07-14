package io.github.restart.gmo_danggeun.dto;

public class FilterDto {
  private String category;
  private int priceLowLimit;
  private int priceHighLimit;

  public FilterDto() {
  }

  public FilterDto(String category, int priceLowLimit, int priceHighLimit) {
    this.category = category;
    this.priceLowLimit = priceLowLimit;
    this.priceHighLimit = priceHighLimit;
  }

  public String getCategory() {
    return category;
  }

  public int getPriceLowLimit() {
    return priceLowLimit;
  }

  public int getPriceHighLimit() {
    return priceHighLimit;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPriceLowLimit(int priceLowLimit) {
    this.priceLowLimit = priceLowLimit;
  }

  public void setPriceHighLimit(int priceHighLimit) {
    this.priceHighLimit = priceHighLimit;
  }
}
