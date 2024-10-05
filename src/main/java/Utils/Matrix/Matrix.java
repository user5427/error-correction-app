package Utils.Matrix;

public interface Matrix {
    int getRows();
    int getColumns();
    double get(int row, int column);
    void set(int row, int column, double value);
    void setRow(int row, double[] values);
    void setColumn(int column, double[] values);
    double[] getRow(int row);
    double[] getColumn(int column);
    void setAll(double[][] values);
    double[][] getAll();

    Matrix add(Matrix other);
    Matrix subtract(Matrix other);
    Matrix multiply(Matrix other);
    Matrix multiply(double scalar);
    Matrix transpose(Matrix other);


}
