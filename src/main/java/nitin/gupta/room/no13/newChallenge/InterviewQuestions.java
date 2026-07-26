package nitin.gupta.room.no13.newChallenge;

import nitin.gupta.room.no13.utils.CsvReader;
import nitin.gupta.room.no13.utils.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterviewQuestions {
    static void main() throws IOException, URISyntaxException {
//        int[] array = {-3, -1, -5, 7, 4, 3, 2, 9};
//        int[][] twoDimensionArray = {{1, 3, 6, 9}, {-1, -3, 11, -12}, {12, 34, -2, 17}};
//        int[] array2 = {2, 4, 6, 8};
        //List<Person> list = CsvReader.readPeopleCsv();
       /// mapDeptmentWiseList(list );
        //        List<List<Person>> batches =rangeWiseList(list,10);
        //        for (List<Person> batch : batches) {
        //            System.out.println(Arrays.toString(batch.toArray()));
        //        }
        //checkString();
        findOutAllPrimeNumberBetween1To100();



    }

    private static void findOutAllPrimeNumberBetween1To100() {
        IntStream.rangeClosed(1, 100).filter(number ->isPrimeNumber(number)).map(Integer::valueOf).forEach(i ->System.out.print(" "+i));
    }

    static public boolean isPrimeNumber(int number){
        int sqrtval =(int)Math.sqrt(number);
        for (int i = 2; i <=sqrtval; i++) {
            if(number%i==0){
                return false;
            }
        }
        return true;
    }

    private static void checkString() {
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println(s1==s2);
        String s3= "abc";
        System.out.println(s1==s3);
        String s4= "abc";
        System.out.println(s3==s4);
        s4= s1;
        System.out.println(s3==s4);
    }

    public List<Person> comparatorDifferentContext(List<Person> personList){
        return personList.stream()
                .sorted(Comparator
                .comparing(Person::department)
                .thenComparing(Person::department, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Person::userId, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            result.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return result;
    }

    private static List<List<Person>> rangeWiseList(List<Person> list , int size) {
        return IntStream.range(0, list.size() / size)
                .mapToObj(i -> list.subList(
                        i * size, Math.min((i + 1) * size, list.size())
                ))
                .collect(Collectors.toList());
    }
    public static void mapDeptmentWiseList(List<Person> list ) {
        Map<String, List<String>> deptMap = new HashMap<>();
        list.stream().collect(Collectors.groupingBy(Person::department, HashMap::new, Collectors.toList()))
                .entrySet().stream().forEach(entry -> {
                    List<String> userId = entry.getValue().stream().map(Person::userId).collect(Collectors.toList());
                    deptMap.put(entry.getKey(), userId);
                    });
        for (Map.Entry<String, List<String>> entry : deptMap.entrySet()) {
            System.out.println(" "+entry.getKey() +" -> "+entry.getValue());
        }
    }
    private static Integer findLargestnumber(int[][] twoDimensionArray) {
        return Arrays.stream(twoDimensionArray).flatMapToInt(Arrays::stream).boxed().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElse(Integer.MAX_VALUE);
    }

    private static Integer findSmallestnumber(int[][] twoDimensionArray) {
        return Arrays.stream(twoDimensionArray).flatMapToInt(Arrays::stream).boxed().sorted().skip(2).findFirst().orElse(Integer.MIN_VALUE);
    }

    private static int[] addTwoArrayInSortedOrder(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        int count = 0;
        for (int i = 0; i < array1.length; i++, count++) {
            result[count] = array1[i];
        }
        for (int i = 0; i < array2.length; i++, count++) {
            result[count] = array2[i];
        }
        Arrays.sort(result);
        return result;
    }

    public void allPossibleComparator() {
        Comparator<Integer> intComp = Comparator.naturalOrder();
        Comparator<String> strComp = String::compareTo;
        Comparator<Integer> intcomp1 = (a, b) -> a - b;
        Comparator<Integer> intcomp2 = (a, b) -> a.compareTo(b);
        Comparator<Integer> intcomp3 = Integer::compareTo;
    }

    private static void combinationOf18Sum(int[] array) {
        List<Integer> subSet = new ArrayList<>();
        List<List<Integer>> powerSet = new ArrayList<>();
        combinationOf18Sum(array, subSet, powerSet, 0);
        powerSet.stream().forEach(list -> {
            if (list.stream().reduce((a, b) -> a + b).filter(sum -> sum == 18).isPresent()) {
                System.out.println(list);
            }
        });
    }

    private static void combinationOf18Sum(int[] array, List<Integer> subSet, List<List<Integer>> powerSet, int index) {
        powerSet.add(new ArrayList<>(subSet));
        for (int i = index; i < array.length; i++) {
            subSet.add(array[i]);
            combinationOf18Sum(array, subSet, powerSet, i + 1);
            subSet.remove(subSet.size() - 1);
        }
    }
}
