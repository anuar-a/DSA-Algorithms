/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> treeSet;

    // construct an empty set of points
    public PointSET() {
        treeSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return treeSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        treeSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        return treeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : treeSet) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius();
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument");
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D p : treeSet) {
            if (rect.contains(p)) stack.push(p);
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (isEmpty()) return null;
        Point2D candidate = null;
        for (Point2D point : treeSet) {
            if (candidate == null) candidate = point;
            else if (point.distanceSquaredTo(p) < candidate.distanceSquaredTo(p)) candidate = point;
        }
        return candidate;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        StdOut.println(brute.size());
        brute.draw();
        Point2D p = new Point2D(0.3732, 0.497);
        StdOut.println(brute.nearest(p));
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        StdOut.println(brute.range(rect));
    }
}
