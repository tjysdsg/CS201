package app;

public class Problem_3_3_19 {
    public static abstract class StatisticsBase {
        public abstract void append(double n);

        public abstract int count();

        public abstract double mean();

        public abstract double std();

        public abstract double var();

    }

    public static class Statistics1 extends StatisticsBase {
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

    public static class Statistics2 extends StatisticsBase {
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

    public static void main(String[] args) {
        Statistics1 stats1 = new Statistics1();
        Statistics2 stats2 = new Statistics2(1000);
        for (int i = 1; i < 1001; ++i) {
            stats1.append(i);
            stats2.append(i);
        }
        System.out.println("First version of Statistics");
        System.out.println("Count of [1, 1000]: " + stats1.count());
        System.out.println("Mean of [1, 1000]: " + stats1.mean());
        System.out.println("Standard deviation of [1, 1000]: " + stats1.std());
        System.out.println("Variance of [1, 1000]: " + stats1.var());

        System.out.println("===========================");
        System.out.println("Second version of Statistics");
        System.out.println("Count of [1, 1000]: " + stats2.count());
        System.out.println("Mean of [1, 1000]: " + stats2.mean());
        System.out.println("Standard deviation of [1, 1000]: " + stats2.std());
        System.out.println("Variance of [1, 1000]: " + stats2.var());
    }
}
