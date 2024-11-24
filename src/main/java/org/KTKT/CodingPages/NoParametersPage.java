package org.KTKT.CodingPages;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Shows popup when there are no parameters generated
 */
public class NoParametersPage {
    @FXML
    void close(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }
}
