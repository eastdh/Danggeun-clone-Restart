package io.github.restart.gmo_danggeun.util;

import java.util.regex.Pattern;
import org.springframework.web.util.HtmlUtils;

public class SecurityUtil {
  private static final Pattern SCRIPT_PATTERN = Pattern.compile("(?i)<script[^>]*>.*?</script>");
  private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile("(?i).*(union|select|insert|update|delete|drop|create|alter|exec|execute).*");

  private static String htmlEscape(String input) {
    if (input == null) return null;
    return HtmlUtils.htmlEscape(input);
  }

  private static String removeScripts(String input) {
    if (input == null) return null;
    return SCRIPT_PATTERN.matcher(input).replaceAll("");
  }

  private static boolean containsSqlInjection(String input) {
    if (input == null) return false;
    return SQL_INJECTION_PATTERN.matcher(input).matches();
  }

  public static String sanitizeInput(String input) {
    if (input == null) return null;

    input = removeScripts(input);
    input = htmlEscape(input);

    return input;
  }

  public static String htmlUnescape(String input) {
    if (input == null) return null;
    return HtmlUtils.htmlUnescape(input);
  }
}
