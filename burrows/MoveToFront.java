/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] arr = new char[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(arr[c]);
            for (int i = 255; i >= 0; --i) {
                if (arr[i] < arr[c]) arr[i]++;
            }
            arr[c] = 0;
        }
        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int prev = 0;
        char[] arr = new char[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readInt(8);
            // StdOut.println(c);
            for (int i = 0; i < 256; ++i) {
                if (arr[i] == c) {
                    // StdOut.println(" " + i + " " + (int) arr[65]);
                    BinaryStdOut.write((char) i);
                    prev = i;
                    break;
                }
            }
            // BinaryStdOut.write(arr[c]);
            for (int i = 255; i >= 0; --i) {
                if (arr[i] < arr[prev]) {
                    arr[i]++;
                }
            }
            arr[prev] = 0;
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
    }
}
