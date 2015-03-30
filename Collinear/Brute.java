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
                int cmpIJ = points[i].compareTo(points[j]);
                if (i != j && cmpIJ <= 0) {
                    for (int k = 0; k < count; k++) {
                        int cmpJK = points[j].compareTo(points[k]);
                        double slopeIJ = points[i].slopeTo(points[j]);
                        double slopeJK = points[j].slopeTo(points[k]);
                        if (i != k && j != k && cmpJK <= 0 && slopeIJ == slopeJK) {
                            for (int l = 0; l < count; l++) {
                                int cmpKL = points[k].compareTo(points[l]);
                                double slopeKL = points[k].slopeTo(points[l]);
                                if (i != l && j != l && k != l && cmpKL <= 0 && slopeJK == slopeKL) {
                                    String out = points[i] + " -> " + points[j] + " -> ";
                                    out += points[k] + " -> " + points[l];
                                    
                                    StdOut.println(out);             
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
