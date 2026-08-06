package nitin.gupta.room.no13.coreJavaPrectise.Array;

/**
 * get max square in the table
 * [1,1]
 * [1,1]
 */
public class TwoDimensionalArray {
    static int[][] matrix = {{1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1},
            {1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 0, 1, 1}};

    public static void main(String[] args) {
        int count = solution(matrix);
        System.out.println(count);
    }

    static public int solution(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m - 1; j++) {
                if (matrix[i][j] == 1 &&
                        matrix[i + 1][j] == 1
                        && matrix[i][j + 1] == 1
                        && matrix[i + 1][j + 1] == 1) {
                    count++;
                }
            }
        }

        return count;
    }
}
