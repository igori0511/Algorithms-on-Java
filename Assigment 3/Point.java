/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;


public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    
    // define comparator by slope order
    private  class BySlopeOrder implements Comparator<Point>
    {
           public int compare(Point v, Point w)
           {
               // calculate slope of invoking  Point with Point v
               double slope1 =  slopeTo(v);
               // calculate slope of invoking  invoking Point with Point w
               double slope2 =  slopeTo(w);
               
               // return -1 if invoking  point has a lower slope with point 1
               if(slope1 < slope2) return -1; 
               // return 1 if invoking  point has a bigger slope with point 2
               else if(slope1 > slope2) return 1;
               // return 0 if points slope is equal 
               else return 0;
           }
    }
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        
        // check if the line is a degenerate line
        if(this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        // check if the line is a horizontal line
        if(this.y == that.y) return  0.0;
        // check if the line is a vertical line
        if(this.x == that.x) return Double.POSITIVE_INFINITY;
        //else compute the slope
        return (double)(this.y-that.y)/(this.x-that.x);        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        // if this point is less then that point return -1
        if(this.y < that.y || this.y == that.y && this.x < that.x)      return -1;
        // if this point is greater than that point
        else if(this.y > that.y || this.y == that.y && this.x > that.x) return  1;
        // else if this point is equal to that point
        else  return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}