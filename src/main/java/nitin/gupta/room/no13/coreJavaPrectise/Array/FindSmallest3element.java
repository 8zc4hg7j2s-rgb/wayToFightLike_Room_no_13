package nitin.gupta.room.no13.coreJavaPrectise.Array;

import java.util.Arrays;

/**
 * Smallest 3 element
 */

public class FindSmallest3element {
    public static void main(String[] args) {
        int[][] array = {
                {10, 20, 30, 40},
                {20, 11, 67, 13},
                {44, 12, 23, 43}
        };
        int smallest3 = Arrays.stream(array)
                .flatMapToInt(l -> Arrays.stream(l)).boxed().sorted().skip(2).findFirst().get();
        System.out.println(smallest3);

        int[] output = new int[4 * 3];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                output[count++] = array[i][j];
            }
        }
        int aaa = Arrays.stream(output).boxed().sorted().skip(2).findFirst().get();
        System.out.println(aaa);

    }
}
