/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int N;
    private LineSegment[] segments;


    private boolean distinct(int i, int j, int k, int l) {
        if (i == j) return false;
        if (i == k) return false;
        if (i == l) return false;
        if (j == k) return false;
        if (j == l) return false;
        if (k == l) return false;
        return true;
    }

    private boolean collinear(Point p1, Point p2, Point p3, Point p4) {
        /* StdOut.println(p1.slopeTo(p2) + " " + p1.slopeTo(p3) + " " + p1.slopeTo(p4));
        StdOut.println((p1.slopeTo(p2) == p1.slopeTo(p3)) && (p1.slopeTo(p2) == p1.slopeTo(p4)) && (
                p1.slopeTo(p3) == p1.slopeTo(p4))); */
        return (p1.slopeTo(p2) == p1.slopeTo(p3)) && (p1.slopeTo(p2) == p1.slopeTo(p4)) && (
                p1.slopeTo(p3) == p1.slopeTo(p4));
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument in constructor");
        }
        int n = points.length;
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) throw new IllegalArgumentException("Null point in input array");
            for (int j = 0; j < n; ++j) {
                if (i != j && points[i].equals(points[j])) {
                    throw new IllegalArgumentException("Repeated points");
                }
            }
        }
        segments = new LineSegment[n];
        Point[] tempArray = new Point[4];
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    for (int l = k + 1; l < n; ++l) {
                        if (distinct(i, j, k, l) && collinear(points[i], points[j], points[k],
                                                              points[l])) {
                            /* StdOut.println(points[i] + " " + points[j] + " " + points[k] + " "
                                                   + points[l]); */
                            tempArray[0] = points[i];
                            tempArray[1] = points[j];
                            tempArray[2] = points[k];
                            tempArray[3] = points[l];
                            Arrays.sort(tempArray, Point::compareTo);
                            segments[N++] = new LineSegment(tempArray[0], tempArray[3]);
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        return N;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[N];
        for (int i = 0; i < N; ++i) {
            temp[i] = segments[i];
        }
        return temp;
    }               // the line segments

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints brutecollinearpoints = new BruteCollinearPoints(points);
        for (LineSegment segment : brutecollinearpoints.segments()) {
            if (segment != null) {
                StdOut.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}
