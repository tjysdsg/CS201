public class Stopwatch {
	private final long start;

	public Stopwatch() {
		start = System.nanoTime();
	}

	public double elapsedTime() {
		long now = System.nanoTime();
		return (now - start);
	}
}