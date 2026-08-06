package nitin.gupta.room.no13.newChallenge;

public class SortedDistanceBetweenWords {
    public static void main(String[] args) {
        String input = "is the quick the brown quick brown the frog";
        String[] words = input.split(" ");

        System.out.println("Min distance :: " + sortedDistanceBetweenWords("quick", "frog", words));
        System.out.println("Min distance :: " + sortedDistanceBetweenWords("frog", "is", words));
    }

    /**
     * Finds the shortest distance (in number of words between them) between two distinct words.
     *
     * Time Complexity:  O(N) - Single pass through the string array.
     * Space Complexity: O(1) - Uses constant extra space.
     */
    public static int sortedDistanceBetweenWords(String w1, String w2, String[] strArray) {
        if (strArray == null || w1 == null || w2 == null ){
            return -1;
        }

        if (w1.equals(w2)) {
            return 0;
        }

        int p1 = -1; // Last seen index of w1
        int p2 = -1; // Last seen index of w2
        int minDist = strArray.length + 1;

        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i].equals(w1)) {
                p1 = i;
            } else if (strArray[i].equals(w2)) {
                p2 = i;
            }

            // If both words have been encountered at least once
            if (p1 != -1 && p2 != -1) {
                int currentDist = Math.abs(p1 - p2) - 1;
                minDist = Math.min(minDist, currentDist);
            }
        }

        return minDist;
    }
}
