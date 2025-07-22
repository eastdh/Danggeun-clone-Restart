package io.github.restart.gmo_danggeun.dto.trade;

public class TradeRequestDto {
  private int page;
  private String keyword;
  private String location;
  private String status;
  private String category;
  private String priceRange;

  public TradeRequestDto() {
  }

  public TradeRequestDto(int page, String keyword, String location, String status, String category,
      String priceRange) {
    this.page = page;
    this.keyword = keyword;
    this.location = location;
    this.status = status;
    this.category = category;
    this.priceRange = priceRange;
  }

  public int getPage() {
    return page;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getLocation() {
    return location;
  }

  public String getStatus() {
    return status;
  }

  public String getCategory() {
    return category;
  }

  public String getPriceRange() {
    return priceRange;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
  }
}
