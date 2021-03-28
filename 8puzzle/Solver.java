import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode answer;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Null argument");
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        SearchNode searchNodeInitial = new SearchNode(initial, null, 0);
        SearchNode searchNodeInitialTwin = new SearchNode(initial.twin(), null, 0);
        minPQ.insert(searchNodeInitial);
        minPQTwin.insert(searchNodeInitialTwin);
        while (true) {
            SearchNode sn = minPQ.delMin();
            SearchNode snTwin = minPQTwin.delMin();
            if (sn.board.isGoal()) {
                answer = sn;
                solvable = true;
                break;
            }
            else {
                for (Board board : sn.board.neighbors()) {
                    if (sn.prev != null) {
                        if (!board.equals(sn.prev.board)) {
                            SearchNode searchNodeChild = new SearchNode(board, sn,
                                                                        sn.moves + 1);
                            minPQ.insert(searchNodeChild);
                        }
                    }
                    else {
                        SearchNode searchNodeChild = new SearchNode(board, sn,
                                                                    sn.moves + 1);
                        minPQ.insert(searchNodeChild);
                    }
                }
            }

            if (snTwin.board.isGoal()) {
                solvable = false;
                break;
            }
            else {
                for (Board board : snTwin.board.neighbors()) {
                    if (snTwin.prev != null) {
                        if (!board.equals(snTwin.prev.board)) {
                            SearchNode searchNodeChild = new SearchNode(board, snTwin,
                                                                        sn.moves + 1);
                            minPQTwin.insert(searchNodeChild);
                        }
                    }
                    else {
                        SearchNode searchNodeChild = new SearchNode(board, snTwin,
                                                                    sn.moves + 1);
                        minPQTwin.insert(searchNodeChild);
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return answer.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        SearchNode root = answer;
        Stack<Board> solution = new Stack<Board>();
        while (root != null) {
            solution.push(root.board);
            root = root.prev;
        }
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int manhattan;
        private final int moves;
        private final int priority;

        public SearchNode(Board b, SearchNode p, int mov) {
            board = b;
            prev = p;
            manhattan = b.manhattan();
            moves = mov;
            priority = manhattan + mov;
        }

        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) return -1;
            else if (this.priority > that.priority) return 1;
            else return 0;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
