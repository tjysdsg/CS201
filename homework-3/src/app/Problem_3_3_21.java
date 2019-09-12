package app;

public class Problem_3_3_21 {
    public static abstract class TimeBase {
        public abstract boolean equals(TimeBase other);

        public abstract String toString();

        public abstract int hashCode();

        public abstract int getHour();

        public abstract int getMinute();

        public abstract int getSecond();

        public abstract void addHour(int h);

        public abstract void addMinute(int m);

        public abstract void addSecond(int s);
    }

    public static class TimeStamp extends TimeBase {
        public TimeStamp() {
            this.time = 0;
        }

        public TimeStamp(int time) {
            this.time = time;
        }

        public String toString() {
            return "" + this.time;
        }

        public int hashCode() {
            return this.time;
        }

        public boolean equals(TimeBase other) {
            TimeStamp t = (TimeStamp) other;
            return this.time == t.time;
        }

        public int getHour() {
            return this.time / 3600;
        }

        public int getMinute() {
            return (this.time % 3600) / 60;
        }

        public int getSecond() {
            return (this.time % 3600) % this.time % 60;
        }

        public void addHour(int h) {
            this.time += h * 3600;
        }

        public void addMinute(int m) {
            this.time += m * 60;
        }

        public void addSecond(int s) {
            this.time += s;
        }

        protected int time;
    }

    public static class Time extends TimeBase {
        public Time() {
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
        }

        public Time(int hour, int min, int sec) {
            this.hour = hour;
            this.minute = min;
            this.second = sec;
        }

        public String toString() {
            return this.hour + "h " + this.minute + "m " + this.second + "s ";
        }

        public int hashCode() {
            return this.hour * 3600 + this.minute * 60 + this.second;
        }

        public boolean equals(TimeBase other) {
            Time t = (Time) other;
            return this.hour == t.hour && this.minute == t.minute && this.second == t.second;
        }

        public int getHour() {
            return this.hour;
        }

        public int getMinute() {
            return this.minute;
        }

        public int getSecond() {
            return this.second;
        }

        public void addHour(int h) {
            this.hour += h;
        }

        public void addMinute(int m) {
            this.minute += m;
        }

        public void addSecond(int s) {
            this.second += s;
        }

        protected int hour;
        protected int minute;
        protected int second;
    }

    public static int[] testTime(TimeBase time) {
        int[] result = new int[] { 0, 3 };
        time.addHour(3);
        time.addMinute(22);
        time.addSecond(56);
        if (time.getHour() == 3) {
            ++result[0];
        }
        if (time.getMinute() == 22) {
            ++result[0];
        }
        if (time.getSecond() == 56) {
            ++result[0];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] result1 = testTime((TimeBase) new TimeStamp());
        System.err.println("Test for TimeStamp: " + result1[0] + " out of " + result1[1] + " passed.");
        int[] result2 = testTime((TimeBase) new TimeStamp());
        System.err.println("Test for Time: " + result2[0] + " out of " + result2[1] + " passed.");
    }
}
