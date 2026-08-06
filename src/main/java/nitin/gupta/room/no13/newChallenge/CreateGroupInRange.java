package nitin.gupta.room.no13.newChallenge;

import nitin.gupta.room.no13.utils.CsvReader;
import nitin.gupta.room.no13.utils.Employee;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * we have 100 people list from index 1 to 100
 * make a group 1 to 10 ,10 to 20.... till 90 to 100
 */
enum Range {
    TEN, TWENTY, THIRTY, FORTY, FIFTY, SIXTY, SEVENTY, EIGHTY, NINETY, HUNDRED;
}

public class CreateGroupInRange {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Employee> employees = CsvReader.readEmployeeCsv();
        Map<Range, Long> indexRange = employees.stream().collect(Collectors.groupingBy(
                employee -> fromPowerLevel(employee.emp_id()), Collectors.counting()
        ));

        System.out.println(indexRange);

    }

    public static Range fromPowerLevel(int index) {
        Range result = Range.EIGHTY;
        int a = index / 10;
        switch (a) {
            case 0:
                result = Range.TEN;
                break;
            case 1:
                result = Range.TEN;
                break;
            case 2:
                result = Range.TWENTY;
                break;
            case 3:
                result = Range.THIRTY;
                break;
            case 4:
                result = Range.FORTY;
                break;
            case 5:
                result = Range.FIFTY;
                break;
            case 6:
                result = Range.SIXTY;
                break;
            case 7:
                result = Range.SEVENTY;
                break;
            case 8:
                result = Range.EIGHTY;
                break;
            case 9:
                result = Range.NINETY;
                break;
            default:
                result = Range.HUNDRED;
                break;
        }
        return result;
    }
}
