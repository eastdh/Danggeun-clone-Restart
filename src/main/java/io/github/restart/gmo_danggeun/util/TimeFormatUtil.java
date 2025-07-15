package io.github.restart.gmo_danggeun.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeFormatUtil {

  private static final DateTimeFormatter CHAT_FORMATTER = DateTimeFormatter.ofPattern("a h:mm"); // 오전 3:13
  private static final DateTimeFormatter EXACT_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");

  /**
   * 채팅방용 시간 포맷 ("오전 3:13")
   */
  public static String formatForChat(LocalDateTime time) {
    return time.format(CHAT_FORMATTER);
  }

  /**
   * 경과 시간 표시 ("5분 전", "3시간 전", "2일 전", ...)
   */
  public static String formatElapsedTime(LocalDateTime past) {
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    long minutes = ChronoUnit.MINUTES.between(past, now);
    long hours = ChronoUnit.HOURS.between(past, now);
    long days = ChronoUnit.DAYS.between(past, now);
    long weeks = days / 7;
    long months = days / 30;
    long years = days / 365;

    if (minutes < 1) return "방금 전";
    if (minutes < 60) return minutes + "분 전";
    if (hours < 24) return hours + "시간 전";
    if (days < 7) return days + "일 전";
    if (weeks < 5) return weeks + "주 전";
    if (months < 12) return months + "달 전";
    return years + "년 전";
  }

  /**
   * 정확한 날짜 포맷 ("2025년 07월 15일 16시 04분")
   */
  public static String formatExact(LocalDateTime time) {
    return time.format(EXACT_FORMATTER);
  }
}
