package io.github.restart.gmo_danggeun.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class SecurityUtil {
  private static final Pattern SCRIPT_PATTERN = Pattern.compile("(?i)<script[^>]*>.*?</script>");
  private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile("(?i).*(union|select|insert|update|delete|drop|create|alter|exec|execute).*");

  private static String escapeHtml(String input) {
    if (input == null) return null;

    return input.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#x27;")
        .replace("/", "&#x2F;");
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
    input = escapeHtml(input);

    return input;
  }

  public static boolean anyNull(Object target, Set<String> excludeFieldNames) {
    return Arrays.stream(target.getClass().getDeclaredFields())
        .filter(f -> excludeFieldNames == null || !excludeFieldNames.contains(f.getName()))
        .peek(f -> f.setAccessible(true))
        .map(f -> getFieldValue(f, target))
        .anyMatch(Objects::isNull);
  }

  private static Object getFieldValue(Field field, Object target) {
    try {
      return field.get(target);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

}
