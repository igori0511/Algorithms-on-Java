import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
   
   // create a TreeSet with point 2d objects 
   private TreeSet<Point2D> Points;
   
   // construct an empty set of points
   public PointSET()                               
   {
       // initialize a empty TreeSet
       Points = new TreeSet<Point2D>();
   }
   
   // is the set empty?
   public boolean isEmpty()                        
   {
       return Points.isEmpty();
   }
   
   // number of points in the set
   public int size()                               
   {
       return Points.size(); 
   }
   
   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p)                   
   {
       Points.add(p);
   }
   
   // does the set contain the point p?
   public boolean contains(Point2D p)              
   {
       return Points.contains(p);
   }
   
   // draw all of the points to standard draw
   public void draw()                              
   {
       for(Point2D p: Points)
       {
          StdDraw.point(p.x(),p.y()); 
       }
   }
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)     
   {
       // construct a Queue with Point2D objects
       Queue<Point2D> pointsQueue = new Queue<Point2D>();
       
       for(Point2D p: Points)
       {
           if(rect.contains(p)) pointsQueue.enqueue(p);       
       }
       
       // return containing points
       return pointsQueue;
   }
   
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p)               
   {
       //get the min point
       double min = Double.POSITIVE_INFINITY;
       Point2D goal = null;
       
       for(Point2D x: Points)
       {
           double dist = x.distanceTo(p);
           if( dist < min)
           {
               min = dist; 
               goal = x;
           }    
       }      
              
       return goal;
   }
   
   

}