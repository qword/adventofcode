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


public class Day4 {

  private static final Logger LOGGER = Logger.getLogger(Day4.class.getName());

  public static void main(String[] args) throws IOException {

    final List<String> input;
    try (Stream<String> stream = Files.lines( Paths.get("input/2022/day4"), UTF_8)) {
      input = stream.collect(Collectors.toList());
    }
    
    long completeOverlaps = 0;
    long partialOverlap = 0;
    
    Iterator<String> iterator = input.iterator();
    while (iterator.hasNext()) {
      final String[] assignments = iterator.next().split(",");
      long start1 = Long.parseLong(assignments[0].split("-")[0]);
      long end1 = Long.parseLong(assignments[0].split("-")[1]);
      long start2 = Long.parseLong(assignments[1].split("-")[0]);
      long end2 = Long.parseLong(assignments[1].split("-")[1]);

      if ((start1 <= start2 && end1 >= end2) || (start1 >= start2 && end1 <= end2)) completeOverlaps++;
      if (start1 <= end2 && start2 <= end1) partialOverlap++;
    }

    LOGGER.log(INFO, "Part 1: {0}", Long.toString(completeOverlaps));
    LOGGER.log(INFO, "Part 2: {0}", Long.toString(partialOverlap));
  }
}
