import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;


public class Board {
    public static final Comparator<Board> MANHATTAN_ORDER = new ManhattanOrder();
    
    private final int len;
    private int[][] blocks;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        len = blocks[0].length;
        if (len < 2 || len >= 128) {
            // length not valid
        }
        
        this.blocks = new int[len][len];
        
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }      
    
    private static class ManhattanOrder implements Comparator<Board> {
        public int compare(Board v, Board w) {
            if (v.manhattan() + v.hamming() > w.manhattan() + w.hamming()) {
                return 1;
            } else if (v.manhattan() + v.hamming() < w.manhattan() + w.hamming()) {
                return -1;
            } else {
                return 0;
            }    
        }
    }     
    
    // board dimension N                                       
    public int dimension() {
        return this.len;
    }
    
    // number of blocks out of place                 
    public int hamming() {
        int out = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (blocks[i][j] != (i * len + j + 1) && blocks[i][j] != 0) {
                    out++;
                }
            }
        }
        
        return out;
    }
    
    // sum of Manhattan distances between blocks and goal                  
    public int manhattan() {
        int distance = 0;
        
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (blocks[i][j] != (i * len + j + 1) && blocks[i][j] != 0) {
                    distance += this.distance(i, j, blocks[i][j]);
                }
            }
        }
        
        return distance;
    }
    
    private int distance(int i, int j, int val) {
        int originI = val / len;
        int originJ = val % len - 1;
        if (val % len == 0) {
            originI = val / len - 1;
            originJ = len - 1;
        }
        
        return abs(originI - i) + abs(originJ - j);
    }
    
    private int abs(int a) {
        if (a < 0)  return -a;
        else        return a;
    }
    
    // is this board the goal board?                 
    public boolean isGoal() {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - 1; j++) {
                if (blocks[i][j] != i * len + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int blankRow = 0; 
        int blankColl = 0;
        int[][] twinBlocks = new int[len][len];
        
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                twinBlocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    blankRow = i;
                    blankColl = j;
                }
            }
        }
        
        if (blankColl != 0) {
            exch(twinBlocks, blankRow, blankColl, blankRow, blankColl - 1);
        } else {
            exch(twinBlocks, blankRow, blankColl, blankRow, blankColl + 1);
        }
               
        Board twin = new Board(twinBlocks);
        return twin;
        
    }
    
    // does this board equal y?                    
    public boolean equals(Object y) {
        if (y == this) return true;
        
        if (y == null) return false;
        
        if (y.getClass() != this.getClass())
            return false;
        
        Board that = (Board) y;
        boolean eq = (this.hamming() == that.hamming() && this.manhattan() == that.manhattan());   
        return eq;
    }
    
    // all neighboring boards        
    public Iterator<Board> neighbors() {
        return new NeighborsIterator();
    }
    
    private class NeighborsIterator implements Iterator<Board>{
        private int blankRow, blankColl;  // i, j
        private int pointer; // clockwise pointer
                             //      1
                             //    4 0 2
                             //      3
        
        public NeighborsIterator() {
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (blocks[i][j] == 0) {
                        blankRow = i;
                        blankColl = j;
                    }
                }
            }
            pointer = 1;
        }
        
        public boolean hasNext() {
            return pointer != 5;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            int[][] neighborBlocks = new int[len][len];
            
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    neighborBlocks[i][j] = blocks[i][j];
                }
            }

            if (pointer == 1) {
                if (blankRow != 0) {
                    exchTop(neighborBlocks);
                    pointer = 2;
                } else if (blankColl != len - 1) {
                    exchRight(neighborBlocks);
                    pointer = 3;
                } else {
                    exchBottom(neighborBlocks);
                    pointer = 4;
                }
            } else if (pointer == 2) {
                if (blankColl != len - 1) {
                    exchRight(neighborBlocks);
                    pointer = 3;
                } else if (blankRow != len - 1) {
                    exchBottom(neighborBlocks);
                    pointer = 4;
                } else {
                    exchLeft(neighborBlocks);
                    pointer = 5;
                }
            } else if (pointer == 3) {
                if (blankRow != len - 1) {
                    exchBottom(neighborBlocks);
                    if (blankColl != 0) {
                        pointer = 4;
                    } else {
                        pointer = 5;
                    }
                } else if (blankColl != 0) {
                    exchLeft(neighborBlocks);
                    pointer = 5;
                } else {
                    pointer = 5;
                }
            } else if (pointer == 4) {
                if (blankColl != 0) {
                    exchLeft(neighborBlocks);
                    pointer = 5;
                } else {
                    pointer = 5;
                }
            } 
            
            Board board = new Board(neighborBlocks);
            return board;
        }
  
        private void exchTop(int[][] a) {
            exch(a, blankRow, blankColl, blankRow - 1, blankColl);
        }
        
        private void exchRight(int[][] a) {
            exch(a, blankRow, blankColl, blankRow, blankColl + 1);
        }
        
        private void exchBottom(int[][] a) {
            exch(a, blankRow, blankColl, blankRow + 1, blankColl);
        }
        
        private void exchLeft(int[][] a) {
            exch(a, blankRow, blankColl, blankRow, blankColl - 1);
        }
    }    
        
    // string representation of this board (in the output format specified below)    
    public String toString() {
        String s = "";
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                s += blocks[i][j];
                if (j < len - 1) {
                    s += " ";
                }
            }
            s += "\n";
        }
        return s;
    }
    
    private void exch(int[][] a, int i1, int j1, int i2, int j2) {
        int tmp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = tmp;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
                
        Board initial = new Board(blocks);
        
        StdOut.println(initial.toString());
        
        StdOut.println("hamming = " + initial.hamming());
        StdOut.println("manhattan = " + initial.manhattan());
        StdOut.println("===================================");
        Iterator<Board> ns = initial.neighbors();
        
        while (ns.hasNext()) {
            StdOut.println("Board neighbor\n" + ns.next().toString());
        }
        
        StdOut.println("Twin\n" + initial.twin().toString());

    }
}
