
/****************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java StdDraw.java In.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size N of the percolation system.
 *    - Creates an N-by-N grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ****************************************************************************/

import java.awt.Font;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationVisualizer {

    // draw N-by-N percolation system
    public static void draw(Percolation perc, int N, int numberOfOpenSites) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);

        // draw N-by-N grid
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (perc.isFull(row, col)) {

                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                } else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                } else
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(.25 * N, -N * .025, numberOfOpenSites + " open sites");
        if (perc.percolates())
            StdDraw.text(.75 * N, -N * .025, "percolates");
        else
            StdDraw.text(.75 * N, -N * .025, "does not percolate");

    }

    /*
     * This program creates an experiment that uses the users input for grid
     * size and number of experiments. The co ordinates for each site to be
     * opened are randomly generated.
     * 
     * int N - the grid size int M - the number of experiments
     */
    public static void main(String[] args) {

        boolean fromFile = true;

        if (!fromFile) {
            int numberOfOpenSites = 0;

            if (args.length != 2) {
                System.out.println("Usage: java PercolationVisualizer <N> <T>");
                System.out.println("where N is the size of the grid to use");
                System.out.println("and T is the number of experiments to run");
                System.exit(1);
            }
            int N = Integer.parseInt(args[0]); // grid size
            int M = Integer.parseInt(args[1]); // number of experiments

            for (int i = 0; i < M; i++) {
                Percolation perc = new Percolation(N);
                draw(perc, N, numberOfOpenSites);
                while (!perc.percolates()) {
                    int randRow = StdRandom.uniform(N);
                    int randCol = StdRandom.uniform(N);
                    if (!perc.isOpen(randRow, randCol)) {
                        perc.open(randRow, randCol);
                        numberOfOpenSites++;
                    }
                    draw(perc, N, numberOfOpenSites);
                }
                numberOfOpenSites = 0;
            }

        } else {

            /*
             * Reading the site co-ordinates from a file
             */

            In in = new In(args[0]); // input file
            int N = in.readInt(); // N-by-N percolation system

            int numberOfOpenSites = 0;

            // repeatedly read in sites to open and draw resulting system
            Percolation perc = new Percolation(N);
            draw(perc, N, numberOfOpenSites);

            while (!in.isEmpty()) {
                int i = in.readInt(); // row
                int j = in.readInt(); // column

                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    numberOfOpenSites++;
                }
                draw(perc, N, numberOfOpenSites);
            }

        }
    }

}