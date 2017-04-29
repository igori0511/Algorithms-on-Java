
/**
 * Board for 8 Puzzle
 * The problem: The 8-puzzle problem is a puzzle invented and popularized 
 * by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square blocks 
 * labeled 1 through 8 and a blank square. Your goal is to rearrange the blocks so that they are in order, 
 * using as few moves as possible.You are permitted to slide blocks 
 * horizontally or vertically into the blank square.
 * 
 * @Igor Zimenco
 * @version (a version number or a date)
 */
import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;
public class Board
{
   // private array that will contain our board 
   private final int[][] blocks;
   // dimensions of the board
   private final int N;
    
   /**
    * Construct a board from an N-by-N array of blocks
    * (where bloks[i][j] = block in row i, column j)
    * @param array of blocks
   */ 
   public Board(int[][] blocks)
   {
       
       // init dimension
       N = blocks.length;
       //init array of blocks
       this.blocks = new int[N][N];
       //copy data
       for(int i = 0; i < N; i++)
        for(int j = 0; j < N; j++)
            this.blocks[i][j] = blocks[i][j];
   }
   
   /**
    * Board dimension N
    * @return the dimension of the board
    */
   public int dimension()
   { return N;}
   
   /**
    * Number of blocks out of place
    * @return number of blocks out of place
    */
   public int hamming()
   {
     // number of bloks out of place  
     int  numBlocks = 0;
     // dimensions of the board
     int  dimension = this.dimension();
     
     // start computing how many bloks are out of place
     /* 
      1)compute 1D coordinate 
      2)get the value in the array
      3)compare (value-1) with 1D coordinate
      4)if they don't match then block is out of plac    
     */
     
     for(int x = 0; x < dimension; x++)
     
        for(int y = 0; y < dimension; y++)
        {
            // get value
            int value = blocks[x][y];
            // don't compute for empty block
            if(value != 0)
            {
                // convert to 1D coordinate
                int coord1d = x * dimension + y;
                // compare 1D coordinate ti value 
                // if they are not equal then the block is out of place
                if(coord1d != (value-1))
                  // increment variable +1 - blocks out of place 
                  numBlocks++;                 
            }
        }
     // return the number of bloks out of place              
     return numBlocks;
   }
   
   /**
    * Sum of Manhattan distances between blocks and goal
    * @return sum of Manhattan distances between blocks and goal
    */   
   public int manhattan()
   {
      // manhattan distance sum = 0 
      int manhDistSum = 0;
      // the board dimension
      int dimension = this.dimension();
      // start computting manhattan distance
      // given two points (x1,y1) and (x2,y2)
      // using the formula |x1 - x2| + |y1 - y2|
      // to compute manhattan distance
      for(int x = 0; x < dimension; x++)
        for(int y = 0; y < dimension; y++)
        {
            // get current value
            int value = blocks[x][y];
            // don't compute the value for 0 - empty position
            if(value != 0)
            {
              // compute the proper x position  
              int dx = (value - 1) / dimension;
              // compute the proper y position
              int dy = (value - 1) % dimension;
              // compute the manhattan distance sum using the furmula
              // |x - dx| + |y - dy|
              manhDistSum+= (Math.abs(x - dx) + Math.abs(y - dy));
            }
        }   
      // return resulting manhattan sum 
      return manhDistSum;
   }
   
   /**
    * Is this board the goal board?
    * @return True  if it is a goal board
    * @return False if it's not
    */
   public boolean isGoal()
   {
       // if hamming equals zero then return true(all blocks are in place)
       if (this.hamming() == 0) return true;
       // else block are not in place
       return false;
   }
   
   /**
    * A board obtained by exchanging two adjacent blocks in the same row
    * @return twin Board
    */
   public Board twin()
   {
       int dim = this.dimension();
       // make a clone of the array
       final int[][] twinBlocks = new int[dim][dim];
       
       // copy the array
       this.copy(twinBlocks);
       
       // set swapped to false we have not swaped the blocks     
       boolean swaped = false;
       
       // while not swapped
       while(!swaped)
       {
           // compute a random row
           int x = StdRandom.uniform(dim);
           // compute a random column
           int y = StdRandom.uniform(dim);
           
           // if element at that index is not 0(blank block)
           if(twinBlocks[x][y] != 0)
           {
              // set index for swapping to -1 
              int indexy = -1; 
              // handle corner case where element is in the right most portion of the array
              if     (y == (N-1)) indexy = y - 1;
              // handle corner case where elem is in right portion of the array
              else if(y == 0)     indexy = y + 1;
              // get a random integer position between (y - 1 and y + 2) exclusive 
              else
              {
                 // do the loop while values is == to 0 or indexes match
                 do{ 
                  indexy = StdRandom.uniform(y - 1, y + 2);
                 }while(twinBlocks[x][indexy] == 0 || indexy == y); 
              }
              // if found element is not zero swap elements 
              if(twinBlocks[x][indexy] != 0)
              {
                  // get the coordinate of the found    element
                  int coordMain = coord1d(x,y);
                  // get the coordinate of the adjecent element 
                  int coordAdj  = coord1d(x,indexy);
                  //swap elements
                  swap(twinBlocks, coordMain, coordAdj);
                  // set swapped to true and end the loop
                  swaped = true;
              }
              
           }
       }

      // return board
      return new Board(twinBlocks); 
   }
   
