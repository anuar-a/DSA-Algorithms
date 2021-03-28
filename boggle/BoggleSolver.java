import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.TreeMap;


public class BoggleSolver {
    private final TrieSTPro<String> dictionary = new TrieSTPro<String>();
    private TreeMap<Integer, Bag<Integer>> adj;
    private HashSet<String> answer;
    private BoggleBoard boggleBoard;
    private boolean[] marked;
    private String wordIter;
    private int n;
    private int cols;
    private int rows;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String s : dictionary) {
            this.dictionary.put(s, s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boggleBoard = board;
        answer = new HashSet<String>();
        cols = board.cols();
        rows = board.rows();
        n = cols * rows;
        marked = new boolean[n];
        adj = new TreeMap<Integer, Bag<Integer>>();
        for (int i = 0; i < n; i++) {
            Bag<Integer> temp = new Bag<Integer>();
            // StdOut.print(i + " : ");
            for (int j : getAdj(i)) {
                temp.add(j);
                // StdOut.print(j + " ");
            }
            adj.put(i, temp);
            // StdOut.println();
        }
        // StdOut.println(boggleBoard);
        for (int i = 0; i < n; ++i) {
            marked = new boolean[n];
            wordIter = "";
            dfs(i);
        }
        // StdOut.println(answer);
        return answer;
    }

    // depth first search from v
    private void dfs(int v) {
        marked[v] = true;
        int i = v / rows;
        if (rows == 1) i = 0;
        int j = v % cols;
        if (cols == 1) {
            j = 0;
            i = v;
        }
        // StdOut.println(v + ": " + i + " " + j);
        char lt = boggleBoard.getLetter(i, j);
        // for (boolean each : marked) {
        //     StdOut.print(each + " ");
        // }
        // StdOut.println();
        if (lt == 'Q') {
            if (!dictionary.hasPrefix(wordIter + lt + 'U')) {
                // StdOut.println(wordIter + lt + " " + "(" + i + "," + j + ")" + " no prefix");
                // marked[v] = false;
                // return;
            }
            else {
                wordIter = wordIter + lt + 'U';
                // StdOut.println(wordIter + " " + "(" + i + "," + j + ")");
                if (wordIter.length() >= 3 && dictionary.contains(wordIter)) {
                    answer.add(wordIter);
                    // wordIter = "";
                }
                for (int w : adj.get(v)) {
                    // StdOut.println(w);
                    if (!marked[w]) {
                        dfs(w);
                    }
                }
                if (wordIter != null && wordIter.length() > 0) {
                    wordIter = wordIter.substring(0, wordIter.length() - 2);
                }
            }
        }
        else {
            if (!dictionary.hasPrefix(wordIter + lt)) {
                // StdOut.println(wordIter + lt + " " + "(" + i + "," + j + ")" + " no prefix");
                // marked[v] = false;
                // return;
            }
            else {
                wordIter = wordIter + lt;
                // StdOut.println(wordIter + " " + "(" + i + "," + j + ")");
                if (wordIter.length() >= 3 && dictionary.contains(wordIter)) {
                    answer.add(wordIter);
                    // wordIter = "";
                }
                for (int w : adj.get(v)) {
                    // StdOut.println(w);
                    if (!marked[w]) {
                        dfs(w);
                    }
                }
                if (wordIter != null && wordIter.length() > 0) {
                    wordIter = wordIter.substring(0, wordIter.length() - 1);
                }
            }
        }
        marked[v] = false;
    }

    private boolean validate(int i, int j) {
        if (i < 0 || j < 0) return false;
        if (i >= rows || j >= cols) return false;
        return true;
    }

    private Iterable<Integer> getAdj(int v) {
        Queue<Integer> ans = new Queue<Integer>();
        int i = v / rows;
        if (rows == 1) i = 0;
        int j = v % cols;
        if (cols == 1) {
            j = 0;
            i = v;
        }
        // StdOut.println(v + " : " + i + " " + j);
        if (validate(i - 1, j - 1)) ans.enqueue(toOneDimension(i - 1, j - 1));
        if (validate(i - 1, j)) ans.enqueue(toOneDimension(i - 1, j));
        if (validate(i, j - 1)) ans.enqueue(toOneDimension(i, j - 1));
        if (validate(i + 1, j + 1)) ans.enqueue(toOneDimension(i + 1, j + 1));
        if (validate(i, j + 1)) ans.enqueue(toOneDimension(i, j + 1));
        if (validate(i + 1, j)) ans.enqueue(toOneDimension(i + 1, j));
        if (validate(i - 1, j + 1)) ans.enqueue(toOneDimension(i - 1, j + 1));
        if (validate(i + 1, j - 1)) ans.enqueue(toOneDimension(i + 1, j - 1));
        return ans;
    }

    private int toOneDimension(int i, int j) {
        return i * cols + j;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionary.contains(word) || word.length() < 3) return 0;
        if (word.length() == 3 || word.length() == 4) return 1;
        else if (word.length() == 5) return 2;
        else if (word.length() == 6) return 3;
        else if (word.length() == 7) return 5;
        else return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dict = in.readAllStrings();
        BoggleBoard boggleBoard = new BoggleBoard(args[1]);
        BoggleSolver boggleSolver = new BoggleSolver(dict);
        int score = 0;
        for (String word : boggleSolver.getAllValidWords(boggleBoard)) {
            StdOut.println(word);
            score += boggleSolver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
