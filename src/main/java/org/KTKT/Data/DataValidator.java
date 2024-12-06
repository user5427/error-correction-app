package org.KTKT.Data;

import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.Matrix.Matrix;

/**
 * Class for data validation
 */
public class DataValidator {
    public static final int KNLimit = 50;
    public static final int KNDIFFERENCE_LIMIT = 15;
    public static final int SAFE_KNDIFFERENCE_LIMIT = 10;

    static public boolean ValidateRowsColumnsCount(int rowsK, int columnsN) {
        ValidateRows(rowsK);
        ValidateColumns(columnsN);

        if (rowsK >= columnsN) {
            throw new IllegalArgumentException(ErrorConstants.ROWS_K_LESS_THAN_COLUMNS_N);
        }

        if (columnsN - rowsK > KNDIFFERENCE_LIMIT) {
            throw new IllegalArgumentException(ErrorConstants.DIFFERENCE_BETWEEN_COLUMNS_N_AND_ROWS_K);
        }

        if (columnsN > KNLimit || rowsK > KNLimit) {
            throw new IllegalArgumentException(ErrorConstants.ROWS_COLUMNS_LIMIT);
        }

        return true;
    }

    static public boolean ValidateRows(int rowsK) {
        if (rowsK <= 0) {
            throw new IllegalArgumentException(ErrorConstants.ROWS_K_LESS_OR_EQUAL_TO_ZERO);
        }
        return true;
    }

    static public boolean ValidateRows(String rowsK) {
        if (rowsK == null) {
            throw new IllegalArgumentException(ErrorConstants.ROWS_K_NULL);
        }
        if (rowsK.isEmpty()) {
            throw new IllegalArgumentException(ErrorConstants.ROWS_K_EMPTY);
        }
        try {
            int rows = Integer.parseInt(rowsK);
            return ValidateRows(rows);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorConstants.ROWS_K_MUST_BE_A_NUMBER);
        }
    }

    static public boolean ValidateColumns(int columnsN) {
        if (columnsN <= 0) {
            throw new IllegalArgumentException(ErrorConstants.COLUMNS_N_LESS_OR_EQUAL_TO_ZERO);
        }
        return true;
    }

    static public boolean ValidateColumns(String columnsN) {
        if (columnsN == null) {
            throw new IllegalArgumentException(ErrorConstants.COLUMNS_N_NULL);
        }
        if (columnsN.isEmpty()) {
            throw new IllegalArgumentException(ErrorConstants.COLUMNS_N_EMPTY);
        }
        try {
            int columns = Integer.parseInt(columnsN);
            return ValidateColumns(columns);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorConstants.COLUMNS_N_MUST_BE_A_NUMBER);
        }
    }

    static public boolean ValidateProbability(double probability_p){
        if (probability_p < 0) {
            throw new IllegalArgumentException(ErrorConstants.PROBABILITY_NEGATIVE);
        }
        if (probability_p > 1) {
            throw new IllegalArgumentException(ErrorConstants.PROBABILITY_MORE_THAN_ONE);
        }
        return true;
    }

    static public boolean ValidateProbability(String probability_p){
        if (probability_p == null) {
            throw new IllegalArgumentException(ErrorConstants.PROBABILITY_NULL);
        }
        if (probability_p.isEmpty()) {
            throw new IllegalArgumentException(ErrorConstants.PROBABILITY_EMPTY);
        }
        try {
            double probability = Double.parseDouble(probability_p);
            return ValidateProbability(probability);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorConstants.PROBABILITY_MUST_BE_A_NUMBER);
        }
    }

    static public boolean ValidateMatrix(Matrix matrix){
        if (matrix == null) {
            throw new IllegalArgumentException(ErrorConstants.MATRIX_NULL);
        }
        // Matrix elemenets must be either 0 or 1
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (matrix.get(i, j) != 0 && matrix.get(i, j) != 1) {
                    throw new IllegalArgumentException(ErrorConstants.MATRIX_ELEMENTS_ZERO_ONE);
                }
            }
        }
        return true;
    }

    static public boolean ValidateUserInput (int k, String message, char n) {
        char[] messageArray = message.toCharArray();
        if (messageArray.length != k) {
            throw new IllegalArgumentException(ErrorConstants.VECTOR_LENGTH_EQUAL + n + ".");
        }
        for (int i = 0; i < k; i++) {
            if (messageArray[i] != '0' && messageArray[i] != '1') {
                throw new IllegalArgumentException(ErrorConstants.VECTOR_ELEMENTS_ZERO_ONE);
            }
        }
        return true;
    }
}
