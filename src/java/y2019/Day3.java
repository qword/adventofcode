package y2019;

import static java.lang.Math.abs;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Definitely too many repeated blocks
 * To be addressed when rewriting to Scala
 */
public class Day3 {

  private static final Logger LOGGER = Logger.getLogger(Day3.class.getName());

  public static void main(String[] args) throws IOException {
    final List<String> input;
    try (Stream<String> stream = Files.lines( Paths.get("input/2019/day3"), UTF_8)) {
      input = stream.collect(Collectors.toList());
    }

    Set<String> intersections = getIntersections(input); // also solves part A
    int minDistance = Integer.MAX_VALUE;
    for (final String intersection : intersections) {

      // run both wires up to intersection, measure distance travelled, get minimum => done part B

      int distanceWire1 = getTravellingDistanceTo(input.get(0).split(","), intersection);
      int distanceWire2 = getTravellingDistanceTo(input.get(1).split(","), intersection);
      int totDistance = distanceWire1 + distanceWire2;
      if (totDistance < minDistance) minDistance = totDistance;
    }
    LOGGER.log(INFO, "Part B: {0}", Integer.toString(minDistance)); // 1519
  }

  static Set<String> getIntersections(final List<String> input) {
    final String[] wire1 = input.get(0).split(",");

    final Set<String> layout = new HashSet<>();
    int curX = 0;
    int curY = 0;
    for (String move: wire1) {
      int length = Integer.parseInt(move.substring(1));
      char direction = move.charAt(0);
      while (length > 0) {
        switch (direction) {
          case 'U': curY--; break;
          case 'D': curY++; break;
          case 'L': curX--; break;
          case 'R': curX++; break;
        }
        layout.add(curX + "," + curY);
        length--;
      }
    } // move wire 1

    final String[] wire2 = input.get(1).split(",");
    curX = 0;
    curY = 0;
    int minDistance = Integer.MAX_VALUE;
    Set<String> intersections = new HashSet<>();
    for (String move: wire2) {
      int length = Integer.parseInt(move.substring(1));
      char direction = move.charAt(0);
      while (length > 0) {
        switch (direction) {
          case 'U': curY--; break;
          case 'D': curY++; break;
          case 'L': curX--; break;
          case 'R': curX++; break;
        }
        final String nextPosition = curX + "," + curY;
        if (layout.contains(nextPosition)) { // intersection met!
          intersections.add(nextPosition);
          int currentDistance = abs(curX) + abs(curY);
          if (currentDistance < minDistance) minDistance = currentDistance;
        }
        length--;
      }
    } // move wire 2

    LOGGER.log(INFO, "Part A: {0}", Integer.toString(minDistance)); // 1519
    return intersections;
  }

  static int getTravellingDistanceTo(final String[] moves, final String intersection) {
    int curX = 0;
    int curY = 0;
    int distance = 0;
    for (String move: moves) {
      int length = Integer.parseInt(move.substring(1));
      char direction = move.charAt(0);
      while (length > 0) {
        switch (direction) {
          case 'U': curY--; break;
          case 'D': curY++; break;
          case 'L': curX--; break;
          case 'R': curX++; break;
        }
        length--;
        distance++;

        final String position = curX + "," + curY;
        if (position.equals(intersection)) { // intersection met!
          return distance;
        }
      }
    } // move wire
    return -1;
  }
}
