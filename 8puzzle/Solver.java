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
    private static class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private int priority;
        private Node prev;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            int manhattan = board.manhattan();
            this.prev = prev;
            priority = manhattan + moves;
        }

        public int compareTo(Node that) {
            return this.priority - that.priority;
        }
    }


    private Node solution;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Initial board can not be null");
        MinPQ<Node> searchPQ = new MinPQ<>();
        searchPQ.insert(new Node(initial, 0, null));
        searchPQ.insert(new Node(initial.twin(), 0, null));

        while (!searchPQ.isEmpty()) {
            Node searchNode = searchPQ.delMin();

            if (searchNode.board.isGoal()) {

                Node p = searchNode;
                while (p.prev != null) {
                    p = p.prev;
                }

                if (p.board == initial) {
                    solution = searchNode;
                }

                break;
            }

            for (Board b :
                    searchNode.board.neighbors()) {
                if (searchNode.prev != null && b.equals(searchNode.prev.board)) {
                    continue;
                }
                searchPQ.insert(new Node(b, searchNode.moves + 1, searchNode));
            }

        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solution != null)
            return solution.moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution != null) {
            Stack<Board> stack = new Stack<>();
            Node p = solution;
            while (p != null) {
                stack.push(p.board);
                p = p.prev;
            }
            return stack;
        }
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
            int s = 0;
            for (Board board : solver.solution()) {
                StdOut.println(board);
                s++;
            }
            StdOut.println("Minimum number of moves = " + solver.moves());
            StdOut.println("Solution size = " + s);
        }
    }
}
