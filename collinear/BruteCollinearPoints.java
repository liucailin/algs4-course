/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

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
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = points[i];
                        double sj = p.slopeTo(points[j]);
                        double sk = p.slopeTo(points[k]);
                        double sl = p.slopeTo(points[l]);
                        if (sj == sk && sj == sl && sk == sl) {
                            lineSegments.add(new LineSegment(p, points[l]));
                        }
                    }
                }
            }
        }
        segments = (LineSegment[]) lineSegments.toArray();
    }

    public int numberOfSegments() {       // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {                // the line segments
        return segments;
    }

    public static void main(String[] args) {

    }
}
