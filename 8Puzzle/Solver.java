/**********************************************************
* Dependencies: MinPQ.java (algs4.jar)
*
*
**********************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;


public class Solver {
    private Board initial;
    private int moves = 0;
    
    private final Comparator<SearchNode> MANHATTAN_ORDER = new ManhattanOrder();
    private final Comparator<SearchNode> HAMMING_ORDER = new HammingOrder();
    private final Comparator<SearchNode> PRIORITY_ORDER = new PriorityOrder();
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        
        this.initial = initial;
        if (!isSolvable()) {
            // sad but true
        }
        
        MinPQ<SearchNode> pq = new MinPQ(this.PRIORITY_ORDER);
        MinPQ<SearchNode> pqTwin = new MinPQ(this.PRIORITY_ORDER);
        
        SearchNode sn = new SearchNode(initial, 0, null);
        SearchNode snTwin = new SearchNode(initial.twin(), 0, null);
        
        pq.insert(sn);
        pqTwin.insert(snTwin);
        
        Board b = pq.delMin().board;
        while (!b.isGoal()) {
            Iterator<Board> ns = b.neighbors();
            
            while (ns.hasNext()) {
                sn = new SearchNode(ns.next(), moves, sn);
                pq.insert(sn);
            }
            
            b = pq.delMin().board;
            StdOut.println(b);
            moves++;
        }
    }
    
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode prev;
        
        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            moves = m;
            prev = p;  
        }
    }
    
    private class ManhattanOrder implements Comparator<SearchNode> {
        public int compare(SearchNode v, SearchNode w) {
            return v.board.manhattan() - w.board.manhattan();
        }
    }
    
    private class HammingOrder implements Comparator<SearchNode> {
        public int compare(SearchNode v, SearchNode w) {
            return v.board.hamming() - w.board.hamming();
        }
    }
    
    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode v, SearchNode w) {
            int pv = v.board.manhattan() + v.moves;
            int pw = w.board.manhattan() + w.moves;
            
            if (pv == pw) {
                return v.board.manhattan() - w.board.manhattan();
            }
            return pv - pw;
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
    public Iterator<Board> solution() {
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
    } 
    
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
