public class Brute {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] pointlist = in.readAllInts();
        int count = pointlist[0];
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        
        Point[] points = new Point[count];
        int counter = 0;
        for (int i = 1; i < pointlist.length - 1; i = i + 2) {
            Point point = new Point(pointlist[i], pointlist[i + 1]);
            points[counter] = point;
            point.draw();
            counter++;
        }
        
        StdDraw.setPenRadius(0.005);
        
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (i != j && points[i].compareTo(points[j]) <= 0) {
                    for (int k = 0; k < count; k++) {
                        if (i != k && j != k && points[j].compareTo(points[k]) <= 0
                            && points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                            for (int l = 0; l < count; l++) {
                                if (i != l && j != l && k != l && points[k].compareTo(points[l]) <= 0
                                    && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {

                                    StdOut.println(points[i] + " -> "
                                                 + points[j] + " -> "
                                                 + points[k] + " -> " + points[l]);
                                                 
                                    points[i].drawTo(points[l]);             
                                    
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
