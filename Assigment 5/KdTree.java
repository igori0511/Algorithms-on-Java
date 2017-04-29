import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree
{
    
   private Node root = null;
   private int N;
    
    
   private class Node{
       // the point
       private Point2D p;
       // the axis-aligned rectangle corresponding to this node
       private RectHV rect;
       // the left/bottom subtree
       private Node lb;
       // the right/top subtree
       private Node rt;
       
       public Node(Point2D p,RectHV rect)
       {
           this.p    = p;
           this.rect = rect;
       }
   }

   // is the set empty?
   public boolean isEmpty()                        
   {
       return this.root == null;
   }
   
   // number of points in the set
   public int size()                               
   {
       return N;
   }
   
   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p)                   
   {
       N++;
       root = insert(root,p,0,false,null);
   }
   
   //private method to add a point
   private Node insert(Node x, Point2D p,int level,boolean turn,Node root)
   {
       //insert a point into the tree
      if(x == null) return new Node(p,setRectHV(root,level-1,turn));
       
       //set the root to prev node
      root = x;
       
       // compare points to choose the path to go to
      int cmp =  compare(x.p,p,level);      
    
      // if p < x.p go left
      if     (cmp < 0)
        x.lb  = insert(x.lb, p, ++level,true,root);
      // if p > x.p go right
      else if(cmp > 0)
        x.rt  = insert(x.rt, p, ++level,false,root);
      // else update value
      else{x.p = p;N--;}
       
       //return the link to the node up the tree 
       return x;
   }
   
   
   //set the rectangle depending on the rules
   private RectHV setRectHV(Node root, int level,boolean turn)
   {
       //if the tree is empty set the first point to the full rectangle
       if(root == null) return new RectHV(0,0,1,1);
       
       //else if compare with the root with vertical line
       else if(level % 2 == 0)
       {
           //if left
           if(turn) return new RectHV(root.rect.xmin(),root.rect.ymin(),root.p.x(),root.rect.ymax());
           //if right
           return new RectHV(root.p.x(),root.rect.ymin(),root.rect.xmax(),root.rect.ymax());
        //else if we compare against the root with horizintal line and we need to go left   
       }else if(turn)
           return new RectHV(root.rect.xmin(),root.rect.ymin(),root.rect.xmax(),root.p.y()); 
       //else if we need to go right       
       return new RectHV(root.rect.xmin(),root.p.y(),root.rect.xmax(),root.rect.ymax());
   }
   
   //compare two points by x and y coordiantes and equality
   private int compare(Point2D root,Point2D p,int level)
   {
       // if points are equal return 0
       if(p.equals(root)){return 0;}
       //if level is even compare by x coordinates
       else if(level % 2 == 0)return comparePoints(p.x(),root.x());
       //elve level if odd compare by y coordinates
       return comparePoints(p.y(),root.y());
       
   }
   
  //compare by x and y coodinates
   private int comparePoints(double p, double root)
   {
       // if point p(points to be inserted) if less than root node return -1
       if(p < root)         return -1;   
       // else if they are equal by their x or y coordinates return 1 to go right
       return 1;
   }
  
   
   // does the set contain the point p?
   public boolean contains(Point2D p)              
   {
       //call provate method to get po
       return contains(root,p,0);
   }
   
   //private method to check if the tree contains a point "p"
   private boolean contains(Node x, Point2D p, int level)
   {
       
      // return false if we reached null node
      if(x == null) return false;
      
      // choose direction
      int cmp = compare(x.p,p,level);
       
      // if p < x.p go left
      if     (cmp < 0)
        return contains(x.lb, p, ++level);
      // if p > x.p go right
      else if(cmp > 0)
        return contains(x.rt, p, ++level);
      else
        return true;      
             
   }
   
   // draw all of the points to standard draw
   public void draw()                              
   {
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.line(0, 0, 1, 0);
       StdDraw.line(1, 0, 1, 1);
       StdDraw.line(1, 1, 0, 1);
       StdDraw.line(0, 1, 0, 0);
       
       draw(root,false);
   }
   
   private void draw(Node x, boolean flag)
   {
       
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(.005);
       x.p.draw();
       StdDraw.setPenRadius(.001);
       
       if(flag == true)
       {
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.line(x.rect.xmin(),x.p.y(),x.rect.xmax(), x.p.y());
       }
       else
       {   StdDraw.setPenColor(StdDraw.RED);
           StdDraw.line(x.p.x(),x.rect.ymin(),x.p.x(),x.rect.ymax());
       }
       
       if(x.lb != null)  draw(x.lb,!flag);
       if(x.rt != null)  draw(x.rt,!flag);
   }
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)     
   {
       Queue<Point2D> points = new Queue<Point2D>();
       
       if (rect != null && root != null)return range(root,points,rect);
       return null;
   }
   
   // private method to find points in the query rectangle
   private Queue<Point2D> range(Node x, Queue Q,RectHV queryRect)
   {
       
       if(queryRect.intersects(x.rect))
       {
           if(queryRect.contains(x.p))
             Q.enqueue(x.p); 
       }      
       else return Q;
       
       if(x.lb != null)Q = range(x.lb,Q,queryRect);
       if(x.rt != null)Q = range(x.rt,Q,queryRect);             
             
       return Q;
   }
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p)               
   {
       if(p != null && root != null)return nearest(root,p,root.p,0);
       return null;
   }
   
   //private method to find the nearest point
   private Point2D nearest(Node x,Point2D queryPoint,Point2D min,int level)
   {
       
       if(min.distanceSquaredTo(queryPoint)< x.rect.distanceSquaredTo(queryPoint))
          return min;
       
       if(x.p.distanceSquaredTo(queryPoint) < min.distanceSquaredTo(queryPoint))
         min = x.p;

         
       if(level % 2 == 0)
       {  
           if(queryPoint.x() < x.p.x())
           {
               if(x.lb != null) min = nearest(x.lb,queryPoint,min,++level);
               if(x.rt != null) min = nearest(x.rt,queryPoint,min,++level);
           }
           else 
           {
               if(x.rt != null) min = nearest(x.rt,queryPoint,min,++level);
               if(x.lb != null) min = nearest(x.lb,queryPoint,min,++level);
           }
       }
       else
       {
           if(queryPoint.y() < x.p.y())
           {
               if(x.lb != null) min = nearest(x.lb,queryPoint,min,++level);
               if(x.rt != null) min = nearest(x.rt,queryPoint,min,++level);
           }
           else 
           {
              if(x.rt != null) min = nearest(x.rt,queryPoint,min,++level);
              if(x.lb != null) min = nearest(x.lb,queryPoint,min,++level);      
           }

       }
  
       return min;
   }
   
}
