/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return 0;
    }

    public void insert(Point2D p) {
        if (root == null)
            root = new Node(p, new RectHV(0, 0, 1, 1));
        else
            insert(root, p, 0);
    }

    private void insert(Node n, Point2D p, int level) {

        if (p.equals(n.p)) return;

        boolean left;
        boolean xorder = level % 2 == 0;
        if (xorder) left = p.x() < n.p.x();
        else left = p.y() < n.p.y();

        if (left) {
            if (n.lb == null) {
                RectHV pr = n.rect;
                if (xorder)
                    n.lb = new Node(p, new RectHV(pr.xmin(), pr.ymin(), n.p.x(), pr.ymax()));
                else
                    n.lb = new Node(p, new RectHV(pr.xmin(), pr.ymin(), pr.xmax(), n.p.y()));
            }
            else {
                insert(n.lb, p, level + 1);
            }
        }
        else {
            if (n.rt == null) {
                RectHV pr = n.rect;
                if (xorder)
                    n.rt = new Node(p, new RectHV(n.p.x(), pr.ymin(), pr.xmax(), pr.ymax()));
                else
                    n.rt = new Node(p, new RectHV(pr.xmin(), n.p.y(), pr.xmax(), pr.ymax()));
            }
            else {
                insert(n.rt, p, level + 1);
            }
        }
    }


    public boolean contains(Point2D p) {
        return contains(root, p, 0);
    }

    private boolean contains(Node n, Point2D p, int level) {
        if (n == null) return false;
        if (p.equals(n.p)) return true;

        boolean left;
        if (level % 2 == 0) left = p.x() < n.p.x();
        else left = p.y() < n.p.y();

        if (left) return contains(n.lb, p, level + 1);
        else return contains(n.rt, p, level + 1);
    }


    public void draw() {
        draw(root, 0);
    }

    private void draw(Node n, int level) {
        if (n == null)
            return;
        double x = n.p.x();
        double y = n.p.y();
        StdDraw.setPenRadius();
        if (level % 2 == 0) {
            StdDraw.setPenColor(Color.red);
            StdDraw.line(x, n.rect.ymin(), x, n.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.line(n.rect.xmin(), y, n.rect.xmax(), y);
        }
        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x, y);
        if (x <= 0.5)
            StdDraw.textLeft(x, y, String.format("(%s, %s)", x, y));
        else
            StdDraw.textRight(x, y, String.format("(%s, %s)", x, y));
        draw(n.lb, level + 1);
        draw(n.rt, level + 1);
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<>();
        range(rect, points, root);
        return points;
    }

    private void range(RectHV rect, ArrayList<Point2D> points, Node n) {
        if (n == null || !n.rect.intersects(rect)) return;

        if (rect.contains(n.p))
            points.add(n.p);
        range(rect, points, n.lb);
        range(rect, points, n.rt);
    }

    private Node closest;

    public Point2D nearest(Point2D p) {

        closest = root;

        nearest(p, root);
        return closest.p;

        // Node close = root;
        // double closed = root.p.distanceSquaredTo(p);


    }

    private void nearest(Point2D p, Node n) {

        if (n == null) return;

        double d = n.rect.distanceSquaredTo(p);
        double closed = p.distanceSquaredTo(closest.p);
        if (d < closed) {
            if (p.distanceSquaredTo(n.p) < closed)
                closest = n;
            nearest(p, n.lb);
            nearest(p, n.rt);
        }
    }

    public static void main(String[] args) {

    }
}
