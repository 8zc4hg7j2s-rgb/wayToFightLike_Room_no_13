package nitin.gupta.room.no13.newChallenge;

import java.util.Stack;
import java.util.stream.Collectors;

public class ReverseStringKeepSpecialChars {
    public static String reverseOnlyLetters(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            // Move left pointer forward until it points to a letter
            if (!Character.isLetter(chars[left])) {
                left++;
                continue;
            }
            // Move right pointer backward until it points to a letter
            if (!Character.isLetter(chars[right])) {
                right--;
                continue;
            }
            // Swap the two letters
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;

            left++;
            right--;
        }
        return new String(chars);
    }

    public static String reverseOnlyLettersStream(String s) {
        // Collect all letters into a Stack to reverse their natural order (LIFO)
        Stack<Character> letterStack = s.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .collect(Collectors.toCollection(Stack::new));

        // Rebuild string: pop from stack if character is a letter, else keep original
        return s.chars()
                .mapToObj(c -> (char) c)
                .map(c -> Character.isLetter(c) ? letterStack.pop() : c)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
    public static void main(String[] args) {
        String input1 = "1a-b#C-dE&f-g3h@%Ij";
        String input2 = "Test1ng-Leet=code-Q!";
        String input3 = "St4ffing@Company#2024";

        System.out.println(reverseOnlyLettersStream(input1));
        System.out.println(reverseOnlyLettersStream(input2));
        System.out.println(reverseOnlyLettersStream(input3));


        System.out.println(reverseOnlyLetters(input1));
        System.out.println(reverseOnlyLetters(input2));
        System.out.println(reverseOnlyLetters(input3));
    }
}
