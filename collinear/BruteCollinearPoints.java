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

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p = points[i];
                Point q = points[j];
                double slop = p.slopeTo(q);

                int k = j + 1;
                for (; k < n; k++) {
                    double kslop = p.slopeTo(points[k]);
                    if (kslop != slop) {
                        break;
                    }
                }

                if (k - i >= 4) {
                    lineSegments.add(new LineSegment(p, points[k-1]));
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
        StdDraw.setPenColor(Color.CYAN);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    }
}
