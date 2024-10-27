package org.KTKT.Data;

import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.Matrix.Matrix;

import java.util.List;

import static org.KTKT.Data.DataValidator.MATRIX_NOT_CREATED;

public class DataManager {
    private static DataManager instance = null;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }


    private int rows_k, columns_n;

    private Boolean savedRowsColumnsCount = false;
    private Boolean savedMatrix = false;

    private Matrix G_matrix;
    private Matrix H_matrix;
    private List<CosetSyndromWeight> cosetSyndromWeights;

    private boolean savedSettings = false;

    public void setRowsColumnsCount(int rows_k, int columns_n) {
        savedRowsColumnsCount = false;
        savedRowsColumnsCount = DataValidator.ValidateRowsColumnsCount( rows_k,  columns_n);
        this.rows_k = rows_k;
        this.columns_n = columns_n;
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

    public void resetDataManager() {
        G_matrix = null;
        H_matrix = null;
        savedRowsColumnsCount = false;
        savedMatrix = false;
        rows_k = 0;
        columns_n = 0;
        cosetSyndromWeights = null;
        savedSettings = false;
    }

    public List<CosetSyndromWeight> getCosetSyndromWeights() {
        return cosetSyndromWeights;
    }

    public void generateCleanG_matrix() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException(DataValidator.K_N_INVALID);
        }
        G_matrix = DataCompute.generateCleanG(rows_k, columns_n);
    }

    public void generateRandomG_matrix() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException(DataValidator.K_N_INVALID);
        }
        G_matrix = DataCompute.generateRandomG(rows_k, columns_n);
    }

    public Matrix getG_matrix() {
        return G_matrix;
    }

    public Matrix getH_matrix() {
        if (H_matrix == null) {
            throw new IllegalArgumentException(DataValidator.H_MATRIX_NOT_GENERATED);
        }
        return H_matrix;
    }

    public List<CosetSyndromWeight> getCosetSyndromWeightTable() {
        if (cosetSyndromWeights == null) {
            throw new IllegalArgumentException(DataValidator.COSSET_SYNDROM_WEIGHTS_NOT_GENERATED);
        }
        return cosetSyndromWeights;
    }

    public void saveSettings() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException(DataValidator.K_N_INVALID);
        }
        if (!savedMatrix) {
            throw new IllegalArgumentException(DataValidator.MATRIX_INVALID);
        }
        H_matrix = DataCompute.generateH(G_matrix);
        cosetSyndromWeights = DataCompute.generateCosetSyndromWeightTable(H_matrix);
        savedSettings = true;
    }

    public boolean isSavedSettings() {
        return savedSettings;
    }
}
