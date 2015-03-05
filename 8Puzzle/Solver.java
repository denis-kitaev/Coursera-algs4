/**********************************************************
* Dependencies: MinPQ.java (algs4.jar)
*
*
**********************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Solver {
    private Board initial;
    private int moves = 0;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        
        this.initial = initial;
        if (!isSolvable()) {
            // sad but true
        }
        
        MinPQ<Board> pq = new MinPQ(Board.MANHATTAN_ORDER);
        
        Board b = initial;
        pq.insert(initial);
        while (b.manhattan() != 0) {
            b = pq.delMin();
            pq.insert(b);
            if (b.manhattan() == 0) {
                break;
            }
            
            Iterator<Board> ns = b.neighbors();
            
            while (ns.hasNext()) {
                pq.insert(ns.next());
            }
            this.moves++;
        }
    }
    
    // is the initial board solvable?           
    public boolean isSolvable() {
        return initial.manhattan() != initial.twin().manhattan();
    }
    
    // min number of moves to solve initial board; -1 if unsolvable         
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return moves;
    }      
    
    // sequence of boards in a shortest solution; null if unsolvable         
 /*   public Iterable<Board> solution() {
        return new SolutionIterator();
    }
    
    private class SolutionIterator implements Iterator<Board> {
        public SolutionIterator() {
        
        }
        
        public boolean hasNext() {
            return true;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return initial;
        }    
    } */
    
    // solve a slider puzzle (given below)   
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            /*for (Board board : solver.solution())
                StdOut.println(board);*/
        }
    }
}
