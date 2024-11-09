package org.KTKT;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.KTKT.CodingPages.NoParametersPage;
import org.KTKT.CodingPages.SendVectorPage;
import org.KTKT.Constants.Constants;
import org.KTKT.Data.DataManager;
import org.KTKT.SettingsPage.SettingsController;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static org.KTKT.Data.SeamlessWeightVectors.SeamlessWeightVectors.generateSeamlessWeightVector;
import static org.KTKT.Utils.WindowManager.WindowManager.openOverlayWindow;
import static org.KTKT.Utils.WindowManager.WindowManager.openWindow;

public class HomePageController implements Initializable {
    @FXML
    void sendImage(MouseEvent event) throws IOException {
        if (!DataManager.getInstance().isSavedSettings()){
            openOverlayWindow(event, Constants.NO_PARAM_GENERATED, new NoParametersPage());
            return;
        }
    }

    @FXML
    void sendMVector(MouseEvent event) throws IOException {
        if (!DataManager.getInstance().isSavedSettings()){
            openOverlayWindow(event, Constants.NO_PARAM_GENERATED, new NoParametersPage());
            return;
        }
        openWindow(event, Constants.SEND_VECTOR_PAGE_FXML, new SendVectorPage());
    }

    @FXML
    void sendText(MouseEvent event) throws IOException {
        if (!DataManager.getInstance().isSavedSettings()){
            openOverlayWindow(event, Constants.NO_PARAM_GENERATED, new NoParametersPage());
            return;
        }
    }

    @FXML
    void setSettings(MouseEvent event) throws IOException {
        openWindow(event, Constants.SETTINGS_PAGE_FXML, new SettingsController());
    }

    @FXML
    void showProgramInfo(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}