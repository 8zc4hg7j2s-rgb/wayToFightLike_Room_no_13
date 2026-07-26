package nitin.gupta.room.no13.newChallenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
}
