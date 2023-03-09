package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import profanityUtility.Profinity;

public class String_helpers {
  public static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
  }

  public static String check_bad_word(String paragraph) {
    try {
      return Profinity.checkWord(paragraph);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    return null;
  }
}
