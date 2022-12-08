package y2022;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day3 {

  private static final Logger LOGGER = Logger.getLogger(Day3.class.getName());

  public static void main(String[] args) throws IOException {

    final List<String> input;
    try (Stream<String> stream = Files.lines( Paths.get("input/2022/day3"), UTF_8)) {
      input = stream.collect(Collectors.toList());
    }
    
    long result1 = 0;
    
    Iterator<String> iterator1 = input.iterator();
    while (iterator1.hasNext()) {
      List<Long> list = iterator1.next().chars().asLongStream()
      .map(l -> { 
        if (l > 96) return l - 96; else return l - 38;
      }).boxed().collect(Collectors.toList());
      
      Set<Long> backpack1 = new HashSet<>();
      Set<Long> backpack2 = new HashSet<>();
      for (int i = 0; i < list.size(); i++) {
        if (i < list.size() / 2) {
          backpack1.add(list.get(i));
        } else {
          backpack2.add(list.get(i));
        }
      }

      backpack1.retainAll(backpack2);
      Long intersection = backpack1.iterator().next();
      result1 += intersection;
    }

    long result2 = 0;
    
    for (int i = 0; i < input.size() / 3; i++) {
      
      String threeBackpacks = "";
      
      for (int j = 0; j < 3; j++) {
        Set<Character> set = new LinkedHashSet<Character>();
        for(char c : input.get(3 * i + j).toCharArray()) {
            set.add(Character.valueOf(c));
        }

        threeBackpacks += set.stream().map(String::valueOf).collect(Collectors.joining());
      }
           
      long found = threeBackpacks.chars().asLongStream()
      .map(l -> { 
        if (l > 96) return l - 96; else return l - 38;
      }).boxed()
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
      .filter(entry -> entry.getValue() == 3).findFirst().get().getKey();
       
      result2 += found;
    }

    LOGGER.log(INFO, "Part 1: {0}", Long.toString(result1));
    LOGGER.log(INFO, "Part 2: {0}", Long.toString(result2));
  }
}
