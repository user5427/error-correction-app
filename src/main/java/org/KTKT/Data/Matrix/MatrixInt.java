package org.KTKT.Data.Matrix;

public interface MatrixInt {
    int getRows();
    int getColumns();
    int get(int row, int column);
    void set(int row, int column, int value);
    void setRow(int row, int[] values);
    void setColumn(int column, int[] values);
    int[] getRow(int row);
    int[] getColumn(int column);
    void setAll(int[][] values);
    int[][] getAll();

    MatrixInt add(MatrixInt other);
    MatrixInt subtract(MatrixInt other);
    MatrixInt multiply(MatrixInt other);
    MatrixInt transpose(MatrixInt other);


}
