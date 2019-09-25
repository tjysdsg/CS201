package app;

import java.util.ArrayList;
import java.util.Collections;

import nspace.Complex;
import nspace.StandardComplex;
import nspace.PolarComplex;

public class Test {
    public static void main(String[] args) {
        Complex a = new StandardComplex(1, 2);
        Complex b = new PolarComplex(-1, 0);
        Complex c = new PolarComplex(1, 0);
        Complex d = new StandardComplex(4, 5);

        ArrayList<Complex> l = new ArrayList<Complex>();
        l.add(a);
        l.add(b);
        l.add(c);
        l.add(d);
        Collections.sort(l);

        System.out.println(l);
    }
}