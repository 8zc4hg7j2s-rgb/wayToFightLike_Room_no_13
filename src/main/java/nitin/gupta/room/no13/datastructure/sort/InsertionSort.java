package nitin.gupta.room.no13.datastructure.sort;

import java.util.Arrays;

public class InsertionSort {

    private static void swap(int[] arr, int start, int end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

    static void main() {
        int[] data = {24, 8, -42, 75, -24, 2, 16, 60, 31, 10};
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.insertionSort(data);
        System.out.println("InsertionSort.main"+ Arrays.toString(data));
    }

    private void insertionSort(int[] data) {
       for (int i = 1; i < data.length; i++) {
           int j  = i ;
           while( j > 0 && data[j-1] > data[j]) {
               swap(data , j, j-1);
               j--;
           }
       }
    }
}
