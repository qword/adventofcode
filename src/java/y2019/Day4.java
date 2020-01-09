package y2019;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Definitely too many repeated blocks
 * To be addressed when rewriting to Scala
 */
public class Day4 {

  private static final Logger LOGGER = Logger.getLogger(Day4.class.getName());

  public static void main(String[] args) throws IOException {
    final String input;
    try (Stream<String> stream = Files.lines(Paths.get("input/2019/day4"), UTF_8)) {
      input = stream.findFirst().orElse("");
    }

    final int from = Integer.parseInt(input.split("-")[0]);
    final int to = Integer.parseInt(input.split("-")[1]);

    doPartA(from, to);
    doPartB(from, to);
  }

  private static void doPartA(int from, int to) {
    int resultA = 0;
    for (int i = from; i <= to; i++) {
      String num = i + "";

      char c = num.charAt(0);
      boolean neverDecreases = true;
      boolean containsAdjacentRepetition = false;
      for (int j = 1; j < 6; j++) {
        if (num.charAt(j) == c)
          containsAdjacentRepetition = true;
        if (num.charAt(j) < c)
          neverDecreases = false;
        c = num.charAt(j);
      }

      if (neverDecreases && containsAdjacentRepetition) {
        resultA++;
        LOGGER.log(FINE, "Found valid code: {0}", num);
      }
    }
    LOGGER.log(INFO, "Part A: {0}", Integer.toString(resultA)); // 1154
  }

  private static void doPartB(int from, int to) {
    int resultB = 0;
    for (int i=from;i<=to;i++){
      String num = i + "";

      char c = num.charAt(0);
      boolean neverDecreases = true;
      for (int j=1;j<6;j++) {
        if (num.charAt(j) < c) neverDecreases = false;
        c = num.charAt(j);
      }

      boolean containsOneDouble = false;
      Pattern p = Pattern.compile("(\\d)\\1+");
      Matcher m = p.matcher(num);

      while(m.find())
        if (m.group().length() == 2)
          containsOneDouble = true;

      if (neverDecreases && containsOneDouble) {
        resultB++;
        LOGGER.log(FINE, "Found valid code: {0}", num);
      }
    }

    LOGGER.log(INFO, "Part B: {0}", Integer.toString(resultB)); // 750
  }
}
