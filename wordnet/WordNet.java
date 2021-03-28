/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

import java.util.TreeMap;

public class WordNet {
    private Digraph digraph;
    private final TreeMap<String, Bag<Integer>> bst = new TreeMap<String, Bag<Integer>>();
    private final TreeMap<Integer, String> map = new TreeMap<Integer, String>();
    private final SAP sap;
    private int v;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Null argument");
        readSynsets(synsets);
        readHypernyms(hypernyms);
        Topological topological = new Topological(digraph);
        if (!topological.hasOrder()) throw new IllegalArgumentException("Not a DAG");
        int counter = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) counter++;
        }
        if (counter != 1) throw new IllegalArgumentException("Not a rooted DAG");
        sap = new SAP(digraph);
    }

    private void readSynsets(String synsets) {
        In in = new In(synsets);
        String[] synset;
        String[] keys;
        Bag<Integer> bag;
        int value;
        while (!in.isEmpty()) {
            synset = in.readLine().split(",");
            value = Integer.parseInt(synset[0]);
            map.put(value, synset[1]);
            keys = synset[1].split(" ");
            for (String key : keys) {
                if (bst.get(key) == null) {
                    bag = new Bag<Integer>();
                    bag.add(value);
                    bst.put(key, bag);
                }
                else {
                    bst.get(key).add(value);
                }
                // StdOut.print(key + " === ");
            }
            v++;
            // StdOut.println();
        }

        // sap = new SAP(this.digraph);
        // StdOut.println(treeMap.size());
    }

    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        String[] ids;
        int id;
        digraph = new Digraph(v);
        while (!in.isEmpty()) {
            ids = in.readLine().split(",");
            id = Integer.parseInt(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                digraph.addEdge(id, Integer.parseInt(ids[i]));
            }
        }
        // StdOut.println(digraph.E());
        // StdOut.println(digraph.V());
    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return bst.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Null argument");
        return bst.get(word) != null;
    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Null argument");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Word is not in WordNet");
        return sap.length(bst.get(nounA), bst.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Null argument");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Word is not in WordNet");
        int answer = sap.ancestor(bst.get(nounA), bst.get(nounB));
        return answer == -1 ? null : map.get(answer);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(wordNet.isNoun("clarinet"));
        StdOut.println(wordNet.isNoun("Anuar"));
    }
}
