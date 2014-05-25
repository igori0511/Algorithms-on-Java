
/**
 * Write a description of class Brute here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Arrays;
public class Brute
{
    // create a cinstructor for the grader
    public Brute(){}
    
    
    // Given a set of N distinct points in the plane, 
    //draw every (maximal) line segment that connects a subset of 4 or more of the points.
    private static void testPoints(Point[] points)
    {
        final int N = points.length;

        
        Arrays.sort(points);
        
        for(int p = 0; p < N; p++)
            for(int q = p+1; q < N; q++)
                for(int r = q+1; r < N; r++)
                    for(int s = r+1; s < N; s++)
                    {
                        double slopepq = points[p].slopeTo(points[q]);
                        double slopepr = points[p].slopeTo(points[r]);
                        double slopeps = points[p].slopeTo(points[s]);
                        
                        
                        if((slopepq == slopepr && slopepq == slopeps)) 
                        {
                          StdOut.println(points[p] + "->"+ points[q] + "->"+
                                         points[r] + "->"+ points[s]);  
                            
                          points[p].drawTo(points[s]); 
                          
                        }  
                     }
     }                 
    
    
    public static void main(String[] args)
    {
        
        // rescale the coordinate system
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        // get the filename
        String filename = args[0];
        // make a new pointer to filename
        In in = new In(filename);
        // read N - number of pairs in the file
        int N = in.readInt();
        // make an array of size N to store the pairs
        Point[] points = new Point[N];
        // loop thru the file to get paint
        for(int i = 0; i < N; i++)
        {
           // get x coordinate 
           int x = in.readInt();
           // get y coordinate
           int y = in.readInt();
           // store point in the array
           points[i] = new Point(x,y);
           points[i].draw();
        }   
        
        // 
        testPoints(points);
        // display to screen all at once
        StdDraw.show(0);
        
    }
}
