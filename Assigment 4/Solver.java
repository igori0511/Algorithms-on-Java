import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solver for 8 Puzzle
 * The problem: The 8-puzzle problem is a puzzle invented and popularized 
 * by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square blocks 
 * labeled 1 through 8 and a blank square. Your goal is to rearrange the blocks so that they are in order, 
 * using as few moves as possible.You are permitted to slide blocks 
 * horizontally or vertically into the blank square.
 * 
 * @Igor Zimenco
 * @version (a version number or a date)
 */
public class Solver
{
    // determine the number of moves made
    private int moves = -1;
    // start trace node
    private Node startNode;
    // create the first PQ
    private final MinPQ<Node> openset = new MinPQ<Node>();
    //create the second PQ
    private final MinPQ<Node> twinPQ = new MinPQ<Node>();
    //
    private boolean found;
    //
    private boolean state;
    
    // make a test class node
    private class Node implements Comparable<Node>
    {
        // this will contain our board
        private Board board;
        // this will contain the moves made for this node
        private int moves;
        // this will contain the reference to the previous node
        private Node prev;
        // this will contain the manhattan distance
        private int manhDist;
        
        // constructor for the node
        public Node(Board board, int moves, Node prev)
        {
            // set this board equal to board
            this.board = board;
            // set this moves to moves
            this.moves = moves;
            // set this prev to prev
            this.prev  = prev;
            // set this manhattan distance to calculated manhattan distance
            // for this board
            this.manhDist  = board.manhattan() + this.moves;
        }
        
        // compare nodes with manhattan priority
        public int compareTo(Node that)
        {   
            // compare using manhattan distances
            // return -1 denoting that this manhDist < that.manhDist 
            if(this.manhDist < that.manhDist) return -1;
            // return 1 denoting that this manh distance < that manhatan distance
            if(this.manhDist > that.manhDist) return  1;
            // return zero if manhattan distances are equal
            else return 0;
        }
        
    }
    
    
    /**
     * Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial)
    {
       // init Twin board
       twinPQ.insert(new Node(initial.twin(),0,null));
        
       // add initial Node
       openset.insert(new Node(initial,0,null));
       
       state = false;
       found = false;
       //try to solve
       this.isSolvable();      
    }
    
    /**
     * Is the initial board solvable?
     * @return True  if it is solvable
     * @return False if it's not solvable
     */
    public boolean isSolvable()
    {
        
     
      while(!found)
      {
           Node current = openset.delMin(); 
          
           // if it's a goal stop the search
           if(current.board.isGoal())
           {
              startNode = current; 
              moves = current.moves;
              state = true;
              found = true;
              break;              
           }     
           // else add neighbors to the list     
           for(Board neighbor: current.board.neighbors())
           {
                boolean add = true;
                //critical optimization
                if(current.prev != null) 
                {
                    if(neighbor.equals(current.prev.board))
                    {
                        add = false;
                       
                    }
                }
                
                // add the neighbor to the queue if the are not like the prev node
                if(add)  openset.insert(new Node(neighbor, current.moves+1, current));
                
           }
           
           ///////////////////////////////////////////////
           //  Twin Board solution                     //
           //////////////////////////////////////////////
           Node twinCurrent = twinPQ.delMin(); 
          
           // if it's a goal stop the search
           if(twinCurrent.board.isGoal())
           {
              found = true; 
              state =  false; 
              break;             
           }     
           // else add neighbors to the list     
           for(Board neighbor: twinCurrent.board.neighbors())
           {
                boolean add = true;
                //critical optimization
                if(twinCurrent.prev != null) 
                {
                    if(neighbor.equals(twinCurrent.prev.board))
                    {
                        add = false;
                    
                    }
                }
                
                // add the neighbor to the queue if the are not like the prev node
                if(add)  twinPQ.insert(new Node(neighbor, current.moves+1, twinCurrent));
                
           }
          
          
      }
        
      return state;
    }
    
    /**
     * Min number of moves to solve intial board; -1 if no solution
     * @return minimum number of moves to solve the initial board
     */    
    public int moves()
    { return this.moves;}
    
    /**
     * Sequence of boards in a shortest solution; 
     * @return sequence of boards in a shortest solution;
     * @return null if no solution
     */    
    public Iterable<Board> solution()
    {
        Stack<Board> solutions = new Stack<Board>();
        
        Node x = startNode;
        
        while(x != null)
        {
           solutions.push(x.board);
           x = x.prev;
        }
        
        if(startNode == null)
            return null;
            
        return solutions;
    }
    
    /**
     * Solve a slider puzzle
     */
    public static void main(String[] args)
    { 
        
    // create initial board from file
        In in = new In(args[0]); 
        // read the dimension of the board
        int N = in.readInt();
        // create an array of supplied values
        int[][] blocks = new int[N][N];
        
        // read values from file
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        
        // create an empty board
        Board initial = new Board(blocks);
   
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
        System.out.println(solver.solution());
        
        
       
    }
}
