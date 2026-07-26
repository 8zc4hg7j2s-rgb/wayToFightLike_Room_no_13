package nitin.gupta.room.no13.coreJavaPrectise.sort;

import java.util.Arrays;

public class DualPivotQuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        dualPivotQuicksort(arr, 0, arr.length - 1);
    }

    private static void dualPivotQuicksort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        // 1. Ensure left pivot is smaller than or equal to right pivot
        if (arr[low] > arr[high]) {
            swap(arr, low, high);
        }

        int pivot1 = arr[low];
        int pivot2 = arr[high];

        // 2. Initialize pointers for partitioning
        int lt = low + 1;
        int gt = high - 1;
        int i = low + 1;

        // 3. Partition elements into three segments
        while (i <= gt) {
            if (arr[i] < pivot1) {
                swap(arr, i++, lt++);
            } else if (arr[i] > pivot2) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }

        // 4. Place pivots into their correct final positions
        swap(arr, low, --lt);
        swap(arr, high, ++gt);

        // 5. Recursively sort the three resulting sub-arrays
        dualPivotQuicksort(arr, low, lt - 1);
        dualPivotQuicksort(arr, lt + 1, gt - 1);
        dualPivotQuicksort(arr, gt + 1, high);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] data = {24, 8, -42, 75, -24, 2, 16, 60, 31, 10};
        System.out.println("Original array: " + Arrays.toString(data));

        sort(data);

        System.out.println("Sorted array:   " + Arrays.toString(data));
    }
}
