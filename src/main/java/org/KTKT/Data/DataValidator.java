package org.KTKT.Data;

import org.KTKT.Data.Matrix.Matrix;

public class DataValidator {
    static public String K_N_INVALID = "k and n are invalid";
    static public String K_INVALID = "k is invalid";
    static public String N_INVALID = "n is invalid";
    static public String PROBABILITY_INVALID = "probability is invalid";
    static public String MATRIX_INVALID = "matrix is invalid";
    static public String MATRIX_DIMENSIONS_INVALID = "matrix dimensions are invalid. (do not match k or n)";
    static public String MATRIX_NOT_CREATED = "matrix is not created";
    static public String INVALID_NUMBER = "invalid number";
    static public String VALID = "valid";
    static public String ERROR = "Error: ";
    static public String PREVIOUS_MATRIX_NOT_FOUND = "Previous matrix not found";


    static public boolean ValidateRowsColumnsCount(int rowsK, int columnsN) {
        ValidateRows(rowsK);
        ValidateColumns(columnsN);

        if (rowsK >= columnsN) {
            throw new IllegalArgumentException("rows_k must be less than columns_n.");
        }

        return true;
    }

    static public boolean ValidateRows(int rowsK) {
        if (rowsK <= 0) {
            throw new IllegalArgumentException("rows_k cannot be less or equal to 0.");
        }
        return true;
    }

    static public boolean ValidateRows(String rowsK) {
        if (rowsK == null) {
            throw new IllegalArgumentException("rows_k cannot be null.");
        }
        if (rowsK.isEmpty()) {
            throw new IllegalArgumentException("rows_k cannot be empty.");
        }
        try {
            int rows = Integer.parseInt(rowsK);
            return ValidateRows(rows);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("rows_k must be a number.");
        }
    }

    static public boolean ValidateColumns(int columnsN) {
        if (columnsN <= 0) {
            throw new IllegalArgumentException("columns_n cannot be less or equal to 0.");
        }
        return true;
    }

    static public boolean ValidateColumns(String columnsN) {
        if (columnsN == null) {
            throw new IllegalArgumentException("columns_n cannot be null.");
        }
        if (columnsN.isEmpty()) {
            throw new IllegalArgumentException("columns_n cannot be empty.");
        }
        try {
            int columns = Integer.parseInt(columnsN);
            return ValidateColumns(columns);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("columns_n must be a number.");
        }
    }

    static public boolean ValidateProbability(double probability_p){
        if (probability_p < 0) {
            throw new IllegalArgumentException("probability cannot be negative.");
        }
        if (probability_p > 1) {
            throw new IllegalArgumentException("probability cannot be more than 1.");
        }
        return true;
    }

    static public boolean ValidateProbability(String probability_p){
        if (probability_p == null) {
            throw new IllegalArgumentException("probability cannot be null.");
        }
        if (probability_p.isEmpty()) {
            throw new IllegalArgumentException("probability cannot be empty.");
        }
        try {
            double probability = Double.parseDouble(probability_p);
            return ValidateProbability(probability);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("probability must be a number.");
        }
    }

    static public boolean ValidateMatrix(Matrix matrix){
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null.");
        }
        // Matrix elemenets must be either 0 or 1
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (matrix.get(i, j) != 0 && matrix.get(i, j) != 1) {
                    throw new IllegalArgumentException("Matrix elements must be either 0 or 1.");
                }
            }
        }
        return true;
    }
}
