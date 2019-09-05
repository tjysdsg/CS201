package app;

import app.StdDraw;

public class Problem_2_3_27 {
    private static void drawTriangles(double[][] triangleCoords, int level) {
        // @note Assume that the coordinates of three points are in clockwise order

        // exit condition
        if (level == 0) {
            return;
        }
        // draw
        StdDraw.line(triangleCoords[0][0], triangleCoords[0][1], triangleCoords[1][0], triangleCoords[1][1]);
        StdDraw.line(triangleCoords[0][0], triangleCoords[0][1], triangleCoords[2][0], triangleCoords[2][1]);
        StdDraw.line(triangleCoords[1][0], triangleCoords[1][1], triangleCoords[2][0], triangleCoords[2][1]);

        // calculate new coordinates
        double[][] newCoords = new double[3][2];
        for (int i = 0; i < 2; ++i) {
            newCoords[0][i] = (triangleCoords[0][i] + triangleCoords[1][i]) / 2;
            newCoords[1][i] = (triangleCoords[1][i] + triangleCoords[2][i]) / 2;
            newCoords[2][i] = (triangleCoords[0][i] + triangleCoords[2][i]) / 2;
        }
        // the triangle at the first corner (triangleCoords[0])
        drawTriangles(new double[][] { { triangleCoords[0][0], triangleCoords[0][1] },
                { newCoords[0][0], newCoords[0][1] }, { newCoords[2][0], newCoords[2][1] } }, level - 1);
        // the triangle at the second corner (triangleCoords[1])
        drawTriangles(new double[][] { { triangleCoords[1][0], triangleCoords[1][1] },
                { newCoords[1][0], newCoords[1][1] }, { newCoords[0][0], newCoords[0][1] } }, level - 1);
        // the triangle at the last corner (triangleCoords[2])
        drawTriangles(new double[][] { { triangleCoords[2][0], triangleCoords[2][1] },
                { newCoords[1][0], newCoords[1][1] }, { newCoords[2][0], newCoords[2][1] } }, level - 1);
    }

    public static void main(String[] args) {
        // default value for depth
        int depth = 4;
        if (args.length != 0) {
            depth = Integer.parseInt(args[0]);
        }
        // draw stuff
        drawTriangles(new double[][] { { 0.1, 0.1 }, { 0.5, 0.9 }, { 0.9, 0.1 } }, depth);
    }
}
