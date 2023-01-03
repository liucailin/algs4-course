/******************************************************************************
 *  Compilation:  javac-algs4 PuzzleChecker.java
 *  Execution:    java-algs4 PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {

    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
        }
    }

    private void debugMinPQ(int step) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(String.format("Step %s:\t", step));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\t\t");
        StringBuilder sb3 = new StringBuilder();
        sb3.append("\t\t");

        StringBuilder[] tiles = null;
        for (Solver.Node n :
                minPQ) {
            sb1.append("\tpriority  = ");
            sb1.append(n.priority);

            sb2.append("\tmoves     = ");
            sb2.append(n.moves);

            sb3.append("\tmanhattan = ");
            sb3.append(n.manhattan);

            // sb4.append(n.board.toString());
            String[] bs = n.board.toString().split("\n");
            if (tiles == null) {
                tiles = new StringBuilder[bs.length];
                for (int i = 0; i < tiles.length; i++) {
                    tiles[i] = new StringBuilder();
                    tiles[i].append("\t\t");
                }
            }
            for (int i = 0; i < bs.length; i++) {
                tiles[i].append("\t");
                tiles[i].append(bs[i]);
                tiles[i].append("\t");
                if (i == 0)
                    tiles[i].append("\t\t");
            }
        }

        // System.out.printf("Step %s:", step);
        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        System.out.println(sb3.toString());
        if (tiles != null) {
            for (StringBuilder sb :
                    tiles) {
                System.out.println(sb.toString());
            }
        }
        System.out.println();
    }
}
