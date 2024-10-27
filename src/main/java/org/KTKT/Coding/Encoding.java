package org.KTKT.Coding;

import org.KTKT.Data.Matrix.Matrix;
import org.KTKT.Data.Matrix.MatrixInt;

public class Encoding {
    public static int[] encode(MatrixInt G_matrix, int[] message) {
        int[] res = new Matrix(message).multiply(G_matrix).toVector();

        return res;
    }
}
