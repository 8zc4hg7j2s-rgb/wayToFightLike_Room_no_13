package nitin.gupta.room.no13.newChallenge;

public class RemoveConsecutiveVowelsFromString {
    static void main() {
        String name = "NitinKumarGupta";
        System.out.println("Result:   " + removeConsecutiveVowels(name));
        String input = "geeks for geeks";
        getStringRemoveConsecutiveVowels(input);

    }

    static public void getStringRemoveConsecutiveVowels(String input) {
        StringBuilder output = new StringBuilder();
        boolean flag = false;
        for (int i = 1; i < input.length(); i++) {
            String output1 = String.valueOf(input.charAt(i - 1));
            if (isVowel(input.charAt(i - 1)) && isVowel(input.charAt(i))) {
                if (!flag)
                    output.append(output1);
                flag = true;
            } else {
                if (!flag)
                    output.append(output1);
                flag = false;
            }
        }
        System.out.println(output);

    }

    public static String removeConsecutiveVowels(String str) {
        StringBuilder result = new StringBuilder();
        char prev = '\0';

        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);

            // Skip this char if both current and previous chars are vowels
            if (isVowel(current) && isVowel(prev)) {
                continue;
            }

            result.append(current);
            prev = current;
        }

        return result.toString();
    }

    public static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

}
