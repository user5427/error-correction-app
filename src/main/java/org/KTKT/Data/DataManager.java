package org.KTKT.Data;

import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.Matrix.Matrix;
import org.KTKT.SettingsPage.SettingsController;

import java.util.List;


public class DataManager {
    private static DataManager instance = null;

    /**
     * Singleton
     * @return
     */
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

    /**
     * Generate clean G matrix
     * Used by UI
     */
    public void generateCleanG_matrix() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException(ErrorConstants.K_N_INVALID);
        }
        G_matrix = DataCompute.generateCleanG(rows_k, columns_n);
    }

    /**
     * Generate random G matrix
     * Used by UI
     */
    public void generateRandomG_matrix() {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException(ErrorConstants.K_N_INVALID);
        }
        G_matrix = DataCompute.generateRandomG(rows_k, columns_n);
    }

    public Matrix getG_matrix() {
        return G_matrix;
    }

    public Matrix getH_matrix() {
        if (H_matrix == null) {
            throw new IllegalArgumentException(ErrorConstants.H_MATRIX_NOT_GENERATED);
        }
        return H_matrix;
    }

    public List<CosetSyndromWeight> getCosetSyndromWeightTable() {
        if (cosetSyndromWeights == null) {
            throw new IllegalArgumentException(ErrorConstants.COSSET_SYNDROM_WEIGHTS_NOT_GENERATED);
        }
        return cosetSyndromWeights;
    }

    /**
     * Save settings
     * Used by UI
     */
    private static boolean THREAD_ACTIVE = false;
    public static boolean getThreadActive() {
        return THREAD_ACTIVE;
    }
    public static boolean THREAD_INSTRUCTED_TO_STOP = false;
    public static void stopThread() {
        THREAD_INSTRUCTED_TO_STOP = true;
    }
    public void saveSettings(SettingsController settingsController) {
        if (!savedRowsColumnsCount) {
            throw new IllegalArgumentException(ErrorConstants.K_N_INVALID);
        }
        if (!savedMatrix) {
            throw new IllegalArgumentException(ErrorConstants.MATRIX_INVALID);
        }
        if (!THREAD_ACTIVE){
            THREAD_ACTIVE = true;
            H_matrix = DataCompute.generateH(G_matrix);
            new Thread(() -> {
                try {
                    cosetSyndromWeights = DataCompute.generateCosetSyndromWeightTable(H_matrix, settingsController);
                    if (cosetSyndromWeights != null){
                        savedSettings = true;
                        settingsController.receiveOKToOpenGeneratedParams();
                    }
                    THREAD_ACTIVE = false;
                    THREAD_INSTRUCTED_TO_STOP = false;
                } catch (Exception e) {
                    savedSettings = false;
                    THREAD_ACTIVE = false;
                    THREAD_INSTRUCTED_TO_STOP = false;
                    e.printStackTrace();
                }
            }).start();
        }

    }

    public boolean isSavedSettings() {
        return savedSettings;
    }
}
