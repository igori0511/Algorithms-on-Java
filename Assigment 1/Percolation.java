public class Percolation {
    
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF full;
    private boolean openClosed[];
    private final int size;
    
    public Percolation(int N) 
    {
        // create an N-by-N brid
        // array that contains percolation grid,containts two virtual sites
        percolation = new WeightedQuickUnionUF(N*N+2); 
        // array that contains one virtual site 
        full        = new WeightedQuickUnionUF(N*N+1);
        // array that keep track wich site is opened
        openClosed  = new boolean[N*N];
        // store the size of the array
        size        = N;
    }
    
    // Map 2D coordinates to 1D coordinates
     private int xyTo1d(int i , int j)
     {
         //check if the indexes are in range
         indeces(i,j); 
         // return 1D index
         return ((i-1) * size) + (j-1);
     }
     
     // check the indices
     private void indeces(int i, int j)
     {
         // check if index "i" is in range
         if (i <= 0 || i > size){
             throw new IndexOutOfBoundsException("row index i out of bounds");
         }
         // check if index "j" is in range
         if (j <= 0 || j > size){
             throw new IndexOutOfBoundsException("row index j out of bounds");
         }
     }
     
    // Open a site   
    public void open(int i, int j)
    {
      // validate indices
      indeces(i,j);
      // calculate global index
      int index = xyTo1d(i,j);
      // open site
      openClosed[index] = true;
      //connect to top virtual site if the index "i" is first row
      if(i == 1){
          percolation.union(size*size,index);
          full.union(size*size,index);
      }
      //connect to bottom virtual site if index "i" is last row
      if(i == size){
         percolation.union(size*size+1,index); 
      }   
      
      
      //union site with it's neighbors.
      // union with j + 1 neighbour
      if(j+1 <= size)
      {
        if(openClosed[index+1] == true)
        {
           percolation.union(index,index+1);
           full.union(index,index+1);
        }  
      }
      // union with j - 1 neighbour
      if(j - 1  > 0)
      {
        if(openClosed[index-1] == true)
        {
           percolation.union(index,index-1);
           full.union(index,index-1);
        }  
      }  
      // union with i + 1 neighbour
      if(i+1 <= size)
      {
        int indexI = xyTo1d(i+1,j);  
        if(openClosed[indexI] == true)
        {
           percolation.union(index,indexI);
           full.union(index,indexI);
        }
      }  
      // union with i - 1 neighbour 
      if(i-1 > 0)
      {
        int indexK = xyTo1d(i-1,j);  
        if(openClosed[indexK] == true)
        {
           percolation.union(index,indexK);
           full.union(index,indexK);
        }  
      }

    }
    
    // check if the site is open
    public boolean isOpen(int i, int j){
    
      // calculate blobal index  
      int index = xyTo1d(i,j);  
      //check if the site is opened
      if(openClosed[index] == true){return true;}
      else {return false;}      
        
    }
    
    // check if the site if full
    public boolean isFull(int i, int j)
    { 
       // check if indices i and j are in the expect range
       indeces(i,j);
       // check if the site is full , return true if the site is full else otherwise
       return full.connected(xyTo1d(i,j),size*size);
       
    }
    // check if the system percolates
    public boolean percolates() 
    {
        //return true if the system percolates
        return percolation.connected(size*size,size*size+1);        
    }
}