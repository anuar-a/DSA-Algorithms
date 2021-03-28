import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s;
        int answer = 0;
        s = BinaryStdIn.readString();
        int n = s.length();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        for (int i = 0; i < n; ++i) {
            if (circularSuffixArray.index(i) == 0) {
                answer = i;
                break;
            }

        }
        BinaryStdOut.write(answer);
        // StdOut.println(answer);
        for (int i = 0; i < n; ++i) {
            if (circularSuffixArray.index(i) == 0) {
                BinaryStdOut.write(s.charAt(n - 1));
                // StdOut.println(s.charAt(n - 1));
            }
            else {
                BinaryStdOut.write(s.charAt(circularSuffixArray.index(i) - 1));
                // StdOut.println(s.charAt(circularSuffixArray.index(i) - 1));
            }
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first;
        int n;
        char[] t;
        char[] a;
        char[] aux;
        int[] next;
        first = BinaryStdIn.readInt();
        t = BinaryStdIn.readString().toCharArray();
        n = t.length;
        a = new char[n];
        aux = new char[n];
        next = new int[n];
        for (int i = 0; i < n; i++) {
            // a[i] = t[i];
            aux[i] = t[i];
        }
        int[] count = new int[257];
        for (int i = 0; i < n; i++) {
            count[t[i] + 1]++;
        }
        for (int r = 0; r < 256; r++) {
            count[r + 1] += count[r];
        }
        for (int i = 0; i < n; i++) {
            next[count[t[i]]] = i;
            aux[count[t[i]]++] = t[i];
        }
        a[0] = aux[first];
        a[n - 1] = t[first];
        for (int i = 1; i < n - 1; ++i) {
            first = next[first];
            a[i] = aux[first];
        }
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(a[i]);
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
    }
}
