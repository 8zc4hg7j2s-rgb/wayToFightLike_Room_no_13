package nitin.gupta.room.no13.coreJavaPrectise;

import nitin.gupta.room.no13.utils.CsvReader;
import nitin.gupta.room.no13.utils.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PracticeMakeManPerfect {
    static void main() throws URISyntaxException {

        String fileName = "people-100.csv";

        try {
            List<Person> people = CsvReader.readPeopleCsv();

            System.out.println("Successfully loaded " + people.size() + " records from resources!\n");

            // Display the first 3 records
            people.stream().limit(3).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

    }
}
