package nspace;

import java.util.*;

public class NSpaceCoordinate {
    protected ArrayList<Double> coords;

    public NSpaceCoordinate(Double[] coords) {
        this.coords = new ArrayList<>(Arrays.asList(coords));
    }

    public final double getCoordinateAt(int index) {
        return this.coords.get(index);
    }

    public final double magnitude() {
        double sumOfSquares = 0;
        for (int i = 0; i < this.coords.size(); ++i) {
            sumOfSquares += Math.pow(this.coords.get(i), 2);
        }
        return Math.sqrt(sumOfSquares);
    }
}