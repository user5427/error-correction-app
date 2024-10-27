package org.KTKT;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.KTKT.Constants.Constants;
import org.KTKT.SettingsPage.SettingsController;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static org.KTKT.Data.SeamlessWeightVectors.SeamlessWeightVectors.generateSeamlessWeightVector;
import static org.KTKT.Utils.WindowManager.WindowManager.openWindow;

public class HomePageController implements Initializable {
    @FXML
    void sendImage(MouseEvent event) {

    }

    @FXML
    void sendMVector(MouseEvent event) {

    }

    @FXML
    void sendText(MouseEvent event) {

    }

    @FXML
    void setSettings(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        openWindow(event, Constants.SETTINGS_PAGE_FXML, new SettingsController());
    }

    @FXML
    void showProgramInfo(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}