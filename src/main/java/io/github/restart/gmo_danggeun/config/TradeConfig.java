package io.github.restart.gmo_danggeun.config;

import java.util.EnumMap;

public class TradeConfig {
  public static final int TRADELIST_PAGE_SIZE = 10;
  public static final int TRADEDETAIL_USER_TRADE_PAGE_SIZE = 10;
  public static final int TRADEDETAIL_CATEGORY_TRADE_PAGE_SIZE = 15;
  public static final String DEFAULT_LOCATION = "서울특별시 종로구";

  public enum Status {
    AVAILABLE,
    COMPLETED,
    RESERVED;

    public static Status compareName(String input) {
      if (input == null) return null;
      String normalized = input.trim().toUpperCase();
      for (Status s : values()) {
        if (s.name().toUpperCase().equals(normalized))
          return s;
      }
      return null;
    }
  }

  private static final EnumMap<Status, String> STATUS_MAP = new EnumMap<>(Status.class);

  static {
    STATUS_MAP.put(Status.AVAILABLE, "거래 가능");
    STATUS_MAP.put(Status.COMPLETED, "거래 완료");
    STATUS_MAP.put(Status.RESERVED, "예약됨");
  }

  public static EnumMap<Status, String> getStatusMap() {
    return STATUS_MAP;
  }

  public static boolean isCorrectStatus(String input) {
    if (input == null) return false;
    return Status.compareName(input) != null;
  }
}
