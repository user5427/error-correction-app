package org.KTKT.Data;

import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.Matrix.Matrix;
import org.KTKT.Data.Matrix.MatrixInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.KTKT.Data.SeamlessWeightVectors.SeamlessWeightVectors.generateSeamlessWeightVector;

public class DataCompute {

    public static Matrix generateCleanG(int rows_k, int columns_n) {
        Matrix G_matrix = new Matrix(rows_k, columns_n);
        // first k * k is
        // 1 0 0 ...
        // 0 1 0 ...
        // 0 0 1 ...
        // ...
        for (int i = 0; i < rows_k; i++) {
            G_matrix.set(i, i, 1);
        }

        // next k * (n - k) is 0
        for (int i = 0; i < rows_k; i++) {
            for (int j = rows_k; j < columns_n; j++) {
                G_matrix.set(i, j, 0);
            }
        }

        return G_matrix;
    }

    public static Matrix generateRandomG(int rows_k, int columns_n) {
        Matrix G_matrix = new Matrix(rows_k, columns_n);
        // first k * k is
        // 1 0 0 ...
        // 0 1 0 ...
        // 0 0 1 ...
        // ...
        for (int i = 0; i < rows_k; i++) {
            G_matrix.set(i, i, 1);
        }

        // next k * (n - k) is random
        for (int i = 0; i < rows_k; i++) {
            for (int j = rows_k; j < columns_n; j++) {
                G_matrix.set(i, j, (int) (Math.random() * 2));
            }
        }

        return G_matrix;
    }

    public static Matrix generateH(Matrix G_matrix) {
        int rows_k = G_matrix.getRows();
        int columns_n = G_matrix.getColumns();
        Matrix A_User_matrix = new Matrix(rows_k, columns_n - rows_k);
        for (int i = rows_k; i < columns_n; i++) {
            for (int j = 0; j < rows_k; j++) {
                A_User_matrix.set(j, i-rows_k, G_matrix.get(j, i));
            }
        }

        Matrix H_matrix = new Matrix(columns_n - rows_k, columns_n);

        Matrix A_Transpose_matrix = (Matrix) A_User_matrix.transpose();
        // H = [A^T I]
        for (int i = 0; i < columns_n - rows_k; i++) {
            for (int j = 0; j < columns_n; j++) {
                if (j < rows_k) {
                    H_matrix.set(i, j, A_Transpose_matrix.get(i, j));
                } else {
                    if (j - rows_k == i) {
                        H_matrix.set(i, j, 1);
                    } else {
                        H_matrix.set(i, j, 0);
                    }
                }
            }
        }

        return H_matrix;
    }

    public static List<CosetSyndromWeight> generateCosetSyndromWeightTable(Matrix H_matrix) {
        int k = H_matrix.getColumns() - H_matrix.getRows();
        int n = H_matrix.getColumns();

        List<CosetSyndromWeight> cosetSyndromWeights = new ArrayList<>();
        int sindromeCount = (int) Math.pow(2, n-k);
        int[] yVector = new int[n];
        Arrays.fill(yVector, 0);
        boolean initialFilled = true;

        while (cosetSyndromWeights.size() < sindromeCount) {
            MatrixInt res = new Matrix(H_matrix).multiply(new Matrix(yVector).transpose());
            int[] vectorRes = res.transpose().toVector();
            // check if the result is in the table
            boolean found = false;
            for (CosetSyndromWeight cosetSyndromWeight : cosetSyndromWeights) {
                if (Arrays.equals(cosetSyndromWeight.getSyndrom(), vectorRes)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                cosetSyndromWeights.add(new CosetSyndromWeight(yVector, vectorRes));
            }

            // generate next yVector
            if (initialFilled){
                yVector = new int[n];
                Arrays.fill(yVector, 0);
                yVector[0] = 1;
                initialFilled = false;
            } else {
                yVector = generateSeamlessWeightVector(yVector);
            }
        }

        return cosetSyndromWeights;
    }

}
