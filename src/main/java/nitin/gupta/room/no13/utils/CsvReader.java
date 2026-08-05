package nitin.gupta.room.no13.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader {

    public static List<Person> readPeopleCsv() throws IOException, URISyntaxException {
        URL resourceUrl = CsvReader.class.getClassLoader().getResource("people-100.csv");
        Path path = Paths.get(resourceUrl.toURI());

        try (Stream<String> lines = Files.lines(path)) {
            return lines.skip(1) // Skip header row
                    .filter(line -> !line.isBlank()).map(CsvReader::parseLineToPerson).toList(); // Java 16+ / Java 21 unmodifiable list collector
        }
    }

    public static List<Employee> readEmployeeCsv() throws IOException, URISyntaxException {
        URL resourceUrl = CsvReader.class.getClassLoader().getResource("Employee-100.csv");
        Path path = Paths.get(resourceUrl.toURI());

        try (Stream<String> lines = Files.lines(path)) {
            return lines.skip(1) // Skip header row
                    .filter(line -> !line.isBlank()).map(CsvReader::parseLineToEmployee).toList(); // Java 16+ / Java 21 unmodifiable list collector
        }
    }

    private static Employee parseLineToEmployee(String line) {
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        return new Employee(Integer.parseInt(fields[0].trim()), cleanField(fields[1]), cleanField(fields[2]), Integer.parseInt(fields[3].trim()), cleanField(fields[4]));
    }

    private static Person parseLineToPerson(String line) {
        // Regex handles commas while respecting basic quoted CSV fields
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        return new Person(Integer.parseInt(fields[0].trim()), cleanField(fields[1]), cleanField(fields[2]), cleanField(fields[3]), cleanField(fields[4]), cleanField(fields[5]), cleanField(fields[6]), LocalDate.parse(cleanField(fields[7])), cleanField(fields[8]), cleanField(fields[9]));
    }

    private static String cleanField(String field) {
        // Removes quotes wrapping fields containing commas (e.g. "Software Engineer, Senior")
        return field.trim().replaceAll("^\"|\"$", "");
    }

    // Method to read Products CSV
    public static List<Product> readProductsCsv() {
        List<Product> products = new ArrayList<>();
        String filePath = "src/main/resources/product-10.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Product product = new Product(Integer.parseInt(values[0].trim()), values[1].trim(), values[2].trim(), Double.parseDouble(values[3].trim()), Integer.parseInt(values[4].trim()));
                products.add(product);
            }
        } catch (IOException e) {
            System.err.println("Error reading products file: " + e.getMessage());
        }
        return products;
    }

    // Method to read Orders CSV
    public static List<Order> readOrdersCsv() {
        List<Order> orders = new ArrayList<>();
        String filePath = "src/main/resources/order-10.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Order order = new Order(Integer.parseInt(values[0].trim()), LocalDate.parse(values[1].trim()), // Parses YYYY-MM-DD
                        Integer.parseInt(values[2].trim()), Integer.parseInt(values[3].trim()), values[4].trim());
                orders.add(order);
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
        return orders;
    }

    public static Map<Order, List<Product>> generateCombinations(List<Order> orders, List<Product> products) {
        Map<Integer, Product> productMap = products.stream().collect(Collectors.toMap(Product::product_id, p -> p));
        return orders.stream().filter(item -> productMap.containsKey(item.product_id())).collect(Collectors.groupingBy(Function.identity(), Collectors.mapping(item -> productMap.get(item.product_id()), Collectors.toList())));
    }

    public static Map<Order, List<Product>> getFlatMapOfOrderAndProducts() {
        List<Product> products = CsvReader.readProductsCsv();
        List<Order> orders = CsvReader.readOrdersCsv();
        return generateCombinations(orders, products);
    }

    public static List<LeadsDuplicate> getLeadsDuplicateCsv() throws IOException, URISyntaxException {
        List<LeadsDuplicate> leadsDuplicates = new ArrayList<>();
        String filePath = "src/main/resources/leads-duplicates-100.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                LeadsDuplicate leadsDuplicate = new LeadsDuplicate(Integer.parseInt(values[0].trim()), values[1].trim(), values[2].trim(), values[3].trim(), values[4].trim(), values[5].trim(), values[6].trim(), values[7].trim(), values[8].trim(), values[9].trim(), values[10].trim(), values[11].trim(), values[12].trim(), values[13].trim());
                leadsDuplicates.add(leadsDuplicate);
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
        return leadsDuplicates;
    }

    public static List<Organization> readOrganizationCsv() throws IOException, URISyntaxException {
        List<Organization> organizations = new ArrayList<>();
        String filePath = "src/main/resources/organizations-100.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Organization organization = new Organization(
                        Integer.parseInt(values[0].trim()),
                        cleanField(values[1].trim()),
                        cleanField(values[2].trim()),
                        cleanField(values[3].trim()),
                        cleanField(values[4].trim()),
                        cleanField(values[5].trim()),
                        cleanField(values[6].trim()),
                        cleanField(values[7].trim()),
                        cleanField(values[8].trim()));
                organizations.add(organization);
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
        return organizations;
    }

    public static List<OrganizationDuplicates> readOrganizationDuplicatesCsv() throws IOException, URISyntaxException {
        List<OrganizationDuplicates> organizationDuplicates = new ArrayList<>();
        String filePath = "src/main/resources/organizations-duplicates-100.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                OrganizationDuplicates organization = new OrganizationDuplicates(
                        Integer.parseInt(values[0].trim()),
                        cleanField(values[1].trim()),
                        cleanField(values[2].trim()),
                        cleanField(values[3].trim()),
                        cleanField(values[4].trim()),
                        cleanField(values[5].trim()),
                        cleanField(values[6].trim()),
                        cleanField(values[7].trim()),
                        cleanField(values[8].trim()),
                        LocalDateTime.parse(values[9].trim() ,formatter));
                organizationDuplicates.add(organization);
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
        return organizationDuplicates;
    }

}


