package org.KTKT.Data;

import org.KTKT.Data.Matrix.Matrix;

public class DataValidator {
    private static final int KNLimit = 50;
    private static final int KNDIFFERENCE_LIMIT = 10;

    static public boolean ValidateRowsColumnsCount(int rowsK, int columnsN) {
        ValidateRows(rowsK);
        ValidateColumns(columnsN);

        if (rowsK >= columnsN) {
            throw new IllegalArgumentException("rows_k must be less than columns_n.");
        }

        if (columnsN - rowsK > KNDIFFERENCE_LIMIT) {
            throw new IllegalArgumentException("difference between columns_n and rows_k must be less than 10.");
        }

        if (columnsN > KNLimit || rowsK > KNLimit) {
            throw new IllegalArgumentException("rows_k and columns_n must be less than 50.");
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

    static public boolean ValidateUserInput (int k, String message, char n) {
        char[] messageArray = message.toCharArray();
        if (messageArray.length != k) {
            throw new IllegalArgumentException("Vector length must be equal to " + n + ".");
        }
        for (int i = 0; i < k; i++) {
            if (messageArray[i] != '0' && messageArray[i] != '1') {
                throw new IllegalArgumentException("Vector elements must be either 0 or 1.");
            }
        }
        return true;
    }
}
