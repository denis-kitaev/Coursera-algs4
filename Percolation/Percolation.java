/***************************************************
* Dependencies: WeightedQuickUnionUF
***************************************************/

public class Percolation {
    private boolean[][] site;
    private WeightedQuickUnionUF fullUF;
    private WeightedQuickUnionUF backwashUF;
    private int size;

    /*
        create N-by-N grid, with all sites blocked
    */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        
        size = N;  
        site = new boolean[size][size];
        backwashUF = new WeightedQuickUnionUF(size * size + 2);
        fullUF = new WeightedQuickUnionUF(size * size + 1);
    }
    
    // validate that p is valid index
    private void validate(int p) {
        if (p < 1 || p > size)
            throw new IndexOutOfBoundsException();
    }
    
    private int getUFIndex(int i, int j) {
        return size * (i - 1) + j;
    }
    
    /*
        open site (row i, column j) if it is not open already
    */
    public void open(int i, int j) {
        if (isOpen(i, j)) return;
        
        site[i - 1][j - 1] = true;
        
        // top site
        if (i > 1 && isOpen(i - 1, j)) {
            backwashUF.union(getUFIndex(i, j), getUFIndex(i - 1, j));
            fullUF.union(getUFIndex(i, j), getUFIndex(i - 1, j));
        }
        
        // bottom site
        if (i < size && isOpen(i + 1, j)) {
            backwashUF.union(getUFIndex(i, j), getUFIndex(i + 1, j));
            fullUF.union(getUFIndex(i, j), getUFIndex(i + 1, j));
        }
        
        // left site
        if (j > 1 && isOpen(i, j - 1)) {
            backwashUF.union(getUFIndex(i, j), getUFIndex(i, j - 1));
            fullUF.union(getUFIndex(i, j), getUFIndex(i, j - 1));
        }
        
        // right site
        if (j < size && isOpen(i, j + 1)) {
            backwashUF.union(getUFIndex(i, j), getUFIndex(i, j + 1));
            fullUF.union(getUFIndex(i, j), getUFIndex(i, j + 1));
        }
        
        
        // union with top virtual site
        if (i == 1) {
            backwashUF.union(0, getUFIndex(i, j));
            fullUF.union(0, getUFIndex(i, j));
        }
        
        // union with bottom virtual site
        if (i == size) {
            backwashUF.union(size * size + 1, getUFIndex(i, j));
        }
       
    }
    
    /*
        is site (row i, column j) open?
    */
    public boolean isOpen(int i, int j) {
        validate(i);
        validate(j);
        return site[i - 1][j - 1];
    }
    
    /*
        is site (row i, column j) full?
    */
    public boolean isFull(int i, int j) {
        return isOpen(i, j) && fullUF.connected(0, getUFIndex(i, j));
    }
    
    /*
        does the system percolate?
    */
    public boolean percolates() {   
        return backwashUF.connected(0, size * size + 1);
    }
}
