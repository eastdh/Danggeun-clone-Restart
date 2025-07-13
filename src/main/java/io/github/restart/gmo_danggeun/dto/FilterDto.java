package io.github.restart.gmo_danggeun.dto;

public class FilterDto {
  private String keyword;
  private String location;
  private String category;
  private int priceLowLimit;
  private int priceHighLimit;

  public FilterDto() {
  }

  public FilterDto(String keyword, String location, String category, int priceLowLimit,
      int priceHighLimit) {
    this.keyword = keyword;
    this.location = location;
    this.category = category;
    this.priceLowLimit = priceLowLimit;
    this.priceHighLimit = priceHighLimit;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getLocation() {
    return location;
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

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public void setLocation(String location) {
    this.location = location;
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
