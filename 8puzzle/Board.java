import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private final int[][] grid;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n);
        s.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(grid[i][j]);
                if (j != n - 1) {
                    s.append(" ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }


    // number of tiles out of place
    public int hamming() {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((grid[i][j] != 0) && (grid[i][j] != i * n + j + 1)) {
                    counter++;
                }
            }
        }
        return counter;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    counter += Math.abs(i - (grid[i][j] - 1) / n);
                    counter += Math.abs(j - (grid[i][j] - 1) % n);
                }
            }
        }
        return counter;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }


    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (getClass() != y.getClass()) {
            return false;
        }
        final Board that = (Board) y;
        return Arrays.deepEquals(this.grid, that.grid);
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int blankI = 0, blankJ = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }
        int[][] gridCopy = new int[n][n];
        if (verify(blankI - 1, blankJ)) {
            gridCopy = createCopy();
            gridCopy[blankI][blankJ] = gridCopy[blankI - 1][blankJ];
            gridCopy[blankI - 1][blankJ] = 0;
            Board b = new Board(gridCopy);
            stack.push(b);
        }
        if (verify(blankI + 1, blankJ)) {
            gridCopy = createCopy();
            gridCopy[blankI][blankJ] = gridCopy[blankI + 1][blankJ];
            gridCopy[blankI + 1][blankJ] = 0;
            Board b = new Board(gridCopy);
            stack.push(b);
        }
        if (verify(blankI, blankJ - 1)) {
            gridCopy = createCopy();
            gridCopy[blankI][blankJ] = gridCopy[blankI][blankJ - 1];
            gridCopy[blankI][blankJ - 1] = 0;
            Board b = new Board(gridCopy);
            stack.push(b);
        }
        if (verify(blankI, blankJ + 1)) {
            gridCopy = createCopy();
            gridCopy[blankI][blankJ] = gridCopy[blankI][blankJ + 1];
            gridCopy[blankI][blankJ + 1] = 0;
            Board b = new Board(gridCopy);
            stack.push(b);
        }
        return stack;
    }

    private boolean verify(int i, int j) {
        if (i < 0) return false;
        if (j < 0) return false;
        if (i >= n) return false;
        if (j >= n) return false;
        return true;
    }

    private int[][] createCopy() {
        int[][] gridCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gridCopy[i][j] = grid[i][j];
            }
        }
        return gridCopy;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] gridCopy;
        gridCopy = createCopy();
        int temp = 0;
        if (gridCopy[0][0] != 0 && gridCopy[1][1] != 0) {
            temp = gridCopy[0][0];
            gridCopy[0][0] = gridCopy[1][1];
            gridCopy[1][1] = temp;
        }
        else {
            temp = gridCopy[0][1];
            gridCopy[0][1] = gridCopy[1][0];
            gridCopy[1][0] = temp;
        }
        Board b = new Board(gridCopy);
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] table = new int[3][3];
        table[0][0] = 1;
        // table[0][1] = 1;
        table[0][2] = 3;
        table[1][0] = 4;
        table[1][1] = 2;
        table[1][2] = 5;
        table[2][0] = 7;
        table[2][1] = 8;
        table[2][2] = 6;
        Board board = new Board(table);
        StdOut.println(board.toString());
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.equals(board));
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
        StdOut.println(board.twin());
    }

}
