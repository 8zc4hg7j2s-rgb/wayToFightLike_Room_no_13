package nitin.gupta.room.no13.newChallenge;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

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
public class SlidingWindowMaximum {
    // Optimized O(N) solution using a monotonic deque
    static int[] slidingWindowMaximum(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length == 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n - k + 1];
        // Deque stores INDICES, values kept in decreasing order front->back
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // 1. Remove indices that are out of this window's range
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // 2. Remove indices whose values are smaller than current
            //    (they can never be the max while nums[i] is in the window)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // 3. Add current index
            deque.offerLast(i);

            // 4. Front of deque is the max for this window
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;

        int[] result = slidingWindowMaximum(arr, k);
        System.out.println(Arrays.toString(result)); // [3, 3, 5, 5, 6, 7]

        int[] arr2 = {1, 2, 3, 1, 4, 5, 2, 3, 6};
        System.out.println(Arrays.toString(slidingWindowMaximum(arr2, 3)));
        // [3, 3, 4, 5, 5, 5, 6]
    }
}
