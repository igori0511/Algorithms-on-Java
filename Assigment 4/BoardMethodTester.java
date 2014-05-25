public class BoardMethodTester
{
  
    
    
    public static void main(String[] args)
    { 
        // create initial board from file
        In in = new In(args[0]); 
        // read the dimension of the board
        int N = in.readInt();
        // create an array of supplied values
        int[][] blocks = new int[N][N];
        // make values for it
        int[][] blocks2 = {{1,2,3},{4,5,6},{7,8,0}};
        
     
        // read values from file
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        
        // create an empty board
        Board initial  = new Board(blocks);
        
        Solver solv = new Solver(initial);
        
         
       /* Board initial2 = new Board(blocks2);
        
        System.out.println("Expected : true");
        
        System.out.println("Equality : " + initial.equals(initial) + "\n");
        
        System.out.println("Expected : true");
        
        System.out.println("Equality : " + initial.equals(initial2) + "\n");
        
        System.out.println("Expected : false");
        
        System.out.println("Equality : " + initial.equals(null) + "\n");
        
        System.out.println("Expected : false");
        
        System.out.println("Equality : " + initial.equals(test) + "\n");
        
        System.out.println("Neighbors: ");
        for(Board x: initial.neighbors())
            System.out.println(x);
            
        System.out.println("Twin: ");
        System.out.println(initial.twin());*/
        
    }
}
