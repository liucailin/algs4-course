/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        set.add(p);
    }

    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) throw new IllegalArgumentException("called range() with a null key");
        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;
    }

    public Point2D nearest(Point2D p) {

        if (p == null) throw new IllegalArgumentException("called nearest() with a null key");
        Point2D result = null;
        double min = Double.POSITIVE_INFINITY;
        for (Point2D point : set) {
            double d = p.distanceSquaredTo(point);
            if (d < min) {
                min = d;
                result = point;
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
