/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class Board {

    private int dimension;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        this.tiles = copy(tiles);
    }


    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension);
        sb.append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int k = 0;
        int h = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int t = tiles[i][j];
                k++;
                if (t != 0 && t != k) {
                    h++;
                }
            }
        }

        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int k = 0;
        int h = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int t = tiles[i][j];
                k++;
                if (t != 0 && t != k) {
                    int dh = (t - 1) / dimension - i;
                    int dv = (t - 1) % dimension - j;
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
        if (this.dimension != that.dimension) return false;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        ArrayList<Board> neighbors = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {

                    if (i > 0) neighbors.add(new Board(exch(copy(tiles), i, j, i - 1, j)));
                    if (i < dimension - 1)
                        neighbors.add(new Board(exch(copy(tiles), i, j, i + 1, j)));
                    if (j > 0) neighbors.add(new Board(exch(copy(tiles), i, j, i, j - 1)));
                    if (j < dimension - 1)
                        neighbors.add(new Board(exch(copy(tiles), i, j, i, j + 1)));
                    break;
                }
            }
        }
        return neighbors;
    }

    private int[][] exch(int[][] a, int fi, int fj, int ti, int tj) {
        int t = a[fi][fj];
        a[fi][fj] = a[ti][tj];
        a[ti][tj] = t;
        return a;
    }

    private int[][] copy(int[][] src) {
        int n = src.length;
        int[][] dest = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dest[i][j] = src[i][j];
            }
        }
        return dest;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
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
        System.out.println("neighbors:");
        for (Board b : initial.neighbors()) {
            System.out.println(b);
        }
    }
}
