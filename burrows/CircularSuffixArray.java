import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private String str;
    private int[] index;
    private int n;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("Null argument!");
        str = s;
        n = str.length();
        index = new int[n];
        CircularSuffix[] suffixes = new CircularSuffix[n];
        for (int i = 0; i < n; ++i) {
            suffixes[i] = new CircularSuffix(i);
        }
        Arrays.sort(suffixes);
        for (int i = 0; i < n; ++i) {
            index[i] = suffixes[i].index();
        }
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > n - 1) throw new IllegalArgumentException("Index out of range");
        return index[i];
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private int i;
        private int iCounter;

        public CircularSuffix(int index) {
            i = index;
        }

        public int index() {
            return i;
        }

        public int compareTo(CircularSuffix that) {
            iCounter = 0;
            return compareToChar(i, that.i);
        }

        private int compareToChar(int a, int b) {
            iCounter++;
            // StdOut.println(a + " " + b);
            if (str.charAt(a) < str.charAt(b)) return -1;
            else if (str.charAt(a) > str.charAt(b)) return 1;
            else {
                if (iCounter == n - 1) return 0;
                return compareToChar(nextChar(a), nextChar(b));
            }
        }

        private int nextChar(int c) {
            if ((c + 1) > n - 1) return 0;
            return c + 1;
        }
    }

    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(circularSuffixArray.length());
        for (int i = 0; i < circularSuffixArray.length(); ++i) {
            StdOut.println(circularSuffixArray.index(i));
        }
    }
}
