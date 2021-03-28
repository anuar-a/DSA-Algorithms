import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph digraph;
    private BreadthFirstDirectedPathsFast bfsV;
    private BreadthFirstDirectedPathsFast bfsW;
    private int ancestor = Integer.MAX_VALUE;
    private int ancestorMultiple = Integer.MAX_VALUE;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Null argument");
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V())
            throw new IllegalArgumentException("Null argument");
        int candidate = Integer.MAX_VALUE;
        ancestor = Integer.MAX_VALUE;
        bfsV = new BreadthFirstDirectedPathsFast(digraph, v);
        bfsW = new BreadthFirstDirectedPathsFast(digraph, w);
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int distV = bfsV.distTo(i);
                int distW = bfsW.distTo(i);
                if (distV + distW < candidate) {
                    candidate = distV + distW;
                    ancestor = i;
                }
            }
        }
        return candidate == Integer.MAX_VALUE ? -1 : candidate;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V())
            throw new IllegalArgumentException("Null argument");
        int candidate = Integer.MAX_VALUE;
        ancestor = Integer.MAX_VALUE;
        bfsV = new BreadthFirstDirectedPathsFast(digraph, v);
        bfsW = new BreadthFirstDirectedPathsFast(digraph, w);
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int distV = bfsV.distTo(i);
                int distW = bfsW.distTo(i);
                if (distV + distW < candidate) {
                    candidate = distV + distW;
                    ancestor = i;
                }
            }
        }
        return ancestor == Integer.MAX_VALUE ? -1 : ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Null argument");
        for (Integer each : v) {
            if (each == null) throw new IllegalArgumentException("Null argument");
        }
        for (Integer each : w) {
            if (each == null) throw new IllegalArgumentException("Null argument");
        }
        int candidate = Integer.MAX_VALUE;
        ancestorMultiple = Integer.MAX_VALUE;
        bfsV = new BreadthFirstDirectedPathsFast(digraph, v);
        bfsW = new BreadthFirstDirectedPathsFast(digraph, w);
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int distV = bfsV.distTo(i);
                int distW = bfsW.distTo(i);
                if (distV + distW < candidate) {
                    candidate = distV + distW;
                    ancestorMultiple = i;
                }
            }
        }
        return candidate == Integer.MAX_VALUE ? -1 : candidate;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Null argument");
        for (Integer each : v) {
            if (each == null) throw new IllegalArgumentException("Null argument");
        }
        for (Integer each : w) {
            if (each == null) throw new IllegalArgumentException("Null argument");
        }
        int candidate = Integer.MAX_VALUE;
        ancestorMultiple = Integer.MAX_VALUE;
        bfsV = new BreadthFirstDirectedPathsFast(digraph, v);
        bfsW = new BreadthFirstDirectedPathsFast(digraph, w);
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int distV = bfsV.distTo(i);
                int distW = bfsW.distTo(i);
                if (distV + distW < candidate) {
                    candidate = distV + distW;
                    ancestorMultiple = i;
                }
            }
        }
        return ancestorMultiple == Integer.MAX_VALUE ? -1 : ancestorMultiple;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            Stack<Integer> vv = new Stack<Integer>();
            vv.push(11);
            vv.push(12);
            Stack<Integer> ww = new Stack<Integer>();
            ww.push(7);
            ww.push(8);

            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
