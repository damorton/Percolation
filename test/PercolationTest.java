import org.junit.Test;

public class PercolationTest {

    @Test(expected = IllegalArgumentException.class)
    public void Constructor_NegativeN_ShouldThrowIllegalArgumentException() {
        @SuppressWarnings("unused")
        Percolation ps = new Percolation(-1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Constructor_ZeroN_ShouldThrowIllegalArgumentException() {
        @SuppressWarnings("unused")
        Percolation ps = new Percolation(0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Constructor_NEqualsOne_ShouldThrowIllegalArgumentException() {
        @SuppressWarnings("unused")
        Percolation ps = new Percolation(1);
    }

    // open()
    @Test(expected = IndexOutOfBoundsException.class)
    public void Open_ZeroArgumentI_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.open(0, 3);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void Open_ZeroArgumentJ_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.open(2, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void Open_NegativeArgumentI_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.open(-1, 3);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void Open_NegativeArgumentJ_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.open(2, -1);
    }
    
    // isOpen()
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsOpen_ZeroArgumentI_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isOpen(0, 3);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsOpen_ZeroArgumentJ_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isOpen(2, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsOpen_NegativeArgumentI_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isOpen(-1, 3);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsOpen_NegativeArgumentJ_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isOpen(2, -1);
    }
    
    @Test
    public void IsOpen_OnOpenCell_ShouldPass() {
        Percolation ps = new Percolation(200);
        ps.open(4, 5);
        ps.isOpen(4, 5);
    }
    
    @Test
    public void IsOpen_OnClosedCell_ShouldFail() {
        Percolation ps = new Percolation(200);
        ps.isOpen(4, 5);
    }
    
    // isFull()
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsFull_ZeroArgumentI_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isFull(0, 3);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsFull_ZeroArgumentJ_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isFull(2, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsFull_NegativeArgumentI_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isFull(-1, 3);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IsFull_NegativeArgumentJ_ShouldThrowIndexOutOfBoundsException() {
        Percolation ps = new Percolation(200);
        ps.isFull(2, -1);
    }
    
    @Test
    public void IsFull_OnFullCell_ShouldPass() {
        Percolation ps = new Percolation(200);
        ps.open(1, 1);
        ps.isFull(1, 1);
    }
    
    @Test
    public void IsFull_OnOpenEmptyCell_ShouldFail() {
        Percolation ps = new Percolation(200);
        ps.open(4, 5);
        ps.isFull(4, 5);
    }
}
