/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPercolation {


    private Percolation createfromInput(String name) {
        In in = new In(name);      // input file
        int n = in.readInt();         // n-by-n percolation system


        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        return perc;
    }

    @Test
    public void testInput6() {
        Percolation percolation = createfromInput("input20.txt");
        Assertions.assertTrue(percolation.isFull(18, 1));
    }

    @Test
    public void testCorner() {
        Percolation percolation = new Percolation(1);
        Assertions.assertFalse(percolation.percolates());
    }
}
