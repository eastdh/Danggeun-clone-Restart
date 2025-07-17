package io.github.restart.gmo_danggeun.util;

public class LocationUtil {

  /**
   * 지역 문자열에서 '구' 또는 '시' 단위를 추출하여 반환
   * @param location 전체 지역 문자열 (예: "서울특별시 강남구 삼성동")
   * @return 대표 지역 단위 (예: "강남구", "서울특별시", "삼성동")
   */
  public static String extractRepresentativeLocation(String location) {
    if (location == null || location.isBlank()) {
      return "지역정보 없음";
    }

    String[] tokens = location.trim().split("\\s+");

    // 1. '구'로 끝나는 어절 찾기
    for (String token : tokens) {
      if (token.endsWith("구")) {
        return token;
      }
    }

    // 2. '시'로 끝나는 어절 찾기
    for (String token : tokens) {
      if (token.endsWith("시")) {
        return token;
      }
    }

    // 3. 마지막 어절 반환
    return tokens[tokens.length - 1];
  }
}