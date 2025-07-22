package io.github.restart.gmo_danggeun.util;

public class DateUtil {
  public static String getMaxDateTerm(String dateFormat) {
    String result = "";
    if (dateFormat == null || dateFormat.isEmpty()) {
      return "";
    }

    String date = dateFormat.split(" ")[0];
    String year = date.split("-")[0];
    String month = date.split("-")[1];
    String day = date.split("-")[2];
    String time = dateFormat.split(" ")[1];
    String hour = time.split(":")[0];
    String minute = time.split(":")[1];

    if (!year.equals("00")) {
      result = Integer.parseInt(year) + "년 전";
    } else if (!month.equals("00")) {
      result = Integer.parseInt(month) + "개월 전";
    } else if (!day.equals("00")) {
      result = Integer.parseInt(day) + "일 전";
    } else if (!hour.equals("00")) {
      result = Integer.parseInt(hour) + "시간 전";
    } else if (!minute.equals("00")) {
      result = Integer.parseInt(minute) + "분 전";
    }

    return result;
  }
}
