/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;

    public FastCollinearPoints(
            Point[] points) { // finds all line segments containing 4 or more points
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            Point p = points[i];
            Arrays.sort(points, i+1, n, p.slopeOrder());
            Point q = points[i+1];
            double slop = p.slopeTo(q);
            int j = i + 2;
            for (; j < n; j++) {
                if (p.slopeTo(points[j]) != slop) {
                    break;
                }
            }
            if (j - i >= 4) {
                LineSegment lineSegment = new LineSegment(p, points[j-1]);
                lineSegmentArrayList.add(lineSegment);
            }
        }
        lineSegments = new LineSegment[lineSegmentArrayList.size()];
        lineSegments = lineSegmentArrayList.toArray(lineSegments);
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() { // the line segments
        return lineSegments;
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
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
