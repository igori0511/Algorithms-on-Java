import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double u;
    private double q;

    private double[] average;

    public PercolationStats(int n, int trials) // perform T independent
                                               // computational
                                               // experiments on an N-by-N grid
    {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException("IllegalArgument!");

        this.trials = trials;

        average = new double[trials];

        for (int k = 0; k < trials; k++) {
            Percolation test = new Percolation(n);
            int count = 0;
            while (!test.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);

                if (!test.isOpen(i, j)) {
                    test.open(i, j);
                    count++;
                }

            }
            average[k] = (double) count / (n * n);

        }
        u = mean();
        q = stddev();
    }

    public double mean() // sample mean of percolation threshold
    {
        return StdStats.mean(average);

    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        return trials == 1 ? Double.NaN : StdStats.stddev(average);

    }

    public double confidenceLo() // returns lower bound of the 95% confidence
                                 // interval
    {
        return u - ((1.96d * q) / Math.sqrt(trials));
    }

    public double confidenceHi() // returns upper bound of the 95% confidence
                                 // interval
    {
        return u + ((1.96d * q) / Math.sqrt(trials));
    }

    public static void main(String[] args) // test client, described below
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        new PercolationStats(n, trials);

    }

}
