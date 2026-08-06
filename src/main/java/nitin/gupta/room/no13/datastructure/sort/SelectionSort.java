package nitin.gupta.room.no13.datastructure.sort;

import java.util.Arrays;

public class SelectionSort {

    private static void swap(int[] arr, int start, int end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

    static void main() {
        int[] data = {24, 8, -42, -75, -2, 16, 60, 31, 10};
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.selectionSort(data);
        System.out.println("Sorted array" + Arrays.toString(data));
    }

    public void selectionSort(int[] data) {
        if (data == null || data.length < 2) return;

        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Find the index of the minimum element in the unsorted portion
            for (int j = i + 1; j < n; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap only if a smaller element was found
            if (minIndex != i) {
                swap(data, i, minIndex);
            }
        }
    }
}
