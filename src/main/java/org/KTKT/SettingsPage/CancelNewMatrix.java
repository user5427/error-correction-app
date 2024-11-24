package org.KTKT.SettingsPage;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Popup for asking if user wants to create a new matrix and overwrite the old one
 */
public class CancelNewMatrix {
    SettingsController settingsController;
    MatrixUpdateStatus matrixUpdateStatus;

    public CancelNewMatrix(SettingsController settingsController, MatrixUpdateStatus matrixUpdateStatus) {
        this.settingsController = settingsController;
        this.matrixUpdateStatus = matrixUpdateStatus;
    }

    @FXML
    void cancel(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        settingsController.generateMatrix(MatrixUpdateStatus.CANCEL);
    }

    @FXML
    void confirm(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        settingsController.generateMatrix(matrixUpdateStatus);
    }
}
