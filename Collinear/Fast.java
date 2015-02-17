import java.util.Arrays;


public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] pointlist = in.readAllInts();
        int count = pointlist[0];
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        
        Point[] points = new Point[count];
        Point[][] endPoints = new Point[count][2];
        int collinearCounter = 0;
        int counter = 0;
        for (int i = 1; i < pointlist.length - 1; i = i + 2) {
            Point point = new Point(pointlist[i], pointlist[i + 1]);
            points[counter] = point;
            point.draw();
            counter++;
        }
        
        //count = points.length;
        StdDraw.setPenRadius(0.005);
        
        //Point tmp = new Point(6512, 2986);
        
        for (int i = 0; i < count; i++) {
            Point p = points[i];
            Arrays.sort(points, p.SLOPE_ORDER);
                
            for (int j = 1; j < count - 2; j++) {
                Point q = points[j];
                Point r = points[j + 2];
                int k = 0;
                
                
                for (int l = 0; l < count; l++) {
                    StdOut.println("l = " + l + "; " + points[l] + "; slope = " + p.slopeTo(points[l]));
                }
                StdOut.println("=======================");

                if (p.slopeTo(q) == p.slopeTo(r)) {
                    double slope = p.slopeTo(q);
                    
                    //k = j + 3;
                    
                    Point t;
                    for (k = j + 3; k < count; k++) {
                        t = points[k];
                        if (p.slopeTo(t) != slope) {
                            break;
                        }
                    }
                    /*
                    if (k < count) {
                    
                        Point q = points[k];
                        while (p.slopeTo(q) == slope) {
                            q = points[k];
                            k++;
                            if (k == count) {
                                break;
                            }
                        }
                    }
                    */
                    Point[] collPoints = new Point[k - j + 1];
                    
                    StdOut.println("j = " + j);
                    StdOut.println("k = " + k);
        
                    
                    collPoints[0] = p;
                    int pointCounter = 1;
                    for (int l = j; l < k; l++) {
                        collPoints[pointCounter] = points[l];
                        pointCounter++;
                    }
                    
                    Arrays.sort(collPoints);
                    
                    boolean flag = false;
                    
                    for (int z = 0; z < collinearCounter; z++) {
                        if (collPoints[0] == endPoints[z][0] && 
                            collPoints[collPoints.length - 1] == endPoints[z][1]) {
                            
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
                    
                    j = --k;
                }
            }
        }
        
    }
}
