package nitin.gupta.room.no13.newChallenge;

import java.util.Arrays;

/**
 * Q1. Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
 * You can only see the k numbers in the window. Each time the sliding window moves right by one position,
 * Print the max value sliding window.
 * Input: vals = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output:
 * Input: arr[] = {1, 2, 3, 1, 4, 5, 2, 3, 6}, K = 3
 * Output: 3 3 4 5 5 5 6
 * Explanation: Maximum of 1, 2, 3 is 3
 * Maximum of 2, 3, 1 is 3
 * Maximum of 3, 1, 4 is 4
 * Maximum of 1, 4, 5 is 5
 * Maximum of 4, 5, 2 is 5
 * Maximum of 5, 2, 3 is 5
 * Maximum of 2, 3, 6 is 6
 * <p>
 * Soln- answer  [1,3,-1,-3,5,3,6,7], and k = 3   ANS[3,3,5,5,6,7]
 * TC-
 * SC-
 */
public class SlidingWindowMaximumApproach2 {
    public static int[] slidingWindowMaximum(int[] array, int k) {
        if (k == 0 || array.length == 0) return new int[0];
        int[] result = new int[array.length - k + 1];
        for (int i = 0; i < array.length - k + 1; i++) {
            int max = array[i];
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, array[j]);
                result[i] = max;
            }
        }
        return result;
    }

    // Driver's code
    public static void main(String args[]) {
        int arr[] = {1, 3, -1, -3, 5, 3, 6, 7};
        int K = 3;
        int[] result = slidingWindowMaximum(arr, 3);
        System.out.println(Arrays.toString(result));
    }
}
