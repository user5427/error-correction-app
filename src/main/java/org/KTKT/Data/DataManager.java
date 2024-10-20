package org.KTKT.Data;

import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeightTable;
import org.KTKT.Data.Matrix.Matrix;

import static org.KTKT.Data.DataValidator.MATRIX_NOT_CREATED;

public class DataManager {
    private static DataManager instance = null;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }


    int rows_k, columns_n;
    double probability_p;

    Boolean savedRowsColumnsCount = false;
    Boolean savedProbability = false;
    Boolean savedMatrix = false;

    Matrix G_matrix;
    Matrix A_User_matrix;
    Matrix H_matrix;
    CosetSyndromWeightTable cosetSyndromWeightTable;

    public DataManager() {
    }

    public void setRowsColumnsCount(int rows_k, int columns_n) {
        savedRowsColumnsCount = false;
        savedRowsColumnsCount = DataValidator.ValidateRowsColumnsCount( rows_k,  columns_n);
        this.rows_k = rows_k;
        this.columns_n = columns_n;
    }

    public void setProbability(double probability_p) {
        savedProbability = false;
        savedProbability = DataValidator.ValidateProbability(probability_p);
        this.probability_p = probability_p;
    }

    public int getColumns_n() {
        return columns_n;
    }

    public int getRows_k() {
        return rows_k;
    }

    public void setG_matrix(Matrix G_matrix) {
        savedMatrix = false;
        savedMatrix = DataValidator.ValidateMatrix(G_matrix);
        this.G_matrix = G_matrix;
    }

    public void resetMatrix() {
        G_matrix = null;
        A_User_matrix = null;
        H_matrix = null;
    }

    public void generateCleanG_matrix() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException("rows_k or columns_n is invalid.");
        }
        G_matrix = new Matrix(rows_k, columns_n);
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
    }

    public void generateRandomG_matrix() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException("rows_k or columns_n is invalid.");
        }
        G_matrix = new Matrix(rows_k, columns_n);
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
    }

    public Matrix getG_matrix() {
        return G_matrix;
    }

    public Matrix getH_matrix() {
        return H_matrix;
    }

    public Matrix getA_User_matrix() {
        return A_User_matrix;
    }

    public void updateA_User_matrix() {
        A_User_matrix = new Matrix(columns_n - rows_k, columns_n);
        G_matrix = new Matrix(rows_k, columns_n);
        for (int i = rows_k; i < columns_n; i++) {
            for (int j = 0; j < rows_k; j++) {
                A_User_matrix.set(j, i-rows_k, G_matrix.get(j, i));
            }
        }

    }

    public void generateFromPreviousG_Matrix() {
        if (G_matrix == null) {
            throw new IllegalArgumentException(MATRIX_NOT_CREATED);
        }

        if (this.previousG_matrix == null) {
            throw new IllegalArgumentException(DataValidator.PREVIOUS_MATRIX_NOT_FOUND);
        }

        Matrix previousG_matrix = this.previousG_matrix;
        generateCleanG_matrix();
        for (int i = 0; i < rows_k; i++) {
            for (int j = rows_k; j < columns_n; j++) {
                if (i < previousG_matrix.getRows() && j < previousG_matrix.getColumns()) {
                    G_matrix.set(i, j, previousG_matrix.get(i, j));
                } else {
                    G_matrix.set(i, j, 0);
                }
            }
        }
    }

    Matrix previousG_matrix;
    public void setPreviousG_matrix(Matrix previousG_matrix) {
        this.previousG_matrix = previousG_matrix;
    }
}
