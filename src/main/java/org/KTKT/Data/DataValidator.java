package org.KTKT.Data;

public class DataValidator {

    static public boolean ValidateRowsColumnsCount(int rowsK, int columnsN) {
        ValidateRows(rowsK);
        ValidateColumns(columnsN);

        if (rowsK > columnsN) {
            throw new IllegalArgumentException("rows_k must be less than or equal to columns_n.");
        }

        return true;
    }

    static public boolean ValidateRows(int rowsK) {
        if (rowsK <= 0) {
            throw new IllegalArgumentException("rows_k cannot be less or equal to 0.");
        }
        return true;
    }

    static public boolean ValidateColumns(int columnsN) {
        if (columnsN <= 0) {
            throw new IllegalArgumentException("columns_n cannot be less or equal to 0.");
        }
        return true;
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
}
