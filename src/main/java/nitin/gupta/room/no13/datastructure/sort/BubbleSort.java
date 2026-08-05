package nitin.gupta.room.no13.datastructure.sort;

import java.util.Arrays;

public class BubbleSort {

    static void main() {
        int[] data = {24, 8, -42, 75, -24, 2, 16, 60, 31, 10};
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSort(data);
        System.out.println("Sorted array"+ Arrays.toString(data));;
    }

    private void bubbleSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] > data[j]) {
                    swap(data, i, j);
                }
            }
        }
    }

    private static void swap(int[] arr, int start, int end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }
}
