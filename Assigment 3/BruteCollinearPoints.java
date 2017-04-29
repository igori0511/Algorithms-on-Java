import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] copyPoints;
    private int arraySize = 0;
    private int numberOfLineSegments;
    private LineSegment[] ls = new LineSegment[1];

    public BruteCollinearPoints(Point[] points) {
        copyPoints = new Point[points.length];
        System.arraycopy(points, 0, copyPoints, 0, points.length);
        checkForNull(points);
        computeSegments();
        resize(arraySize);
    }

    public int numberOfSegments() {
        return numberOfLineSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(ls, ls.length);
    }

    private void computeSegments() {
        final int pointNumber = copyPoints.length;

        Arrays.sort(copyPoints);
        double slopepq, slopepr, slopeps;
        double precision = 0.0000001;

        for (int p = 0; p < pointNumber; p++) {
            checkForNull(copyPoints[p]);
            for (int q = p + 1; q < pointNumber; q++) {

                if (copyPoints[p].compareTo(copyPoints[q]) == 0) {
                    throw new IllegalArgumentException("Repeated points found");
                }

                for (int r = q + 1; r < pointNumber; r++) {
                    for (int s = r + 1; s < pointNumber; s++) {
                        slopepq = copyPoints[p].slopeTo(copyPoints[q]);
                        slopepr = copyPoints[p].slopeTo(copyPoints[r]);
                        slopeps = copyPoints[p].slopeTo(copyPoints[s]);

                        if (checkForSlopeMatching(slopepq, slopepr, slopeps)) {
                            addLineSegment(new LineSegment(copyPoints[p], copyPoints[s]));
                        }
                    }
                }
            }
        }
    }

    private void checkForNull(Object argument) {
        if (argument == null) {
            throw new NullPointerException("Argument is null");
        }
    }

    private void resize(int capacity) {

        LineSegment[] copy = new LineSegment[capacity];

        for (int i = 0; i < arraySize; i++) {
            copy[i] = ls[i];
        }

        ls = copy;

    }

    private void addLineSegment(LineSegment lineSegment) {
        if (arraySize == ls.length)
            resize(2 * ls.length);
        LineSegment newLineSegment = lineSegment;
        ls[arraySize++] = newLineSegment;
        ++numberOfLineSegments;
    }

    private boolean checkForSlopeMatching(double slope1, double slope2, double slope3) {

        double precision = 0.0000001;

        boolean a = (Math.abs(slope1 - slope2) < precision &&
                     Math.abs(slope1 - slope3) < precision);

        boolean b = slope1 == Double.POSITIVE_INFINITY &&
                    slope2 == Double.POSITIVE_INFINITY &&
                    slope3 == Double.POSITIVE_INFINITY;

        boolean c = slope1 == Double.NEGATIVE_INFINITY &&
                    slope2 == Double.NEGATIVE_INFINITY &&
                    slope3 == Double.NEGATIVE_INFINITY;

        return a || b || c;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
