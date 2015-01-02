/*
 *  Database Structures assignment. Due 7th March.
 *  
 *  The Percolation class creates a grid of size specified by the users input 
 *  and randomly, or from inputs in  a file, opens sites within the grid until the grid percolates. 
 *  The grid will percolate provided that there is a link between any of the 
 *  top sites in the grid to any of the bottom sites of the grid, The grid can be 
 *  thought of like a side view of a wall. Water is poured onto the wall form the top
 *  and as each site is opened, a brick is removed. When the water flows from the top
 *  to the bottom, percolation occurs.
 * 
 *    
 */
public class Percolation{
	
	private boolean[][] grid;
	private WeightedQuickUnionUF unionFindArray; 
	
	private int gridSize; 			// number of elements in grid is gridSize squared
	private int UFSize;				// union find array size
	private int virtualTop; 		// index (0) of the union find array
	private int virtualBottom;		// index (UFSize - 1) of the union find array
		
	/*
	 * Constructor for the grid takes a grid size, squares it
	 * and creates a two dimensional array containing gridSize^2 
	 * number of elements. Each element contains a boolean
	 * value. True means the site at the array index
	 * is open and false means its closed. Initialized to false.
	 * 
	 * @param int gridSize - the value used to create the grid size. 
	 */
	public Percolation(int gridSize){
		
		this.gridSize = gridSize;
		
		// create the grid and count the number of sites
		this.grid = new boolean[gridSize][gridSize];
		this.UFSize = 0;
		for(int row = 0; row < gridSize; row++){
			for(int column = 0; column < gridSize; column++){
				this.grid[row][column] = false;				
				this.UFSize++;				
			}
		}		
			 
		this.UFSize += 2; // add two nodes for virtual top and bottom
		this.unionFindArray = new WeightedQuickUnionUF(UFSize); // create union find array of nodes
		
		this.virtualTop = 0; // virtual top of the grid
		this.virtualBottom = this.UFSize - 1; // virtual bottom of the grid
		
	}
	
	/*
	 * Open a site and check its position in the grid. 
	 * Depending on its position check sites adjacent to itself. 
	 * If a site is found to be open connect to it. 
	 * 
	 * @param int row - the row in the percolation grid
	 * @param int column - the column in the percolation grid
	 */
	public void open(int row, int column){
		this.grid[row][column] = true;				
			
		if(row == 0 && column == 0){												// top left
			connectToVTop(row, column);
			checkRight(row, column);
			checkBottom(row, column);			
		}else if(row == this.gridSize - 1 && column == 0){							// bottom left
			connectToVBottom(row, column); 
			checkTop(row, column);			
			checkRight(row, column);			
		}else if(row == 0 && column == this.gridSize - 1){							// top right
			connectToVTop(row, column);			 		
			checkLeft(row, column);			
			checkBottom(row, column);						
		}else if(column == this.gridSize - 1 && row == this.gridSize - 1){ 			// bottom right
			connectToVBottom(row, column); 
			checkTop(row, column);			
			checkLeft(row, column);			
		}else if(column == 0 && row != this.gridSize - 1){ 							// left
			checkTop(row, column);			
			checkRight(row, column);
			checkBottom(row, column);				
		}else if(column == this.gridSize - 1 && row != this.gridSize - 1){			// right
			checkTop(row, column);			
			checkLeft(row, column);			
			checkBottom(row, column);			
		}else if(row == 0){															// top	
			connectToVTop(row, column);
			checkLeft(row, column);			
			checkRight(row, column);
			checkBottom(row, column);						
		}else if(row == this.gridSize - 1){											// bottom
			connectToVBottom(row, column);
			checkTop(row, column);			
			checkRight(row, column);
			checkLeft(row, column);			
		}else{																		// center
			checkTop(row, column);			
			checkRight(row, column);
			checkLeft(row, column);			
			checkBottom(row, column);			
		}				
	}
	
	/*
	 * Methods to handle the connections between nodes
	 * on the grid. Check in all four directions and union the nodes 
	 * if they are open.
	 * 
	 * @param int row - the row index of the node
	 * @param int column - the column index of the node
	 */
	private void checkLeft(int row, int column){
		if(isOpen(row, column - 1)){		
			this.unionFindArray.union(convertIndex(row, column), (row * this.gridSize) + column);
		}
	}	
	
	private void checkRight(int row, int column){
		if(isOpen(row, column + 1)){		
			this.unionFindArray.union(convertIndex(row, column), (row * this.gridSize) + column + 2);
		}
	}	
	
	private void checkTop(int row, int column){
		if(isOpen(row - 1, column)){		
			this.unionFindArray.union(convertIndex(row, column), ((row - 1) * this.gridSize) + column + 1);
		}
	}
	
	private void checkBottom(int row, int column){
		if(isOpen(row + 1, column)){		
			this.unionFindArray.union(convertIndex(row, column), ((row + 1) * this.gridSize) + column + 1);
		}
	}
	
	/*
	 * Method to convert the two dimensional array index
	 * to a single node index for the union find array
	 * 
	 * @param int row - the row index of the node
	 * @param int column - the column index of the node
	 */
	private int convertIndex(int row, int column){
		return (row * this.gridSize) + column + 1;
	}
	
	/*
	 * Methods to handle the connections to the virtual top node 
	 * and the virtual bottom node.
	 * 
	 * @param int row - the row index of the node
	 * @param int column - the column index of the node
	 */
	private void connectToVTop(int row, int column){
		this.unionFindArray.union(this.virtualTop, convertIndex(row, column));
	}
	private void connectToVBottom(int row, int column){
		this.unionFindArray.union(this.virtualBottom, convertIndex(row, column));
	}
	 
	
	/*
	 * Check if the array index that represents the site is 
	 * true or false. True means the site is open and false 
	 * means it is closed. 
	 * 
	 * @param int row - the row in the percolation grid
	 * @param int column - the column in the percolation grid
	 * 
	 * @return boolean isOpen status
	 */
	public boolean isOpen(int row, int column){				
		return this.grid[row][column];		
	}
	
	/*
	 * Check if the site is full by checking first if its open.
	 * If its open and connected to the top virtual node this means
	 * the site is full as it is connected to nodes from itself 
	 * all the way to the top of the grid.
	 * 
	 * @param int row - the row index of the node 
	 * @param int column - the column index of the node
	 * 
	 * @return boolean isFull site status
	 */
	public boolean isFull(int row, int column){		
		// check if the site is open and connected to the top
		return (isOpen(row, column) &&
				this.unionFindArray.connected(0, convertIndex(row, column))? true : false);		
	}
	
	/*
	 * Check if the site percolates by checking if
	 * the top virtual node and the bottom virtual node are 
	 * connected.
	 * 
	 * @return boolean percolation status
	 * 
	 */
	public boolean percolates(){
		// check if the system percolates		
		return (this.unionFindArray.connected(0, this.UFSize - 1))? true : false;		
	}
	
	public String toString(){
		String information = "";
		// check the sites connection status
		for(int i = 0; i < this.gridSize; i++){
			for(int j = 0; j < this.gridSize; j++){
				information += "Node " + convertIndex(i, j) + ((isOpen(i, j))? " open" : " closed") + " and " + (isFull(i, j)? "full" : "empty") + "\n";
			}
		}
		return information;
	}
}