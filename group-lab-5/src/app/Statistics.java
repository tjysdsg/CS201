package app;

public class Statistics {
	public static void main(String[] args) {
		int column = Integer.parseInt(args[0]);
		In file = args.length > 1 ? new In(args[1]) : new In();
		int count = 0;
		double sum = 0.0;
		while (file.hasNextLine()) {
			String s = file.readLine();
			String[] data = s.split(",");
			++count;
			sum += Double.parseDouble(data[column]);
		}
		System.out.println("Average: " + sum / count);
	}
}