package app;

public class Problem_3_2_11 {
    public static class Point {
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double distanceTo(Point q) {
            return Math.sqrt(Math.pow(this.x - q.x, 2) + Math.pow(this.y - q.y, 2));
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }

        protected double x;
        protected double y;
    }

    public static void main(String[] args) {
        Point x = new Point(1, 2);
        Point y = new Point(3, 4);
        System.out.println("Distance from " + x + " to " + y + " is " + x.distanceTo(y));
    }

}
