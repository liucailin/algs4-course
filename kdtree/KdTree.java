/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            size++;
        }
        else if (insert(root, p, 0)) size++;
    }

    private boolean insert(Node n, Point2D p, int level) {

        if (p.equals(n.p)) return false;

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
                return insert(n.lb, p, level + 1);
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
                return insert(n.rt, p, level + 1);
            }
        }

        return true;
    }


    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
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
        if (rect == null) throw new IllegalArgumentException("called range() with a null key");
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


    public Point2D nearest(Point2D p) {

        if (p == null) throw new IllegalArgumentException("called nearest() with a null key");
        if (root == null) return null;
        return nearest(p, root.p, root, true);
    }

    private Point2D nearest(Point2D p, Point2D c, Node n, boolean xcmp) {

        Point2D closest = c;
        if (n == null) return closest;

        double d = n.rect.distanceSquaredTo(p);
        double closed = p.distanceSquaredTo(closest);
        if (d < closed) {
            if (p.distanceSquaredTo(n.p) < closed)
                closest = n.p;
            Node near;
            Node far;
            if ((xcmp && (p.x() < n.p.x())) || (!xcmp && (p.y() < n.p.y()))) {
                near = n.lb;
                far = n.rt;
            }
            else {
                near = n.rt;
                far = n.lb;
            }
            closest = nearest(p, closest, near, !xcmp);
            closest = nearest(p, closest, far, !xcmp);
        }

        return closest;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(1, 0.25));
        kdTree.insert(new Point2D(0.75, 1));
        kdTree.insert(new Point2D(1, 0.5));
        kdTree.insert(new Point2D(0, 0));
        kdTree.insert(new Point2D(1, 0.5));

        StdOut.println(kdTree.size());
    }
}
