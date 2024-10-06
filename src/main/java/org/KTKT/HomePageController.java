package org.KTKT;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.KTKT.SettingsPage.SettingsController;

import java.io.IOException;

import static org.KTKT.Utils.WindowManager.WindowManager.openWindow;

public class HomePageController {
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
        openWindow(event, "Settings/settings-page.fxml", new SettingsController());
    }

    @FXML
    void showProgramInfo(MouseEvent event) {

    }
}