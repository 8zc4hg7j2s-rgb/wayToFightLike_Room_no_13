package nitin.gupta.room.no13.newChallenge;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * * find out elements ,who have length is less than 5 characters
 * * and print in upper case insertion order must be preserved  .
 * * redundant element must not print.
 *
 */

public class EPAMTest {
    static void main() {
        String str = "New York City NYC, often simply called New York is" +
                " the most populous city in the United States";
        Arrays.stream(str.split(" "))
                .collect(Collectors.groupingBy(Function.identity()
                        , LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getKey().length() < 5)
                .distinct()
                .map(e -> e.getKey()).forEach(System.out::println);
    }
}
