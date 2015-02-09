/*
*
*  Dependencies: Percolation, StdStats, StdRandom
*/

public class PercolationStats {
    private double[] fractions;
    private int times;
    private int size;
    
    /*
        perform T independent experiments on an N-by-N grid
    */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        
        size = N;
        times = T;
        fractions = new double[times];
        for (int k = 0; k < times; k++) {
            Percolation pr = new Percolation(size);
            int calls = 0;
            while (!pr.percolates()) {
                int i = StdRandom.uniform(1, size + 1);
                int j = StdRandom.uniform(1, size + 1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    calls++;
                }
            }
            fractions[k] = (double) calls / (N * N);
        }
    }
    
    /*
        sample mean of percolation threshold
    */
    public double mean() {
        return StdStats.mean(fractions);
    }
    
    /*
        sample standard deviation of percolation threshold
    */
    public double stddev() {
        return StdStats.stddev(fractions);
    }
    
    /*
        low endpoint of 95% confidence interval
    */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }
    
    /*
        high endpoint of 95% confidence interval
    */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }
    
    /*
        test client
    */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(N, T);
        
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        
        StdOut.printf("mean                    = %s\n", ps.mean() + "");
        StdOut.printf("stddev                  = %s\n", ps.stddev() + "");
        StdOut.printf("95%% confidence interval = %s\n", confidence);
    }
}
