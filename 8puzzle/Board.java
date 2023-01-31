/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private int n;
    private char[] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new char[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i * n + j] = (char) tiles[i][j];
            }
        }
    }

    private Board(char[] tiles, int n) {
        this.tiles = tiles.clone();
        this.n = n;
    }


    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", (int) tiles[i * n + j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int h = 0;

        for (int i = 0; i < n * n; i++) {
            char t = tiles[i];
            if (t == 0) continue;
            if (t != i + 1) h++;
        }

        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int k = 0;
        int h = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char t = tiles[i * n + j];
                k++;
                if (t != 0 && t != k) {
                    int dh = (t - 1) / n - i;
                    int dv = (t - 1) % n - j;
                    h += Math.abs(dh) + Math.abs(dv);
                }
            }
        }

        return h;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (this.n != that.n) return false;
        for (int i = 0; i < this.n * this.n; i++) {
            if (this.tiles[i] != that.tiles[i]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> neighbors = new Queue<>();

        int blank = 0;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i] == 0) {
                blank = i;
                break;
            }
        }

        int r = blank / n, c = blank % n;
        

        if (r > 0) {
            AddNeighbor(neighbors, blank, blank - n);
        }
        if (r < n - 1) {
            AddNeighbor(neighbors, blank, blank + n);
        }
        if (c > 0) {
            AddNeighbor(neighbors, blank, blank - 1);
        }
        if (c < n - 1) {
            AddNeighbor(neighbors, blank, blank + 1);
        }

        return neighbors;
    }

    private void AddNeighbor(Queue<Board> nei, int blank, int replace) {
        exch(tiles, blank, replace);
        nei.enqueue(new Board(tiles, n));
        exch(tiles, replace, blank);
    }

    private void exch(char[] a, int i, int j) {
        char t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int y = (tiles[0] == 0 || tiles[1] == 0) ? n : 0;
        exch(tiles, y, y + 1);
        Board b = new Board(tiles, n);
        exch(tiles, y + 1, y);
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In("puzzle04.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan:" + initial.manhattan());
        System.out.println("twin:" + initial.twin());
        System.out.println("neighbors:");
        for (Board b : initial.neighbors()) {
            System.out.println(b);
        }
    }
}
