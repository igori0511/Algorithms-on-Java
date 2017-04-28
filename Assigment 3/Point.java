import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    private class BySlopeOrder implements Comparator<Point> {
        public int compare(Point v, Point w) {

            double slope1 = slopeTo(v);
            double slope2 = slopeTo(w);

            if (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            else return 0;
        }
    }

    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new BySlopeOrder();
    }

    public double slopeTo(Point that) {

        // check if the line is a degenerate line
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        // check if the line is a horizontal line
        if (this.y == that.y) return 0.0;
        // check if the line is a vertical line
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        // else compute the slope
        return (double) (this.y - that.y) / (this.x - that.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y || this.y == that.y && this.x < that.x) return -1;
        else if (this.y > that.y || this.y == that.y && this.x > that.x) return 1;
        else return 0;
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

}