package nitin.gupta.room.no13.coreJavaPrectise.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Integer[] intArr = {2,3,4,6,3,7,6,7,8,9};
 * //print the elements which are duplicate, by using stream api
 */
public class LargestSubStringWithoutRepeatingCharacter {

    public static String getLongestSubstring1(String s) {
        if (s == null || s.isEmpty()) return "";
        Map<Character, Integer> seen = new HashMap<>();
        int left = 0, start = 0, max = 0;
        for (int right = 0; right < s.length(); right++) {
            left = Math.max(left, seen.getOrDefault(s.charAt(right), -1) + 1);
            seen.put(s.charAt(right), right);
            if (right - left + 1 > max) {
                max = right - left + 1;
                start = left;
            }
        }
        return s.substring(start, start + max);
    }

    public static void main(String[] args) {
        String test1 = "abcabcbbasdfghjllkjhgdsa";
        String test2 = "bbbbb";
        String test3 = "pwwkew";

        System.out.println("Input: \"" + test1 + "\" -> Output: \"" + getLongestSubstring1(test1) + "\""); // Output: "abc"
        //System.out.println("Input: \"" + test2 + "\" -> Output: \"" + getLongestSubstring1(test2) + "\""); // Output: "b"
       // System.out.println("Input: \"" + test3 + "\" -> Output: \"" + getLongestSubstring1(test3) + "\""); // Output: "wke"
    }
}
