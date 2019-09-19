package nspace;

public class PolarComplex implements Complex {
	public double r;
	public double th;

	public PolarComplex(double r, double th) {
		this.r = r;
		this.th = th;
	}

	public double re() {
		return r * Math.cos(th);
	}

	public double im() {
		return r * Math.sin(th);
	}

	public double r() {
		return r;
	}

	public double th() {
		return th;
	}

	// return a new Complex object whose value is (this + b)
	public Complex plus(Complex b) {
		double real = this.re() + b.re();
		double imag = this.im() + b.im();
		return new StandardComplex(real, imag);
	}

	// return a new Complex object whose value is (this - b)
	public Complex minus(Complex b) {
		Complex a = this;
		double real = a.re() - b.re();
		double imag = a.im() - b.im();
		return new StandardComplex(real, imag);
	}

	// return a new Complex object whose value is (this * b)
	public Complex times(Complex b) {
		return new PolarComplex(this.r() * b.r(), this.th() + b.th());
	}

	public double abs() {
		return Math.abs(r);
	}

	public String toString() {
		double im = this.im();
		double re = this.re();
		if (im == 0) return re + "";
		if (re == 0) return im + "i";
		if (im <  0) return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}
}