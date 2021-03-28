import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] data;
    private int openCounter = 0;
    private int N = 0;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N is less than or equal to zero");
        }
        N = n;
        data = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col)) {
            throw new IllegalArgumentException("Column or row index out of range");
        }
        if (!data[row - 1][col - 1]) openCounter++;
        data[row - 1][col - 1] = true;
        if (row == 1) {
            unionFind.union(convertToOneD(row, col), N * N);
        }
        if (row == N) {
            unionFind.union(convertToOneD(row, col), N * N + 1);
        }

        if (validate(row - 1, col)) {
            if (data[row - 2][col - 1]) {
                unionFind.union(convertToOneD(row - 1, col), convertToOneD(row, col));
            }
        }
        if (validate(row, col - 1)) {
            if (data[row - 1][col - 2]) {
                unionFind.union(convertToOneD(row, col - 1), convertToOneD(row, col));
            }
        }
        if (validate(row + 1, col)) {
            if (data[row][col - 1]) {
                unionFind.union(convertToOneD(row + 1, col), convertToOneD(row, col));
            }
        }
        if (validate(row, col + 1)) {
            if (data[row - 1][col]) {
                unionFind.union(convertToOneD(row, col + 1), convertToOneD(row, col));
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            throw new IllegalArgumentException("Column or row index out of range");
        }
        return data[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) {
            throw new IllegalArgumentException("Column or row index out of range");
        }
        return unionFind.find(N * N) == unionFind.find(convertToOneD(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(N * N) == unionFind.find(N * N + 1);
    }

    private int convertToOneD(int x, int y) {
        return (y - 1) * N + x - 1;
    }

    private boolean validate(int r, int c) {
        return r >= 1 && c >= 1 && r <= N && c <= N;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
        StdOut.println("Hello world 2");
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(5, 1);
        percolation.open(6, 1);
        percolation.open(7, 1);
        percolation.open(8, 1);
        StdOut.println(percolation.isOpen(1, 1));
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.numberOfOpenSites());
    }

}
