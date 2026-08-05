package nitin.gupta.room.no13.newChallenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordCountInFile {
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("src/main/java/com/nitin/gupta/newchallange/FindOutMaxLengthSubStringWithoutRepeatingCharacter.java");
        LinkedHashMap<String, Long> map = Files.lines(file)
                .flatMap(String::lines)
                .map(e -> e.split("[ .;::()]"))
                 .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        map.entrySet().stream().forEach(System.out::println);
    }

    public static int[] merge(int[] foo, int[] bar) {
        int[] result = new int[foo.length + bar.length];
        int i = 0, j = 0, k = 0;
        // Compare elements from both arrays and insert the smaller one
        while (i < foo.length && j < bar.length) {
            if (foo[i] <= bar[j]) {
                result[k++] = foo[i++];
            } else {
                result[k++] = bar[j++];
            }
        }
        // Copy remaining elements from foo, if any
        while (i < foo.length) {
            result[k++] = foo[i++];
        }
        // Copy remaining elements from bar, if any
        while (j < bar.length) {
            result[k++] = bar[j++];
        }
        return result;
    }
    public static int[] merge1(int[] foo, int[] bar) {
        int[] result = new int[foo.length + bar.length];
        System.arraycopy(foo, 0, result, 0, foo.length);
        System.arraycopy(bar, 0, result, foo.length, bar.length);
        return result;
    }
}
