import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int N;
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private Point[] aux;
    private Point[] temp;

    public FastCollinearPoints(Point[] points) {
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
        // segments = new LineSegment[n * n];
        aux = new Point[n];
        for (int i = 0; i < n; ++i) {
            reset(points);
            /* for (Point p : aux) {
                StdOut.print(p + " ");
            }
            StdOut.println();
            StdOut.println(points[i]); */
            Arrays.sort(aux, points[i].slopeOrder());
            /* StdOut.println(aux[i]);
            for (Point p : aux) {
                StdOut.print(p + " ");
            }
            StdOut.println();
            for (Point p : aux) {
                StdOut.print(points[i].slopeTo(p) + " ");
            }
            StdOut.println();
            StdOut.println("=========================================="); */
            Stack<Point> stack = new Stack<>();
            for (int j = 0; j < n; ++j) {
                // StdOut.println("Stack 1" + stack);
                if (stack.isEmpty() || points[i].slopeTo(stack.peek()) == points[i]
                        .slopeTo(aux[j])) {
                    stack.push(aux[j]);
                }
                else if (points[i].slopeTo(stack.peek()) != points[i].slopeTo(aux[j])) {
                    if (stack.size() >= 3) {
                        temp = new Point[stack.size() + 1];
                        int k = 0;
                        while (!stack.isEmpty()) {
                            temp[k++] = stack.pop();
                        }
                        temp[temp.length - 1] = points[i];
                        Arrays.sort(temp);
                        /* StdOut.print("Temp ");
                        for (Point p : temp) {
                            StdOut.print(p);
                        }
                        StdOut.println(); */
                        LineSegment ls = new LineSegment(temp[0], temp[temp.length - 1]);
                        if (notDuplicate(ls)) {
                            // StdOut.println("At point " + points[i]);
                            segments.add(ls);
                            N++;
                        }
                        stack.push(aux[j]);
                    }
                    else {
                        while (!stack.isEmpty()) {
                            stack.pop();
                        }
                        stack.push(aux[j]);
                    }
                }
                // StdOut.println("Stack 2" + stack);
            }
            if (stack.size() >= 3) {
                temp = new Point[stack.size() + 1];
                int k = 0;
                while (!stack.isEmpty()) {
                    temp[k++] = stack.pop();
                }
                temp[temp.length - 1] = points[i];
                Arrays.sort(temp);
                /* StdOut.print("Temp ");
                for (Point p : temp) {
                    StdOut.print(p);
                }
                StdOut.println(); */
                LineSegment ls = new LineSegment(temp[0], temp[temp.length - 1]);
                if (notDuplicate(ls)) {
                    /* StdOut.println("At point " + points[i] + "Temp size is " + temp.length);
                    for (Point p : temp) {
                        StdOut.print(p + " ");
                    }
                    StdOut.println(); */
                    segments.add(ls);
                    N++;
                }
            }
        }
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return N;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); ++i) {
            segmentsArray[i] = segments.get(i);
        }
        return segmentsArray;
    }

    private void reset(Point[] that) {
        for (int i = 0; i < that.length; ++i) {
            aux[i] = that[i];
        }
    }

    private boolean notDuplicate(LineSegment ls) {
        for (LineSegment lineSegment : segments) {
            if (lineSegment != null && lineSegment.toString().equals(ls.toString())) return false;
        }
        return true;
    }

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
        FastCollinearPoints fastcollinearpoints = new FastCollinearPoints(points);
        for (LineSegment segment : fastcollinearpoints.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
