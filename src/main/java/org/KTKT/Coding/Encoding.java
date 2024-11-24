package org.KTKT.Coding;

import org.KTKT.Data.Matrix.Matrix;
import org.KTKT.Data.Matrix.MatrixInt;

public class Encoding {
    /**
     * Encodes the message using G matrix
     * @param G_matrix
     * @param message
     * @return
     */
    public static int[] encode(MatrixInt G_matrix, int[] message) {
        int[] res = new Matrix(message).multiply(G_matrix).toVector();

        return res;
    }
}

