/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int n;
    private boolean[] openSites;
    private int openSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("the size must greater than zero");
        }
        this.n = n;
        int size = n * n;
        uf = new WeightedQuickUnionUF(size + 2);
        openSites = new boolean[size + 2];
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(size + 1, size + 1 - i);
        }
    }

    private int toId(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException(
                    String.format("(%s, %s) out of range:%s", row, col, n));
        }
        int id = (row - 1) * n + col;
        return id;
    }

    private boolean valid(int id) {
        return id >= 1 && id <= n * n;
    }

    private void unionIfOpen(int p, int q) {
        if (valid(q) && openSites[q]) {
            uf.union(p, q);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int id = toId(row, col);
        if (!openSites[id]) {
            openSites[id] = true;
            openSize++;
            if (col > 1) {
                unionIfOpen(id, id - 1);
            }
            if (col < n) {
                unionIfOpen(id, id + 1);
            }
            if (row > 1) {
                unionIfOpen(id, id - n);
            }
            if (row < n) {
                unionIfOpen(id, id + n);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openSites[toId(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int id = toId(row, col);
        if (openSites[id]) {
            return uf.find(0) == uf.find(id);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }

    public static void main(String[] args) {

    }
}
