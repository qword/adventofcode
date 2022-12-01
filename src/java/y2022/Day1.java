package y2022;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day1 {

  private static final Logger LOGGER = Logger.getLogger(Day1.class.getName());

  public static void main(String[] args) throws IOException {
    final List<String> input;
    try (Stream<String> stream = Files.lines( Paths.get("input/2022/day1"), UTF_8)) {
      input = stream.collect(Collectors.toList());
    }

    int currentMax = 0;
    int current = 0;

    Iterator<String> iterator = input.iterator();

    List<Integer> backpacks = new ArrayList<>();

    while (iterator.hasNext()) {
      String next = iterator.next();

      if (next.isEmpty()) {
        if (current > currentMax) {
          currentMax = current;
        }

        backpacks.add(current);
        current = 0;
      } else {
        current += Integer.parseInt(next);
      }

    }

    LOGGER.log(INFO, "Part 1: {0}", Integer.toString(currentMax));

    int part2 = backpacks.stream()
    .sorted(Comparator.reverseOrder())
    .limit(3)
    .collect(Collectors.summingInt(Integer::intValue));
  
    LOGGER.log(INFO, "Part 2: {0}", part2);
  }
}
