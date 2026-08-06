package nitin.gupta.room.no13.newChallenge;

import nitin.gupta.room.no13.utils.CsvReader;
import nitin.gupta.room.no13.utils.Employee;
import nitin.gupta.room.no13.utils.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterviewQuestions {

    public static String reverseStringUsingRecursion(String str){
        return !str.equals("") ? reverseStringUsingRecursion(str.substring(1)) + str.charAt(0) : str;
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

    static public void getKLargeAndSmalllestElementFromArray(){
        int[] array = {8,-8,10,4,-6,9,-9,12,55,-30,2,5,6};
        int k=2;
        int smallest=Arrays.stream(array).boxed().sorted().findFirst().orElseGet(()->Integer.MIN_VALUE);
        int largest=Arrays.stream(array).boxed().sorted(Collections.reverseOrder(Integer::compareTo)).skip(k-1).findFirst().orElseGet(() -> Integer.MAX_VALUE);
        System.out.println(smallest +" "+largest);
    }

    private static void top3Employees() throws IOException, URISyntaxException {
        List<Employee> employes = CsvReader.readEmployeeCsv();
        List<List<Employee>> salaryEmp = employes.stream()
                .collect(Collectors.groupingBy(Employee::department))
                .entrySet()
                .stream()
                .map(emp -> emp.getValue().stream().sorted(Comparator.comparing(Employee::salary_inr))
                        .limit(3).collect(Collectors.toUnmodifiableList()))
                .collect(Collectors.toList());
        for (List<Employee> employees : salaryEmp) {
             for (Employee employee : employees) {
                 System.out.println(employee);
             }
        }
    }

    /**
     * input  List<Integer> list = Arrays.asList(2,3,2,2,5,5,6,5,7,5,3,1);
     * output  555522233671
     */
    public static void getMaxOccuranceIntegerFirst(){
        List<Integer> list = Arrays.asList(2,3,2,2,5,5,6,5,7,5,3,1);
        list.stream()
                .collect(Collectors.groupingBy(Function.identity(),
                        LinkedHashMap::new, Collectors.counting())) ///Map<5 ,4>
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e ->{
                    for (int i = 0; i < e.getValue(); i++) {
                        System.out.print(e.getKey());
                    }
                });
    }
    public static void FirstNonRepeatingCharacter(){
        String  str= "jksfhkqshfqwuaieqfqiwj";
        Consumer<Character> callback = a -> System.out.println(a);
        Optional<Character> charaaaa = str.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() == 1L)
                .map(entry -> entry.getKey()).findFirst().map(c -> {
                    callback.accept(c);
                    return c;
                });
    }

    public static void DutchFlagProblem(int[] array){
        int i=0 ,j=0, k=array.length-1;
        while(j<=k){
            if(array[j] < 1){
                swap(array,j++,i++);
            } else if (array[j] >1) {
                swap(array,j,k--);
            }else{
                j++;
            }
        }
    }

    private static void swap(int[] arr, int start, int end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }
    public static int findMaxSubarraySum(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int maxSoFar = nums[0];
        int currentMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            maxSoFar = Math.max(maxSoFar, currentMax);
        }

        return maxSoFar;
    }

    public static void findoutFirstLongestConsucativeArray(int[] arr) {
        if (arr == null || arr.length == 0) return;
        int maxLen = 0;
        int currentLen = 0;
        int bestStartIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                currentLen++;
                // Strictly greater (>) ensures we pick the FIRST longest sequence in case of ties
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    bestStartIndex = i - currentLen + 1;
                }
            } else {
                currentLen = 0;
            }
        }

        // Print result details
        System.out.println("Longest consecutive 1s length: " + maxLen);
        System.out.println("Starts at index: " + bestStartIndex + ", Ends at index: " + (bestStartIndex + maxLen - 1));

        System.out.print("Subarray: [");
        for (int i = 0; i < maxLen; i++) {
            System.out.print(arr[bestStartIndex + i] +" ");
        }
        System.out.println("]");
    }

    static void main() throws IOException, URISyntaxException {
//        String input = "abcdefgh";
//        System.out.println(reverseStringUsingRecursion(input));
        //top3Employees();
        // getKLargeAndSmalllestElementFromArray();
       // getMaxOccuranceIntegerFirst();
       // FirstNonRepeatingCharacter();
       // int[] array = new int[]{2, 1, 2, 2, 0, 1, 2, 0, 2, 1, 1, 2, 1, 0, 1, 0};
      ///  DutchFlagProblem(array);
       // System.out.println(Arrays.toString(array));
      //  int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        ///System.out.println(findMaxSubarraySum(nums));
        int[] intarray = {0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1};
        findoutFirstLongestConsucativeArray(intarray);
    }
}
