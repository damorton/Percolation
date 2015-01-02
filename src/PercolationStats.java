/*
 * This program executes percolation experiments and prints the results
 * based on inputs <N> the grid size used in the experiment
 * and <T> the number of experiments to run. It calculates the mean of the
 * experiments, the standard deviation from the mean, the 
 * confidence HI value, the confidence LO value and the margin of error.
 * 
 * 
 */
public class PercolationStats{

	private int[] cycleResults;
	private double sumOfOpenSites;
	private double numberOfCycles;
	private int numberOfOpenSites;
	private Percolation perc;
	
	/*
	 * Constructor that takes two values <N> the grid size to use
	 * in the experiment and <T> the number of experiments to run.
	 * 
	 * @param int N - the grid size
	 * @param int T - the number of experiments to run
	 */
	public PercolationStats( int N, int T){
		this.cycleResults = new int[T];
		
		for(int i = 0; i < T; i++){
			this.perc = new Percolation(N);			
			while(!perc.percolates()){				
				int randRow = (int)(Math.random() * (N-0));
				int randCol = (int)(Math.random() * (N-0));				
				if(!perc.isOpen(randRow, randCol)){
	            	perc.open(randRow, randCol);
	            	this.numberOfOpenSites++;
	            }				
			}
			this.cycleResults[i] = this.numberOfOpenSites;
			this.sumOfOpenSites += this.numberOfOpenSites;
			this.numberOfCycles = T;
			this.numberOfOpenSites = 0;
			//System.out.printf("\nExperiment %d\n%s", i, this.perc);
		}
		
	}
	
	/*
	 * Calculate the mean/average of the whole 
	 * percolation operation.
	 * 
	 * @return double mean of the percolation cycles
	 */
	public double mean(){	
		return this.sumOfOpenSites / this.numberOfCycles;
	}
	
	/*
	 * Calculates the standard deviation for the values stored
	 * from each of the experiments.
	 * 
	 * @return double the standard deviation from the mean
	 */
	public double stddev(){
		double[] varience = new double[(int)this.numberOfCycles];
		double sumOfVarience = 0.0;
				
		for(int i = 0; i < this.numberOfCycles;i++){
			varience[i] = Math.pow((this.cycleResults[i] - mean()), 2);
			sumOfVarience += varience[i];
		}
		return Math.sqrt(sumOfVarience / this.numberOfCycles);				
	}
	
	/*
	 * Calculates the confidence Lo value
	 * 
	 * @return double confidenceLo value
	 */
	public double confidenceLo(){
		return mean() - marginOfError();
	}
	
	/*
	 * Calculates the confidence Hi value
	 * 
	 * @return double confidenceHi value
	 */
	public double confidenceHi(){
		return mean() + marginOfError();
	}
	
	/*
	 * Calculating the margin of error 
	 * for confidence interval of 95% has
	 * a critical value of 1.96 from .475(.95 / 2) on 
	 * the tables.
	 * 
	 * @return double margin of error
	 */
	private double marginOfError(){
		return stddev() / this.numberOfCycles * 1.96; 
	}
	
	/*
	 * This program runs percolation experiments based on
	 * values input by the user and prints the experiments
	 * statistics. 
	 */
	public static void main(String [] args){
		
		// check if there are two arguments input when executing
		if (args.length != 2){
			System.out.println("Usage: java PercolationStats <N> <T> \n");
			System.out.println("where N is the size of the grid to use");
			System.out.println("and T is the number of experiments to run");
			System.exit(1);
		}
		
		// N is the grid size
		int N = Integer.parseInt(args[0]);
		
		// T is the number of experiments to run
		int T = Integer.parseInt(args[1]);

		// check if N and T are valid inputs
		if(N > 1 && T > 0){
			
			// run the experiment
			PercolationStats ps = new PercolationStats(N, T);
			
			System.out.printf("Mean: %.3f open sites\n", ps.mean());
			System.out.printf("Std Dev: %.3f\n", ps.stddev());
			System.out.printf("Margin of error: %.3f\n", ps.marginOfError());
			System.out.printf("95%% confidence interval: %.3f, %.3f\n",ps.confidenceLo() ,ps.confidenceHi());
			
		}else{
			System.out.printf("Please enter valid arguments for N & T. Values greater than 1");
		}
	}
}