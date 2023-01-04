/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private int priority;
        private int manhattan;
        private Node prev;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.prev = prev;
            priority = manhattan + moves;
        }

        public int compareTo(Node that) {
            return this.priority - that.priority;
        }
    }


    private Stack<Board> solutionStack = new Stack<>();
    private boolean isSolvable;
    private int moves = -1;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Initial board can not be null");
        MinPQ<Node> searchPQ = new MinPQ<>();
        searchPQ.insert(new Node(initial, 0, null));

        MinPQ<Node> twinPQ = new MinPQ<>();
        twinPQ.insert(new Node(initial.twin(), 0, null));

        while (!searchPQ.isEmpty() && !twinPQ.isEmpty()) {
            // debugMinPQ(step, minPQ);
            Node searchNode = searchPQ.delMin();

            if (searchNode.board.isGoal()) {
                isSolvable = true;
                moves = searchNode.moves;

                Node p = searchNode;
                while (p != null) {
                    solutionStack.push(p.board);
                    p = p.prev;
                }

                break;
            }

            Node twinNode = twinPQ.delMin();
            if (twinNode.board.isGoal()) {
                isSolvable = false;
                break;
            }

            for (Board b :
                    searchNode.board.neighbors()) {
                if (searchNode.prev == null || !b.equals(searchNode.prev.board)) {
                    searchPQ.insert(new Node(b, searchNode.moves + 1, searchNode));
                }
            }

            for (Board b :
                    twinNode.board.neighbors()) {
                if (twinNode.prev == null || !b.equals(twinNode.prev.board)) {
                    twinPQ.insert(new Node(b, twinNode.moves + 1, twinNode));
                }
            }
        }
    }

    private void debugMinPQ(int step, MinPQ<Node> minPQ) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(String.format("Step %s:\t", step));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\t\t");
        StringBuilder sb3 = new StringBuilder();
        sb3.append("\t\t");

        StringBuilder[] tiles = null;
        for (Node n :
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

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable)
            return solutionStack;
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            int s = 0;
            for (Board board : solver.solution()) {
                StdOut.println(board);
                s++;
            }
            StdOut.println("Solution size = " + s);
        }
    }
}
