package Utils.Matrix;

public class MatrixImpl implements Matrix, Cloneable {
    private double[][] matrix;
    private int rows;
    private int columns;

    public MatrixImpl(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

    public MatrixImpl(double[][] values) {
        rows = values.length;
        columns = values[0].length;
        matrix = values;
    }

    public MatrixImpl(Matrix other) {
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
    public double get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
            return matrix[row][column];
    }

    @Override
    public void set(int row, int column, double value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
            matrix[row][column] = value;
    }

    @Override
    public void setRow(int row, double[] values) {
        if (row < 0 || row >= rows || values.length != columns) {
            throw new IllegalArgumentException("Invalid row index or number of values");
        }
            matrix[row] = values;
    }

    @Override
    public void setColumn(int column, double[] values) {
        if (column < 0 || column >= columns || values.length != rows) {
            throw new IllegalArgumentException("Invalid column index or number of values");
        }
            for (int i = 0; i < rows; i++) {
                matrix[i][column] = values[i];
            }
    }

    @Override
    public double[] getRow(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Invalid row index");
        }
            return matrix[row];
    }

    @Override
    public double[] getColumn(int column) {
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Invalid column index");
        }
            double[] values = new double[rows];
            for (int i = 0; i < rows; i++) {
                values[i] = matrix[i][column];
            }
            return values;
    }

    @Override
    public void setAll(double[][] values) {
        if (values.length != rows || values[0].length != columns) {
            throw new IllegalArgumentException("Invalid number of rows or columns");
        }
        matrix = values;
    }

    @Override
    public double[][] getAll() {
        return matrix;
    }

    @Override
    public Matrix add(Matrix other) {
        if (other.getRows() != rows || other.getColumns() != columns) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] += other.get(i, j);
            }
        }
        return this;
    }

    @Override
    public Matrix subtract(Matrix other) {
        if (other.getRows() != rows || other.getColumns() != columns) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] -= other.get(i, j);
            }
        }
        return this;
    }

    /**
     * Multiply two matrices
     * result = this * other
     * result is not this or other
     * @param other
     * @return
     */
    @Override
    public Matrix multiply(Matrix other) {
        if (columns != other.getRows()) {
            throw new IllegalArgumentException("Invalid matrix dimensions");
        }

        Matrix result = new MatrixImpl(rows, other.getColumns());

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < other.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < columns; k++)
                    sum += matrix[i][k] * other.get(k, j);
                result.set(i, j, sum);
            }
        return result;
    }

    @Override
    public Matrix multiply(double scalar) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                matrix[i][j] *= scalar;
            }
        return this;
    }

    /**
     * Transpose the matrix
     * result is not this
     * @param other
     * @return
     */
    @Override
    public Matrix transpose(Matrix other) {
        Matrix result = new MatrixImpl(columns, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                result.set(j, i, matrix[i][j]);
            }
        return result;
    }

    @Override
    public Matrix clone() {
        return new MatrixImpl(this);
    }
}
