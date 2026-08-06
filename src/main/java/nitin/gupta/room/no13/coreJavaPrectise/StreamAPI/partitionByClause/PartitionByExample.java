package nitin.gupta.room.no13.coreJavaPrectise.StreamAPI.partitionByClause;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartitionByExample {
    static void main() {
        Stream<Integer>
                s = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, List<Integer>>
                map = s.collect(
                Collectors.partitioningBy(num -> num % 3 == 0));
        for (Map.Entry<Boolean, List<Integer>> entry : map.entrySet()) {
            System.out.println("Partition By Example :  " + entry.getKey() + " : " + entry.getValue());
        }

    }
}
