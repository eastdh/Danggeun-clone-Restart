package io.github.restart.gmo_danggeun.util;

public class UserMannerUtil {
  public static String setEmoji(double mannerScore) {
    String emojiFileName = "";
    if (mannerScore >= 80) {
      emojiFileName = "emoji-grin-fill-orange.svg";
    } else if (mannerScore >= 60) {
      emojiFileName = "emoji-sunglasses-fill-yellow.svg";
    } else if (mannerScore >= 40) {
      emojiFileName = "emoji-smile-fill-green.svg";
    } else if (mannerScore >= 20) {
      emojiFileName = "emoji-frown-fill-skyblue.svg";
    } else {
      emojiFileName = "emoji-expressionless-fill-purple.svg";
    }
    return emojiFileName;
  }
}
