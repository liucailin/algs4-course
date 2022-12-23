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

public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(
            Point[] points) { // finds all line segments containing 4 or more points
        if (points == null) {
            throw new IllegalArgumentException("points is null");
        }
        int n = points.length;

        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("entry in point is null: " + i);
            }
            for (int j = i + 1; j < n; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException("entry in point is null: " + j);
                }
                if (points[j].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "entry in point is duplicate: " + i + ", " + j);
                }
            }
        }

        Point[] source = new Point[n];
        System.arraycopy(points, 0, source, 0, n);
        lineSegments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p = source[i];
            Arrays.sort(points, 1, n);
            Arrays.sort(points, p.slopeOrder());
            Point q = null;
            int j = 1;
            int c = 0;

            while (j < n) {
                if (q == null) {
                    if (points[j].compareTo(p) > 0) {
                        q = points[j];
                        c = 0;
                    }
                    j++;
                }
                else if (p.slopeTo(points[j]) == p.slopeTo(q)) {
                    c++;
                    j++;
                }
                else if (c >= 2) {
                    break;
                }
                else {
                    q = null;
                }
            }

            if (c >= 2) {
                LineSegment lineSegment = new LineSegment(p, points[j - 1]);
                lineSegments.add(lineSegment);
            }

        }
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() { // the line segments
        LineSegment[] segments = new LineSegment[lineSegments.size()];
        segments = lineSegments.toArray(segments);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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