   /**
    * Does this board equal y?
    * @return True if this board equal y
    * @return Else if this board doesn't equal to y
    */
   public boolean equals(Object y)
   { 
     // if references are the same  
     if (y == this) return true;
     // if reference equal to null return false
     if (y == null) return false;
     // if they are not the same classes
     if (y.getClass() != this.getClass()) return false;
     // check deep equality
     Board that = (Board) y;
     return Arrays.deepEquals(this.blocks, that.blocks);
   }
   
   
   //convert to 1d coordinates
   private int coord1d(int x, int y)
   {
      // return 1d coordinate 
      return x * this.dimension() + y;
   }
   
   // helper function that finds zero
   private int findZero(int[][] blocks)
   {
       // get dimension
       int dim = this.dimension();
       
       for(int x = 0; x < dim; x++)
        for(int y = 0; y < dim; y++)
           // find zero 
           if(blocks[x][y] == 0)
                // return 1d coordinate representation
                return coord1d(x,y);
                
      // if fail return -1
      return -1;
   }
   // copy blocks array to a new array
   private void copy(int[][] target)
   {
     // get dimension  
     int dim = this.dimension();
     // start loop and copy values
     for(int x = 0; x < dim; x++)
        for(int y = 0; y < dim; y++)
            // copy values to a target array
            target[x][y] = this.blocks[x][y];
   }
   
   //swap elements in 2d array
   private void swap(int[][] arr, int i, int j )
   {
       //get dimension
       int dim = this.dimension();
       // convert to 2d coordinates
       // get coordinates of zero on array
       int x  = i / dim;      
       int y  = i % dim;
       // get coordinates of point to swap
       int dx = j / dim;
       int dy = j % dim;
       
       //swap elements
       int temp = arr[x][y]; arr[x][y] = arr[dx][dy]; arr[dx][dy] = temp;       
   }
   
   /**
    * All neighboring boards
    * @return Iterable<Board>
    */
   public Iterable<Board> neighbors()
   { 
     // construp a queue of bords  
     Queue<Board> iter = new Queue<Board>();
     
     // get dimensions of the array
     int dim = this.dimension();
     // make a copy of the array
     int[][] copyBlocks = new int[dim][dim];
     this.copy(copyBlocks);
     // get the pos of zero
     int index1d = findZero(this.blocks);
     // convert it to 2d coordinates
     int x = index1d / dim;
     int y = index1d % dim;
     //get 1d coordinates of zero
     int coordZero  = coord1d(x,y);
     // check if we can create neigbors at (x,y-1);(x,y+1);(x-1,y);(x+1,y)
     // right
     if((y+1) < dim)
     {
         int swapCoord = coord1d(x,y+1);
         swap(copyBlocks,coordZero,swapCoord);
         iter.enqueue(new Board(copyBlocks));
         swap(copyBlocks,coordZero,swapCoord); 
     }
     //left
     if((y-1) >= 0)
     {
         int swapCoord = coord1d(x,y-1);
         swap(copyBlocks,coordZero,swapCoord);
         iter.enqueue(new Board(copyBlocks));
         swap(copyBlocks,coordZero,swapCoord);         
     }
     //down
     if((x+1) < dim)
     {
         int swapCoord = coord1d(x+1,y);
         swap(copyBlocks,coordZero,swapCoord);
         iter.enqueue(new Board(copyBlocks));
         swap(copyBlocks,coordZero,swapCoord);         
     }
     
     //up
     if((x-1) >= 0)
     {
         int swapCoord = coord1d(x-1,y);
         swap(copyBlocks,coordZero,swapCoord);
         iter.enqueue(new Board(copyBlocks));
         swap(copyBlocks,coordZero,swapCoord);         
     }
     
     // return queue of neighbors
     return iter;
     
   }
   
   /**
    * String representation of the board
    */
   public String toString() {
        // get dimension of the board
        int dim = this.dimension();
        
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
