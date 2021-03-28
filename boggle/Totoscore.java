/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdOut;

public class Totoscore {
    public static void main(String[] args) {
        In in1 = new In("1.txt");
        Out out = new Out("var-res.txt");
        String[] line1;
        String[] line2;
        for (int j = 1; j < 14; j++) {
            line1 = in1.readLine().split("5-");
            In in2 = new In("var" + j + ".txt");
            for (int i = 0; i < 13; ++i) {
                line2 = in2.readLine().split("5-");
                out.println(line1[0] + "5-" + line2[1]);
            }
        }
        String[] line;
        In inFinal = new In("var-res.txt");
        Out outFinal = new Out("var-res-final.txt");
        for (int i = 0; i < 169; i++) {
            line = inFinal.readLine().split("\\Q4-(1:1)\\E");
            StdOut.println(line[0] + " " + line[1]);
            outFinal.println(line[0] + "4-(" + line[1].charAt(20) + ":" + line[1].charAt(22) + ")"
                                     + line[1]);
        }
    }
}
