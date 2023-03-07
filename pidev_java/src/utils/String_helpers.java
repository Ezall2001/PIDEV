package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class String_helpers {
  public static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
  }

  public static String check_bad_word(String paragraph) {
    try {
      Path fileName = Paths.get("public/local_data/bad_words.txt");

      List<String> bad_words = Files.readAllLines(fileName);
      String bad_words_pattern = String.format("(%s)", String.join("|", bad_words));

      Pattern pattern = Pattern.compile(bad_words_pattern, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(paragraph);

      if (matcher.find()) {
        Integer start_index = matcher.start();
        Integer end_index = matcher.end();

        return paragraph.substring(start_index, end_index);
      }

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return null;

  }
}
