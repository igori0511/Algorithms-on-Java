
import java.util.Arrays;
public class Fast {
    // create a cinstructor for the grader
    public Fast(){}
    
    //print results
    private static void printRes(Point refPoint, Point[] copy, int startPos, int endPost)
    {
      System.out.print(refPoint);
      
      for(int x = startPos; x <= endPost;x++)
         System.out.print(" -> "+copy[x]);
         
      System.out.println();    
    }   
    
    // adopted binary search for Point class
    private static boolean binarySearch(Point refPoint, double refPointSlope, Point[] copy)
    {
        
        int lo = 0; int hi = copy.length-1;
        
        while(lo <= hi)
        {
            int mid = lo + (hi-lo)/2;
            
            if(refPointSlope < refPoint.slopeTo(copy[mid]))       hi = mid - 1;
            
            else if(refPointSlope > refPoint.slopeTo(copy[mid]))  lo = mid + 1;
            
            else return true;            
        }
        
        return false;
        
    }
    
    private static boolean check(Point refPoint,Point subsetFound, Point[] points,int loopIndex)
    {
        
        double slopeToNextPoint = refPoint.slopeTo(subsetFound);
        

        
        for(int x = 0; x < loopIndex; x++)
        {
            double refPointSlope = refPoint.slopeTo(points[x]);
            
            if(slopeToNextPoint == refPointSlope)
                return true;    
            
            
        }
        return false;
        
    }
    
    // Given a set of N distinct points in the plane, 
    //draw every (maximal) line segment that connects a subset of 4 or more of the points.
    private static void testPoints(Point[] points)
    {
        
       int N = points.length;
       int count;
       boolean isSubset;
       boolean startPos;
       int pos;
       // sort the array by natural order
       Arrays.sort(points);
       
       for(int p = 0; p < N-3; p++)
       {
           
           
           // determine the length of temporary array
           int tempLength = N-p-1;
           // create a temporary array
           Point[] copy = new Point[tempLength];
           // copy the points array starting from position (1+p)
           for(int i = 0; i < tempLength; i++)
           {
            copy[i] = points[i+1+p];
           }

           // sort the copy array by slope order
           Arrays.sort(copy,points[p].SLOPE_ORDER);
              
           
           // set count to zero
           count = 0;
           // check if we found a start position
           startPos = false;
           // init pos to -1
           pos = -1;

           for(int i = 0; i < tempLength-2; i++)
           {
               // calculate slope with respect to "p" with "i" point
               double slope1 = points[p].slopeTo(copy[i]);
               // calculate slope with respect to "p" with "i+1" point
               double slope2 = points[p].slopeTo(copy[i+1]);
               // calculate slope with respect to "p" with "i+2" point
               double slope3 = points[p].slopeTo(copy[i+2]);
              
               // if all 4 points have the same slope count them
               if(slope1 == slope2 && slope1 == slope3)
               {
                 // count number of adjacent point that have the same slope with respect to "p" 
                 count = count + 3;
                 if(!startPos){startPos = true;pos = i;}
                 
                 // handle corner case where the whole array of point has the slope with respect to "p"
                 if(i+2 == tempLength-1)
                    if(!check(points[p], copy[pos],points,p))
                    {
                         points[p].drawTo(copy[i+2]);
                         startPos = false;
                         printRes(points[p],copy,pos,i+2);                           
                    } 
                 
                  
               }else if(count+1 >= 4) // else handle the standart case where we just count items
                {
                    // reset count to count next row of data
                    count = 0;
                    startPos = false;
                    
                    if(!check(points[p], copy[pos],points,p))
                    {
                        points[p].drawTo(copy[i+1]);
                        printRes(points[p],copy,pos,i+1);
                    }
                   
                }
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