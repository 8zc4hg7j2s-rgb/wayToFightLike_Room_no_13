package nitin.gupta.room.no13.newChallenge;


import java.util.ArrayList;
import java.util.List;

/**
 * Given an integer array, check if it contains a contiguous subarray having zero-sum.
 * <p>
 * Input : [3, 4, -7, 3, 1, 3, 1, -4, -2, -2]
 * Output: true
 * Explanation: The subarrays with zero-sum are
 * <p>
 * [3, 4, -7]
 * [4, -7, 3]
 * [-7, 3, 1, 3]
 * [3, 1, -4]
 * [3, 1, 3, 1, -4, -2, -2]
 * [3, 4, -7, 3, 1, 3, 1, -4, -2, -2]
 * <p>
 * Input : [4, -7, 1, 2, -1]
 * Output: false
 * Explanation: The subarray with zero-sum doesn't exist.
 */
public class HackerRankPrectiseTest {
     static void main() {
         int[] array ={3, 4, -7, 3, 1, 3, 1, -4, -2, -2};
         List<Integer> subset = new ArrayList<>();
         List<List<Integer>> superset = new ArrayList<>();
         combinationOfAllSuperset(array ,subset ,superset ,0);
         boolean flag = false;
        for(List<Integer> list : superset){
            if(list.stream().mapToInt(Integer::intValue).sum()==0){
                flag = true;
            }
        }
         System.out.println(flag);
     }

    private static void combinationOfAllSuperset(int[] array, List<Integer> subset,
                                                 List<List<Integer>> superset, int i) {
         superset.add(new ArrayList<>(subset));
         for (int j = i; j < subset.size(); j++) {
             subset.add(array[j]);
             combinationOfAllSuperset(array, subset, superset, j++);
             subset.remove(subset.size()-1);
         }
    }

}
