package app;

public class Problem_3_3_19 {
    interface Statistics {
        public void append(double n);

        public int count();

        public double mean();

        public double std();

        public double var();

    }

    public static class Statistics1 implements Statistics {
        Statistics1() {
            this.count = 0;
            this.sum = 0;
            this.sumSquare = 0;

        }

        public void append(double n) {
            ++this.count;
            this.sum += n;
            this.sumSquare += n * n;
        }

        public int count() {
            return this.count;
        }

        public double mean() {
            return this.sum / this.count;
        }

        public double std() {
            return Math.sqrt(this.sumSquare / (count - 1));
        }

        public double var() {
            return this.sumSquare / count;
        }

        protected int count;
        protected double sum;
        protected double sumSquare;
    }

    public static class Statistics2 implements Statistics {
        Statistics2(int maxCount) {
            data = new double[maxCount];
            back = -1;
        }

        public void append(double n) {
            this.data[++this.back] = n;
        }

        public int count() {
            return this.back + 1;
        }

        public double mean() {
            return this.sum() / (this.back + 1);
        }

        public double std() {
            return Math.sqrt(this.sumSquare() / this.back);
        }

        public double var() {
            return this.sumSquare() / (this.back + 1);
        }

        protected double sum() {
            double result = 0;
            for (double d : this.data) {
                result += d;
            }
            return result;
        }

        protected double sumSquare() {
            double result = 0;
            for (double d : this.data) {
                result += d * d;
            }
            return result;
        }

        protected double[] data;
        // index of the last element
        protected int back;
    }

    private static void testStatistics(Statistics s) {
        s.append(1);
        s.append(2);
        s.append(3);
        System.out.println("Mean: " + s.mean());
        System.out.println("Standard Deviation: " + s.std());
        System.out.println("Variance: " + s.var());

    }

    public static void main(String[] args) {
        Statistics1 stats1 = new Statistics1();
        Statistics2 stats2 = new Statistics2(3);
        testStatistics(stats1);
        testStatistics(stats2);
    }
}
