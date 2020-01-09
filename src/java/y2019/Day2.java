package y2019;

import static java.lang.System.exit;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Day2 {

  private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());
  private static final int TARGET_RESULT_PART_B = 19690720;

  public static void main(String[] args) throws IOException {
    String input;
    try (Stream<String> stream = Files.lines( Paths.get("input/2019/day2"), UTF_8)) {
      input = stream.findFirst().orElse("");
    }

    // Part A, simple direct execution
    int resultPartA = exec(getInitialProgram(input));
    LOGGER.log(INFO, "Part A: {0}", Integer.toString(resultPartA)); // 6327510

    /*
       Part B:
         set noun (i) = 0, change verb (j) brings increments of 1
         set verb (j) = 0, change noun (i) brings increments of 460800 starting from 797908

       Solution is to be searched in the equation
         19690720 = 797908 + 460800 * noun + 1 * verb
       so
         noun = (19690720 - 797908) / 460800
         verb = (19690720 - (797908 + 460800 * noun)) / 1
       hence
         noun = 41
         verb = 12
       answer is 4112
     */

    final int[] programWithNounIncreased = getInitialProgram(input);
    programWithNounIncreased[1] = programWithNounIncreased[1] + 1;
    int deltaNoun = exec(programWithNounIncreased) - resultPartA;

    final int[] programWithVerbIncreased = getInitialProgram(input);
    programWithVerbIncreased[2] = programWithVerbIncreased[2] + 1;
    int deltaVerb = exec(programWithVerbIncreased) - resultPartA;

    // TODO: 797908 should be calculated as well
    int noun = (TARGET_RESULT_PART_B - 797908) / deltaNoun;
    int verb = (TARGET_RESULT_PART_B - (797908 + deltaNoun * noun)) / deltaVerb;

    LOGGER.log(INFO, "Part B: {0}", Integer.toString(noun * 100 + verb)); // 4112
  }

  private static int[] getInitialProgram(String input) {
    return Arrays.stream(input.split(","))
          .mapToInt(Integer::parseInt)
          .toArray();
  }

  private static int exec(int[] program) {
    int command;
    int curPos = 0;

    while (true) {
      command = program[curPos];
      int result = 0;
      if (command == 1) {
        result = program[program[curPos + 1]] + program[program[curPos + 2]];
      } else if (command == 2) {
        result = program[program[curPos + 1]] * program[program[curPos + 2]];
      } else if (command == 99) {
        return program[0];
      } else {
        exit(-1);
      }

      program[program[curPos + 3]] = result;
      curPos += 4;

      show(program);
    }
  }

  private static void show(int[] program) {
    StringJoiner j = new StringJoiner(",");
    Arrays.stream(program).forEach(i -> j.add(Integer.toString(i)));
    LOGGER.log(FINE, j.toString());
  }
}
