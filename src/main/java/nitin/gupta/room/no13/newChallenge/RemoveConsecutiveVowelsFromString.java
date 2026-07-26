package nitin.gupta.room.no13.newChallenge;

public class RemoveConsecutiveVowelsFromString {
    static void main() {
        String name = "NitinKumarGupta";
        System.out.println("Result:   " + removeConsecutiveVowels(name));
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

    public static boolean isVowel(char c){
        return  c=='a'|| c=='e'||c=='i'||c=='o'||c=='u'||c=='A'|| c=='E'||c=='I'||c=='O'||c=='U';
    }

}
