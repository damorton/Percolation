import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*
 * Algorithms and Data Structures Assignment
 * 
 * @author David Morton
 * 
 * PercolationStats.java
 */
public class PercolationStats {

    private double[] cycleResults;
    private double numberOfCycles;
    private int numberOfOpenSites;
    private Percolation perc;

    /*
     * Constructor that takes two values <N> the grid size to use in the
     * experiment and <T> the number of experiments to run.
     * 
     * @param int N - the grid size
     * 
     * @param int T - the number of experiments to run
     */
    public PercolationStats(int n, int trials) {
        this.cycleResults = new double[trials];

        for (int i = 0; i < trials; i++) {
            this.perc = new Percolation(n);
            this.numberOfOpenSites = 0;

            while (!perc.percolates()) {
                int randRow = StdRandom.uniform(1, n + 1);
                int randCol = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(randRow, randCol)) {
                    perc.open(randRow, randCol);
                    this.numberOfOpenSites++;
                }
            }
            this.cycleResults[i] = this.numberOfOpenSites;
            this.numberOfCycles = trials;
        }

    }

    /*
     * Calculate the mean/average of the whole percolation operation.
     * 
     * @return double mean of the percolation cycles
     */
    public double mean() {
        // return this.sumOfOpenSites / this.numberOfCycles;
        return StdStats.mean(this.cycleResults);
    }

    /*
     * Calculates the standard deviation for the values stored from each of the
     * experiments.
     * 
     * @return double the standard deviation from the mean
     */
    public double stddev() {
        /*
         * double[] varience = new double[(int) this.numberOfCycles]; double
         * sumOfVarience = 0.0;
         * 
         * for (int i = 0; i < this.numberOfCycles; i++) { varience[i] =
         * Math.pow((this.cycleResults[i] - mean()), 2); sumOfVarience +=
         * varience[i]; } return Math.sqrt(sumOfVarience / this.numberOfCycles);
         */
        return StdStats.stddev(this.cycleResults);
    }

    /*
     * Calculates the confidence Lo value
     * 
     * @return double confidenceLo value
     */
    public double confidenceLo() {
        return StdStats.mean(this.cycleResults) - marginOfError();
    }

    /*
     * Calculates the confidence Hi value
     * 
     * @return double confidenceHi value
     */
    public double confidenceHi() {
        return StdStats.mean(this.cycleResults) + marginOfError();
    }

    /*
     * Calculating the margin of error for confidence interval of 95% has a
     * critical value of 1.96 from .475(.95 / 2) on the tables.
     * 
     * @return double margin of error
     */
    private double marginOfError() {
        return (1.96 * stddev()) / Math.sqrt(this.numberOfCycles);
    }

    /*
     * This program runs percolation experiments based on values input by the
     * user and prints the experiments statistics.
     */
    public static void main(String[] args) {

        // N is the grid size
        int n = Integer.parseInt(args[0]);

        // T is the number of experiments to run
        int t = Integer.parseInt(args[1]);

        // run the experiment
        PercolationStats ps = new PercolationStats(n, t);

        System.out.printf("mean\t\t\t= %f\n", ps.mean());
        System.out.printf("stddev\t\t\t= %.3f\n", ps.stddev());
        // System.out.printf("Margin of error: %.3f\n", ps.marginOfError());
        System.out.printf("95%% confidence interval\t= %f, %f\n", ps.confidenceLo(), ps.confidenceHi());

    }
}