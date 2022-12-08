package y2022;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day2 {

  private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());

  public static void main(String[] args) throws IOException {

    
    final List<String> input;
    try (Stream<String> stream = Files.lines( Paths.get("input/2022/day2"), UTF_8)) {
      input = stream.collect(Collectors.toList());
    }

    long score1 = 0;
    long score2 = 0;
    Iterator<String> iterator1 = input.iterator();

    while (iterator1.hasNext()) {
      String[] tokens = iterator1.next().split(" ");

      switch (tokens[0]) {
        case "A": // rock
          switch (tokens[1]) {
            case "X": score1 += 3 + 1; score2 += 0 + 3; break;
            case "Y": score1 += 6 + 2; score2 += 3 + 1; break;
            case "Z": score1 += 0 + 3; score2 += 6 + 2; break;
          }
        break;

        case "B": // paper
          switch (tokens[1]) {
            case "X": score1 += 0 + 1; score2 += 0 + 1; break;
            case "Y": score1 += 3 + 2; score2 += 3 + 2; break;
            case "Z": score1 += 6 + 3; score2 += 6 + 3; break;
          }
        break;

        case "C": // scissors
          switch (tokens[1]) {
            case "X": score1 += 6 + 1; score2 += 0 + 2; break;
            case "Y": score1 += 0 + 2; score2 += 3 + 3; break;
            case "Z": score1 += 3 + 3; score2 += 6 + 1; break;
          }
        break;
      }
    }

    LOGGER.log(INFO, "Part 1: {0}", Long.toString(score1));
    LOGGER.log(INFO, "Part 2: {0}", Long.toString(score2));
  }
}
