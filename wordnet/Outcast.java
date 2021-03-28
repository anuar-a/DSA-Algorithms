/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        // StdOut.println(nouns);
        String answer = null;
        int d = -1;
        for (String nounThis : nouns) {
            int tempSum = 0;
            for (String nounThat : nouns) {
                // StdOut.println(nounThis + " " + nounThat);
                if (!nounThis.equals(nounThat)) {
                    tempSum += wordNet.distance(nounThis, nounThat);
                }
            }
            if (tempSum > d) {
                answer = nounThis;
                d = tempSum;
            }
        }
        return answer;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
