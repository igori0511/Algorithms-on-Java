import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] copyPoints;
    private int numberOfLineSegments;
    private int arraySize = 0;
    private LineSegment[] ls = new LineSegment[1];

    public FastCollinearPoints(Point[] points) {
        checkForNull(points);
        duplicates(points);
        copyPoints = new Point[points.length];
        System.arraycopy(points, 0, copyPoints, 0, points.length);
        calculateCollinearPoints();
        resize(arraySize);
        
    }

    public int numberOfSegments() {
        return numberOfLineSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(ls, ls.length);
    }

    private void calculateCollinearPoints() {
        int n = copyPoints.length;
        Point[] copy = new Point[n];

        int count;
        boolean startPos;
        int pos;

        for (int i = 0; i < n; i++) {
            copy[i] = copyPoints[i];
        }

        for (int p = 0; p < n; p++) {

            // sort the copy array by slope order
            Arrays.sort(copy, copyPoints[p].slopeOrder());

            // set count to zero
            count = 0;
            // check if we found a start position
            startPos = false;
            // init pos to -1
            pos = -1;

            for (int i = 0; i < n - 2; i++) {
                // calculate slope with respect to "p" with "i" point
                double slope1 = copyPoints[p].slopeTo(copy[i]);
                // calculate slope with respect to "p" with "i+1" point
                double slope2 = copyPoints[p].slopeTo(copy[i + 1]);
                // calculate slope with respect to "p" with "i+2" point
                double slope3 = copyPoints[p].slopeTo(copy[i + 2]);

                // if all 4 copyPoints have the same slope count them
                if (checkForSlopeMatching(slope1, slope2, slope3)) {
                    // count number of adjacent point that have the same slope with respect to "p"
                    count = count + 3;
                    if (!startPos) {
                        startPos = true;
                        pos = i;
                    }

                    // handle corner case where the whole array of point has the slope with respect to "p"
                    if (i + 2 == n - 1) {
                        Arrays.sort(copy, pos, i + 3);

                        if (copyPoints[p].compareTo(copy[pos]) < 0) {

                            startPos = false;
                            addLineSegment(new LineSegment(copyPoints[p], copy[i + 2]));
                        }
                    }


                } else if (count + 1 >= 4) // else handle the standard case where we just count items
                {
                    // reset count to count next row of data
                    count = 0;
                    startPos = false;
                    Arrays.sort(copy, pos, i + 2);

                    if (copyPoints[p].compareTo(copy[pos]) < 0) {
                        addLineSegment(new LineSegment(copyPoints[p], copy[i + 1]));
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

    private void addLineSegment(LineSegment lineSegment) {
        if (arraySize == ls.length)
            resize(2 * ls.length);
        LineSegment newLineSegment = lineSegment;
        ls[arraySize++] = newLineSegment;
        ++numberOfLineSegments;
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < arraySize; i++) {
            copy[i] = ls[i];
        }
        ls = copy;
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

    private void duplicates(final Point[] lst) {
        for (int j = 0; j < lst.length; j++)
            for (int k = j + 1; k < lst.length; k++)
                if (k != j && lst[k].compareTo(lst[j]) == 0)
                    throw new IllegalArgumentException("Repeated points found");
    }

    public static void main(String[] args) {

        // read the n copyPoints from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the copyPoints
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