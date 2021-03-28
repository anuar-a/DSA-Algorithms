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

public class KdTree {
    private Node root;
    private int counter;
    private Stack<Point2D> stack;
    private Point2D candidate;
    private final RectHV unitSquare = new RectHV(0.0, 0.0, 1.0, 1.0);

    private static class Node {
        private Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private final boolean isX;    // is x/vertical node (y/horizontal otherwise)

        public Node(Point2D p, boolean isX, RectHV rect) {
            this.p = p;
            this.isX = isX;
            this.rect = rect;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return counter == 0;
    }

    // number of points in the set
    public int size() {
        return counter;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        root = insert(root, p, true, unitSquare);
        // StdOut.println(root.p);
    }

    private Node insert(Node x, Point2D p, boolean isX, RectHV rect) {
        if (x == null) {
            counter++;
            return new Node(p, isX, rect);
        }
        double pX = p.x();
        double pY = p.y();
        double xpX = x.p.x();
        double xpY = x.p.y();
        double rectXmin = rect.xmin();
        double rectYmin = rect.ymin();
        double rectXmax = rect.xmax();
        double rectYmax = rect.ymax();
        if (pX == xpX && pY == xpY) {
            x.p = p;
            return x;
        }
        if (x.isX) {
            if (pX < xpX) {
                if (x.lb == null) {
                    RectHV r = new RectHV(rectXmin, rectYmin, xpX, rectYmax);
                    x.lb = insert(x.lb, p, false, r);
                }
                else {
                    x.lb = insert(x.lb, p, false, x.lb.rect);
                }
            }
            else if (pX >= xpX) {
                if (x.rt == null) {
                    RectHV r = new RectHV(xpX, rectYmin, rectXmax, rectYmax);
                    x.rt = insert(x.rt, p, false, r);
                }
                else {
                    x.rt = insert(x.rt, p, false, x.rt.rect);
                }
            }
            // else x.p = p;
        }
        else {
            if (pY < xpY) {
                if (x.lb == null) {
                    RectHV r = new RectHV(rectXmin, rectYmin, rectXmax, xpY);
                    x.lb = insert(x.lb, p, true, r);
                }
                else {
                    x.lb = insert(x.lb, p, true, x.lb.rect);
                }
            }
            else if (pY >= xpY) {
                if (x.rt == null) {
                    RectHV r = new RectHV(rectXmin, xpY, rectXmax, rectYmax);
                    x.rt = insert(x.rt, p, true, r);
                }
                else {
                    x.rt = insert(x.rt, p, true, x.rt.rect);
                }
            }
            // else x.p = p;
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;
        if (p.x() == x.p.x() && p.y() == x.p.y()) return true;
        if (x.isX) {
            if (p.x() < x.p.x()) return contains(x.lb, p);
            else return contains(x.rt, p);
        }
        else {
            if (p.y() < x.p.y()) return contains(x.lb, p);
            else return contains(x.rt, p);
        }
    }

    // draw all points to standard draw
    public void draw() {
        traverse(root);
    }

    private void traverse(Node x) {
        if (x == null) return;
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x.p.x(), x.p.y());
        StdDraw.setPenRadius();
        if (x.isX) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        traverse(x.lb);
        traverse(x.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument");
        stack = new Stack<Point2D>();
        traverseRange(root, rect);
        return stack;
    }

    private void traverseRange(Node x, RectHV that) {
        if (x == null) return;
        if (x.rect.intersects(that)) {
            if (that.contains(x.p)) stack.push(x.p);
            traverseRange(x.lb, that);
            traverseRange(x.rt, that);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (isEmpty()) return null;
        candidate = root.p;
        traverseNearest(root, p);
        return candidate;
    }

    private void traverseNearest(Node x, Point2D that) {
        // StdOut.println("Candidate " + candidate);
        if (x == null) return;
        if (candidate == null) {
            candidate = x.p;
            return;
        }
        else if (x.p.distanceSquaredTo(that) <= candidate.distanceSquaredTo(that)) candidate = x.p;

        if (x.rt != null && x.rt.rect.distanceSquaredTo(that) <= candidate.distanceSquaredTo(that)
                &&
                x.lb != null && x.lb.rect.distanceSquaredTo(that) <= candidate
                .distanceSquaredTo(that)) {
            if (x.lb.rect.distanceSquaredTo(that) < x.rt.rect.distanceSquaredTo(that)) {
                if (x.lb.rect.distanceSquaredTo(that) <= candidate
                        .distanceSquaredTo(that)) {
                    traverseNearest(x.lb, that);
                }
                if (x.rt.rect.distanceSquaredTo(that) <= candidate
                        .distanceSquaredTo(that)) {
                    traverseNearest(x.rt, that);
                }
            }
            else {
                if (x.rt.rect.distanceSquaredTo(that) <= candidate
                        .distanceSquaredTo(that)) {
                    traverseNearest(x.rt, that);
                }
                if (x.lb.rect.distanceSquaredTo(that) <= candidate
                        .distanceSquaredTo(that)) {
                    traverseNearest(x.lb, that);
                }
            }
        }
        else if (x.rt != null && x.rt.rect.distanceSquaredTo(that) <= candidate
                .distanceSquaredTo(that)) {
            traverseNearest(x.rt, that);
        }
        else if (x.lb != null && x.lb.rect.distanceSquaredTo(that) <= candidate
                .distanceSquaredTo(that)) {
            traverseNearest(x.lb, that);
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdOut.println(kdtree.size());
        Point2D p1 = new Point2D(0.36, 0.0);
        Point2D p2 = new Point2D(0.372, 0.497);
        StdOut.println(kdtree.contains(p1));
        StdOut.println(kdtree.contains(p2));
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        StdOut.println(kdtree.range(rect));
        StdOut.println(kdtree.nearest(p1));
        kdtree.draw();
    }
}
