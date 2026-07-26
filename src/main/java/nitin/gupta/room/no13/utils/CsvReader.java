package nitin.gupta.room.no13.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {

    public static List<Person> readPeopleCsv() throws IOException, URISyntaxException {
        URL resourceUrl = CsvReader.class
                .getClassLoader()
                .getResource("people-100.csv");
        Path path = Paths.get(resourceUrl.toURI());

        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .skip(1) // Skip header row
                    .filter(line -> !line.isBlank())
                    .map(CsvReader::parseLineToPerson)
                    .toList(); // Java 16+ / Java 21 unmodifiable list collector
        }
    }

    private static Person parseLineToPerson(String line) {
        // Regex handles commas while respecting basic quoted CSV fields
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        return new Person(
                Integer.parseInt(fields[0].trim()),
                cleanField(fields[1]),
                cleanField(fields[2]),
                cleanField(fields[3]),
                cleanField(fields[4]),
                cleanField(fields[5]),
                cleanField(fields[6]),
                LocalDate.parse(cleanField(fields[7])),
                cleanField(fields[8]),
                cleanField(fields[9])
        );
    }

    private static String cleanField(String field) {
        // Removes quotes wrapping fields containing commas (e.g. "Software Engineer, Senior")
        return field.trim().replaceAll("^\"|\"$", "");
    }
}