package nitin.gupta.room.no13.newChallenge;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  input  str="SDFAIUERDSFSEWRZXCWERFHSEKLJ";
 */
public class FindOutMaxLengthSubStringWithoutRepeatingCharacter {
    public static void main(String[] args) {
        String input  ="NtjItTtwtItgttNtGtjtwttUPtTjwtA";
        System.out.println( findOutNonRepeatableMaxLengthSubString(input));
        System.out.println( findOutNonRepeatableMaxLengthSubString1(input));
        int[] arrary ={4,1,2};
//        /**
//         * 4,1,2
//         * 4+1 ,1 ,2
//         * 5,5+1,2
//         * 5,6,6+2
//         * 19
//         */
//       int sum= Arrays.stream(outputASdefine(arrary)).boxed().reduce(0 ,(a ,b) ->a+b);
//        System.out.println(sum);
//
//        int[][]  array = {{54,32,1},{22,56,99},{10,20,30}};
//        System.out.println(findoutSmallestItem(array));
//
//        System.out.println(findoutLargestItem(array));
    }

    private static int findoutLargestItem(int[][] array) {
        Comparator<Integer> integerComparator = Integer::compare;
        int sss = Arrays.stream(array).flatMap(a -> Arrays.stream(a).asLongStream().boxed())
                .sorted(Comparator.reverseOrder()).findFirst().get().intValue();
        int ss2s = Arrays.stream(array).flatMapToInt(Arrays::stream).boxed()
                .sorted(Comparator.reverseOrder()).findFirst().get().intValue();
        int bb = Arrays.stream(array).flatMapToInt(Arrays::stream).boxed().sorted(integerComparator.reversed()).findFirst().get().intValue();
       int sssss= Arrays.stream(array).flatMap(a -> Arrays.stream(a).boxed()).sorted().findFirst().get().intValue();
     return    Arrays.stream(array).flatMapToInt(Arrays::stream).boxed().sorted((a,b) -> b-a).findFirst().get().intValue();
    }

    private static int findoutSmallestItem(int[][] array) {
      return Arrays.stream(array).flatMapToInt(Arrays::stream).boxed().sorted().findFirst().get().intValue();
    }


    public static String findOutNonRepeatableMaxLengthSubString1(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        Map<Character, Integer> lastSeenIndex = new HashMap<>();
        int start = 0;
        int maxLen = 0;
        int maxStart = 0;

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (lastSeenIndex.containsKey(c) && lastSeenIndex.get(c) >= start) {
                start = lastSeenIndex.get(c) + 1;
            }
            lastSeenIndex.put(c, end);
            int currentLen = end - start + 1;
            if (currentLen > maxLen) {
                maxLen = currentLen;
                maxStart = start;
            }
        }
        return s.substring(maxStart, maxStart + maxLen);
    }

    static String findOutNonRepeatableMaxLengthSubString(String input){
        long millis1 =System.currentTimeMillis();
       String output="";
        for (int i = 0; i < input.length(); i++) {
            for (int j = i+1; j < input.length(); j++) {
                String str =  input.substring(i ,j);
                String expectedEEmptyString        = str
                        .chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue() != 1)
                        .map(Map.Entry::getKey)
                                 .map(String::valueOf)
                                 .collect(Collectors.joining());

               if(expectedEEmptyString.isBlank()) {
                   output = str.length() > output.length() ? str : output;
               }else break;
            }
        }
        return output;
    }
}