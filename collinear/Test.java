/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        double[] array = new double[7];
        array[0] = -1.0;
        array[1] = +1.0;
        array[2] = -0.0;
        array[3] = +0.0;
        array[4] = Double.POSITIVE_INFINITY;
        array[5] = Double.NEGATIVE_INFINITY;
        Arrays.sort(array);
        for (double d : array) {
            StdOut.println("Item " + d);
        }
    }
}
