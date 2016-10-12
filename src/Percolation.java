import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
 * Algorithms and Data Structures Assignment
 * 
 * @author David Morton
 * 
 * Percolation.java
 */
public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF weightedQuickUnionFindArray;

    private int gridSize; // number of elements in grid is gridSize squared
    private int virtualTop; // index (0) of the union find array
    private int virtualBottom; // index (UFSize - 1) of the union find array

    /*
     * Constructor for the grid takes a grid size, squares it and creates a two
     * dimensional array containing gridSize^2 number of elements. Each element
     * contains a boolean value. True means the site at the array index is open
     * and false means its closed. Initialized to false.
     * 
     * @param int n - the value used to create the grid size.
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Error: Percolation() n <= 0");
        }
        if (n == 1) {
            throw new IllegalArgumentException("Error: Percolation() n == 1");
        }
        this.gridSize = n;
        this.grid = new boolean[gridSize][gridSize];
        int unionFindSize = (gridSize * gridSize) + 2;
        this.weightedQuickUnionFindArray = new WeightedQuickUnionUF(unionFindSize);
        this.virtualTop = 0;
        this.virtualBottom = weightedQuickUnionFindArray.count() - 1;
    }

    /*
     * Open a site and check its position in the grid. Depending on its position
     * check sites adjacent to itself. If a site is found to be open connect to
     * it.
     * 
     * @param int i - the row in the percolation grid
     * 
     * @param int j - the column in the percolation grid
     */
    public void open(int i, int j) {
        if (i <= 0 || j > this.gridSize) {
            throw new IndexOutOfBoundsException("Error: open()");
        }
        this.grid[i - 1][j - 1] = true;
        if (i == 1 && j == 1) { // top left
            connectToVTop(i, j);
            checkRight(i, j);
            checkBottom(i, j);
        } else if (i == this.gridSize && j == 1) { // bottom left
            connectToVBottom(i, j);
            checkTop(i, j);
            checkRight(i, j);
        } else if (i == 1 && j == this.gridSize) { // top right
            connectToVTop(i, j);
            checkLeft(i, j);
            checkBottom(i, j);
        } else if (j == this.gridSize && i == this.gridSize) { // bottom
            connectToVBottom(i, j);
            checkTop(i, j);
            checkLeft(i, j);
        } else if (j == 1 && i != this.gridSize) { // left
            checkTop(i, j);
            checkRight(i, j);
            checkBottom(i, j);
        } else if (j == this.gridSize && i != this.gridSize) { // right
            checkTop(i, j);
            checkLeft(i, j);
            checkBottom(i, j);
        } else if (i == 1) { // top
            connectToVTop(i, j);
            checkLeft(i, j);
            checkRight(i, j);
            checkBottom(i, j);
        } else if (i == this.gridSize) { // bottom
            connectToVBottom(i, j);
            checkTop(i, j);
            checkRight(i, j);
            checkLeft(i, j);
        } else { // center
            checkTop(i, j);
            checkRight(i, j);
            checkLeft(i, j);
            checkBottom(i, j);
        }
    }

    /*
     * Methods to handle the connections between nodes on the grid. Check in all
     * four directions and union the nodes if they are open.
     * 
     * @param int row - the row index of the node
     * 
     * @param int column - the column index of the node
     */
    private void checkLeft(int row, int column) {
        if (isOpen(row, column - 1)) {
            this.weightedQuickUnionFindArray.union(convertIndex(row, column), convertIndex(row, column - 1));
        }
    }

    private void checkRight(int row, int column) {
        if (isOpen(row, column + 1)) {
            this.weightedQuickUnionFindArray.union(convertIndex(row, column), convertIndex(row, column + 1));
        }
    }

    private void checkTop(int row, int column) {
        if (isOpen(row - 1, column)) {
            this.weightedQuickUnionFindArray.union(convertIndex(row, column), convertIndex(row - 1, column));
        }
    }

    private void checkBottom(int row, int column) {
        if (isOpen(row + 1, column)) {
            this.weightedQuickUnionFindArray.union(convertIndex(row, column), convertIndex(row + 1, column));
        }
    }

    /*
     * Method to convert the two dimensional array index to a single node index
     * for the union find array
     * 
     * @param int row - the row index of the node
     * 
     * @param int column - the column index of the node
     */
    private int convertIndex(int row, int column) {
        row -= 1;
        column -= 1;
        return (row * this.gridSize) + column + 1;
    }

    /*
     * Methods to handle the connections to the virtual top node and the virtual
     * bottom node.
     * 
     * @param int row - the row index of the node
     * 
     * @param int column - the column index of the node
     */
    private void connectToVTop(int row, int column) {
        this.weightedQuickUnionFindArray.union(this.virtualTop, convertIndex(row, column));
    }

    private void connectToVBottom(int row, int column) {
        this.weightedQuickUnionFindArray.union(this.virtualBottom, convertIndex(row, column));
    }

    /*
     * Check if the array index that represents the site is true or false. True
     * means the site is open and false means it is closed.
     * 
     * @param int i - the row in the percolation grid
     * 
     * @param int j - the column in the percolation grid
     * 
     * @return boolean isOpen status
     */
    public boolean isOpen(int i, int j) {
        if (i <= 0 || j > this.gridSize) {
            throw new IndexOutOfBoundsException("Error: isOpen()");
        }
        i = i - 1;
        j = j - 1;
        return this.grid[i][j];
    }

    /*
     * Check if the site is full by checking first if its open. If its open and
     * connected to the top virtual node this means the site is full as it is
     * connected to nodes from itself all the way to the top of the grid.
     * 
     * @param int i - the row index of the node
     * 
     * @param int j - the column index of the node
     * 
     * @return boolean isFull site status
     */
    public boolean isFull(int i, int j) {
        if (i <= 0 || j > this.gridSize) {
            throw new IndexOutOfBoundsException("Error: isFull()");
        }
        // check if the site is open and connected to the top
        return (isOpen(i, j) && this.weightedQuickUnionFindArray.connected(this.virtualTop, convertIndex(i, j)) ? true
                : false);
    }

    /*
     * Check if the site percolates by checking if the top virtual node and the
     * bottom virtual node are connected.
     * 
     * @return boolean percolation status
     * 
     */
    public boolean percolates() {
        // check if the system percolates
        return (this.weightedQuickUnionFindArray.connected(this.virtualTop, this.virtualBottom)) ? true : false;
    }
}