public class MatrixZero {
    public static void convert(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] m_arr = new boolean[m];
        boolean[] n_arr = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    m_arr[i] = true;
                    n_arr[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (m_arr[i] || n_arr[j]) matrix[i][j] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 0, 0, 15}};
        convert(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
