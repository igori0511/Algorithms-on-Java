public class PercolationStats
{
   private int T;
   private int N;
   private double u;
   private double q;
   
   private double average [];
    
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {
       if(N <=0 || T<=0) throw new java.lang.IllegalArgumentException("IllegalArgument!");  
       
       this.T = T;
       this.N = N;
       
       average = new double[T];
       
       for(int k = 0;k < T;k++)
       {
         Percolation test = new Percolation(N);
         int count = 0;
         while(!test.percolates())
         {
             int i = StdRandom.uniform(1,N+1);
             int j = StdRandom.uniform(1,N+1);
             
             if(!test.isOpen(i,j))
             {
                test.open(i,j);
                count++;
             }
             
         }
         average[k] = (double)count/(N*N);
        
           
       }
       
       printResults();
      
   }
   
   public double mean()                     // sample mean of percolation threshold
   {
     return StdStats.mean(average);  
    
   }
   
   public double stddev()                   // sample standard deviation of percolation threshold
   {
     return StdStats.stddev(average); 
     
   }   
   
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   {
     return u-((1.96*q)/Math.sqrt(T));  
   }
   
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   {
     return u+((1.96*q)/Math.sqrt(T)); 
   }
   
   private void printResults()
   {
       u = mean();
       q = stddev();
       StdOut.println("mean                    = " + u);
       StdOut.println("stddev                  = " + q);
       StdOut.println("95% confidence interval = " + confidenceLo() + ", " + confidenceHi());
   }
   
   public static void main(String[] args)   // test client, described below
   {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       
       PercolationStats ps = new PercolationStats(N,T);
       
   }
    
   
}
