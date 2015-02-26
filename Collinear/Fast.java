import java.util.Arrays;


public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int count = in.readInt();
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        
        Point[] points = new Point[count];
        Point[] originPoints = new Point[count];
        
        Point[][] endPoints = new Point[count * 2][2];
        int collinearCounter = 0;

        for (int i = 0; i < count; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            originPoints[i] = new Point(x, y);
            points[i].draw();
        }
        
        if (count < 3) return;
        
        StdDraw.setPenRadius(0.005);

        for (int i = 0; i < count; i++) {
            Arrays.sort(points, originPoints[i].SLOPE_ORDER);
            Point p = points[0];
            int j;
            for (j = 0; j < count - 2; j++) {
                Point q = points[j];
                Point r = points[j + 2];
                int k;
                
                if (p.slopeTo(q) == p.slopeTo(r)) {
                    double slope = p.slopeTo(q);
                    
                    Point t;
                    for (k = j + 3; k < count; k++) {
                        t = points[k];
                        if (p.slopeTo(t) != slope) {
                            break;
                        }
                    }
        
                    Point[] collPoints = new Point[k - j + 1];
                    collPoints[0] = p;
                    int pointCounter = 1;
                    for (int l = j; l < k; l++) {
                        collPoints[pointCounter] = points[l];
                        pointCounter++;
                    }
                    
                    Arrays.sort(collPoints);
                    
                    boolean flag = false;

                    for (int z = 0; z < collinearCounter; z++) {
                        if (collPoints[0] == endPoints[z][0] 
                            && collPoints[collPoints.length - 1] == endPoints[z][1]) {
                            
                            flag = true;
                            break;
                        }
                    }
                    
                    if (!flag) {
                        endPoints[collinearCounter][0] = collPoints[0];
                        endPoints[collinearCounter][1] = collPoints[collPoints.length - 1];
                        collinearCounter++;
                        
                        String out = collPoints[0].toString();
                        for (int l = 1; l < collPoints.length; l++) {
                            out += " -> " + collPoints[l];
                        }
                        
                        StdOut.println(out);
                        collPoints[0].drawTo(collPoints[collPoints.length - 1]);
                    }
                    
                    j = k;
                }
            }
        }
        
    }
}
