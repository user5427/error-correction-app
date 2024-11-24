package org.KTKT.Data.Matrix;

import org.KTKT.Constants.ErrorConstants;

public class Matrix implements MatrixInt, Cloneable {
    private static final int MOD = 2;
    private int[][] matrix;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROWS_COLUMNS_COUNT);
        }
        this.rows = rows;
        this.columns = columns;
        matrix = new int[rows][columns];
    }

    public Matrix(int [] vector) {
        this.rows = 1;
        this.columns = vector.length;
        matrix = new int[1][vector.length];
        matrix[0] = vector;
    }

    public Matrix(int[][] values) {
        if (values.length == 0 || values[0].length == 0) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROWS_COLUMNS_COUNT);
        }
        rows = values.length;
        columns = values[0].length;
        matrix = values;
    }

    public Matrix(MatrixInt other) {
        rows = other.getRows();
        columns = other.getColumns();
        matrix = other.getAll();
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROW_COLUMNS_INDEX);
        }
            return matrix[row][column];
    }

    @Override
    public void set(int row, int column, int value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROW_COLUMNS_INDEX);
        }
            matrix[row][column] = value;
    }

    @Override
    public void setRow(int row, int[] values) {
        if (row < 0 || row >= rows || values.length != columns) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROW_INDEX_OR_VALUES_COUNT);
        }
            matrix[row] = values;
    }

    @Override
    public void setColumn(int column, int[] values) {
        if (column < 0 || column >= columns || values.length != rows) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_COLUMN_INDEX_OR_VALUES_COUNT);
        }
            for (int i = 0; i < rows; i++) {
                matrix[i][column] = values[i];
            }
    }

    @Override
    public int[] getRow(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROW_INDEX);
        }
            return matrix[row];
    }

    @Override
    public int[] getColumn(int column) {
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_COLUMN_INDEX);
        }
            int[] values = new int[rows];
            for (int i = 0; i < rows; i++) {
                values[i] = matrix[i][column];
            }
            return values;
    }

    @Override
    public void setAll(int[][] values) {
        if (values.length != rows || values[0].length != columns) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_ROWS_COLUMNS_COUNT);
        }
        matrix = values;
    }

    @Override
    public int[][] getAll() {
        return matrix;
    }

    @Override
    public MatrixInt add(MatrixInt other) {
        if (other.getRows() != rows || other.getColumns() != columns) {
            throw new IllegalArgumentException(ErrorConstants.SAME_DIMENSIONS_REQUIRED);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = (matrix[i][j] + other.get(i, j)) % MOD;
            }
        }
        return this;
    }

    @Override
    public MatrixInt subtract(MatrixInt other) {
        if (other.getRows() != rows || other.getColumns() != columns) {
            throw new IllegalArgumentException(ErrorConstants.SAME_DIMENSIONS_REQUIRED);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = (matrix[i][j] - other.get(i, j)) % MOD;
                if (matrix[i][j] < 0){
                    matrix[i][j] *= -1;
                }
            }
        }
        return this;
    }

    /**
     * Multiply two matrices
     * result is this
     * @param other
     * @return
     */
    @Override
    public MatrixInt multiply(MatrixInt other) {
        if (columns != other.getRows()) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_MATRIX_DIMENSIONS);
        }

        int[][] result = new int[rows][other.getColumns()];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < other.getColumns(); j++) {
                int sum = 0;
                for (int k = 0; k < columns; k++)
                    sum += matrix[i][k] * other.get(k, j);
                result[i][j] = sum % MOD;
            }

        matrix = result;
        rows = result.length;
        columns = result[0].length;
        return this;
    }

    /**
     * Transpose the matrix
     * result is this
     * @return
     */
    @Override
    public MatrixInt transpose() {
        int [][] result = new int[columns][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                result[j][i] = matrix[i][j];
            }

        matrix = result;
        rows = result.length;
        columns = result[0].length;
        return this;
    }

    @Override
    public boolean equals(MatrixInt other) {
        if (rows != other.getRows() || columns != other.getColumns()) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] != other.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int[] toVector() {
        if (rows != 1) {
            throw new IllegalArgumentException(ErrorConstants.ONLY_ONE_ROW);
        }
        return matrix[0];
    }

    @Override
    public MatrixInt clone() {
        return new Matrix(this);
    }
}
