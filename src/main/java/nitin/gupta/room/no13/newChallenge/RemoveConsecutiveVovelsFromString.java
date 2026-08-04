package nitin.gupta.room.no13.newChallenge;

public class RemoveConsecutiveVovelsFromString {
    static void main() {
        String s = "sffaadfsdsiiijdsooosdfksllsuuu";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (isVowel(currentChar)) {
                // Keep the vowel only if it's the first character
                // or if the previous character wasn't a vowel
                if (i == 0 || !isVowel(s.charAt(i - 1))) {
                    result.append(currentChar);
                }
            } else {
                // Always keep non-vowel characters
                result.append(currentChar);
            }
        }
        System.out.println("Original: " + s);
        System.out.println("Result:   " + result.toString());
    }
    private static boolean isVowel(char ch) {
        char lower = Character.toLowerCase(ch);
        return lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u';
    }
}
