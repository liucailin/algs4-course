/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point pi = points[i];

            for (int j = 0; j < n; j++) {
                Point pj = points[j];
                if (pj.compareTo(pi) <= 0) continue;
                double testSlop = pi.slopeTo(pj);

                for (int k = 0; k < n; k++) {
                    Point pk = points[k];
                    if (pk.compareTo(pj) <= 0 || pi.slopeTo(pk) != testSlop) continue;

                    ArrayList<Point> lpoints = new ArrayList<>();
                    for (int l = 0; l < n; l++) {

                        Point pl = points[l];
                        if (i != l && j != l && k != l && pi.slopeTo(pl) == testSlop) {
                            lpoints.add(pl);
                        }

                    }
                    if (lpoints.size() > 0) {
                        Point[] ps = new Point[lpoints.size()];
                        lpoints.toArray(ps);
                        Arrays.sort(ps);

                        if (ps[0].compareTo(pk) > 0) {
                            lineSegments.add(new LineSegment(pi, ps[ps.length - 1]));
                        }
                    }
                }
            }
        }
        segments = new LineSegment[lineSegments.size()];
        segments = lineSegments.toArray(segments);
    }

    public int numberOfSegments() {       // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {                // the line segments
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.setPenColor(Color.magenta);
        StdDraw.setPenRadius(0.008);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.show();
    }
}
