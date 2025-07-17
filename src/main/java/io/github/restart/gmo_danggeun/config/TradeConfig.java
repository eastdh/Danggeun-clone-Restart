package io.github.restart.gmo_danggeun.config;

import java.util.Set;

public class TradeConfig {
  public static final int TRADELIST_PAGE_SIZE = 10;
  public static final int TRADEDETAIL_USER_TRADE_PAGE_SIZE = 10;
  public static final int TRADEDETAIL_CATEGORY_TRADE_PAGE_SIZE = 15;
  public static final String DEFAULT_LOCATION = "서울특별시 종로구";
  public static final Set<String> newTradeNullable = Set.of("id", "preferredLocation", "bumpCount", "status", "hidden", "createdAt", "updatedAt", "bumpUpdatedAt", "files");
  public static final Set<String> editTradeNullable = Set.of("preferredLocation", "bumpCount", "status", "hidden", "createdAt", "updatedAt", "bumpUpdatedAt", "files");
}
