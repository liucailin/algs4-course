/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

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

        ArrayList<Board> neighbors = new ArrayList<>();

        int r = 0;
        int c = 0;

        int[][] tilesCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char t = tiles[i * n + j];
                tilesCopy[i][j] = t;
                if (t == 0) {
                    r = i;
                    c = j;
                }
            }
        }

        if (r > 0) {
            neighbors.add(new Board(exch(tilesCopy, r, c, r - 1, c)));
            exch(tilesCopy, r, c, r - 1, c);
        }
        if (r < n - 1) {
            neighbors.add(new Board(exch(tilesCopy, r, c, r + 1, c)));
            exch(tilesCopy, r, c, r + 1, c);
        }
        if (c > 0) {
            neighbors.add(new Board(exch(tilesCopy, r, c, r, c - 1)));
            exch(tilesCopy, r, c, r, c - 1);
        }
        if (c < n - 1) {
            neighbors.add(new Board(exch(tilesCopy, r, c, r, c + 1)));
            exch(tilesCopy, r, c, r, c + 1);
        }

        return neighbors;
    }


    private int[][] exch(int[][] a, int fi, int fj, int ti, int tj) {
        int t = a[fi][fj];
        a[fi][fj] = a[ti][tj];
        a[ti][tj] = t;
        return a;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int r1 = -1;
        int c1 = -1;
        int r2 = -1;
        int c2 = -1;

        int[][] tilesCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char t = tiles[i * n + j];
                tilesCopy[i][j] = t;
                if (t != 0) {
                    if (r1 < 0) {
                        r1 = i;
                        c1 = j;
                    }
                    else if (r2 < 0) {
                        r2 = i;
                        c2 = j;
                    }
                }
            }
        }

        return new Board(exch(tilesCopy, r1, c1, r2, c2));

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
