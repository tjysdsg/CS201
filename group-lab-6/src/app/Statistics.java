package app;

public interface Statistics {

    public void append(double n);

    public int count();

    public double mean();

    public double std();

    public double var();

}