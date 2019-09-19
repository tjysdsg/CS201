package app;

import nspace.Complex;
import nspace.StandardComplex;
import nspace.PolarComplex;

public class Test {
    public static void main(String[] args) {
        Complex a = new StandardComplex(1, 1);
        Complex b = new PolarComplex(Math.sqrt(2), Math.PI / 4);
        System.out.println("(1 + i) - (1 + i): " + b.minus(a)); // should be approx. 0
        System.out.println("(1 + i) - (1 + i): " + a.minus(b)); // should be approx. 0

        Complex a2 = new StandardComplex(-1, -1);
        Complex b2 = new PolarComplex(Math.sqrt(2), Math.PI / 4);
        System.out.println("(-1 - i) + (1 + i): " + a2.plus(b2)); // should be approx. 0
        System.out.println("(1 + i) + (-1 - i): " + b2.plus(a2)); // should be approx. 0
    }
}