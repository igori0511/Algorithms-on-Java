import java.util.Set;
import java.util.HashSet;


public class SAP{
    
    //initialize the graph
    private Digraph G;
    //
    private int Ancest;

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        this.G = new Digraph(G);

    }
    
    //check if indexes are valid
    private void  IndexOfMany(Iterable<Integer> v, Iterable<Integer> w){
        for(int i:v) checkIndex(i);
        for(int i:w) checkIndex(i);
        
    }
    //  check index
    private void checkIndex(int v)
    {
       // check index 
       if((v < 0 || v > this.G.V()-1)) throw new java.lang.IndexOutOfBoundsException("Vertices index out of bounds!"); 
    }
    
    //run main algorithm to compute the SAP(Shortest ancestor path)
    private int MainAlgo(BreadthFirstDirectedPaths P1,BreadthFirstDirectedPaths P2)
    {
        
       // set min
       int min;
       min = Integer.MAX_VALUE;
       this.Ancest = -1;
       //distance
       int dist = 0; 
       // run main algorithm to find the the SAP(Shortest Ancestor Path) ancestor, if it exists
       for(int i = 0; i < G.V();i++){
          if(P1.hasPathTo(i) && P2.hasPathTo(i))
          {
             dist = P1.distTo(i)+P2.distTo(i);
             if(dist < min)
             {
               min = dist;
               this.Ancest = i;
             }
                        
          }
       }
       
       if(min == Integer.MAX_VALUE) return -1;
       return min;  
    }   
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
       
       //check index
       checkIndex(v);
       checkIndex(w);

       // run BFDP on both vertices
       BreadthFirstDirectedPaths P1 = new BreadthFirstDirectedPaths(this.G,v);
       BreadthFirstDirectedPaths P2 = new BreadthFirstDirectedPaths(this.G,w);
        // return result
       return MainAlgo(P1,P2);
            
    }

    
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        //check index
        checkIndex(v);
        checkIndex(w);
        //if the method will be called without first calling the length routing which gives the common ancester
        length(v,w);
        // return the ancestor
        return this.Ancest;
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
       IndexOfMany(v,w); 
       // set min
       int min;
       min = Integer.MAX_VALUE;
       this.Ancest = -1;
       //distance
       int dist = 0;
       // run BFDP on both vertices
       BreadthFirstDirectedPaths P1 = new BreadthFirstDirectedPaths(this.G,v);
       BreadthFirstDirectedPaths P2 = new BreadthFirstDirectedPaths(this.G,w);
       // return result
       return MainAlgo(P1,P2);  
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        //check indexes
        IndexOfMany(v,w);
        //if the method will be called without first calling the length routing which gives the common ancester
        length(v,w);
        // return ancester
        return this.Ancest;
    }
    

    
    // for unit testing of this class (such as the one below)
    public static void main(String[] args)
    {
            System.out.println();
            In in = new In(args[0]);
            Digraph G = new Digraph(in);
            SAP sap = new SAP(G);
            
            while (!StdIn.isEmpty()) {
                int v = StdIn.readInt();
                int w = StdIn.readInt();
                int length   = sap.length(v, w);
                int ancestor = sap.ancestor(v, w);
                StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            }

    }

}