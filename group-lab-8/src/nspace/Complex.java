package nspace;

public interface Complex {
	public double re();
	public double im();
	public double r();
	public double th();

	public Complex plus(Complex b);
	public Complex minus(Complex b);
	public Complex times(Complex b);
	public double abs();
}