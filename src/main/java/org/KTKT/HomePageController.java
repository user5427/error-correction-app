package org.KTKT;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.KTKT.CodingPages.NoParametersPage;
import org.KTKT.CodingPages.SendImagePage;
import org.KTKT.CodingPages.SendTextPage;
import org.KTKT.CodingPages.SendVectorPage;
import org.KTKT.Constants.FileConstants;
import org.KTKT.Data.DataManager;
import org.KTKT.SettingsPage.SettingsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.KTKT.Utils.WindowManager.WindowManager.openOverlayWindow;
import static org.KTKT.Utils.WindowManager.WindowManager.openWindow;

public class HomePageController implements Initializable {
    /**
     * Open the image sending page.
     * @param event
     * @throws IOException
     */
    @FXML
    void sendImage(MouseEvent event) throws IOException {
        if (!DataManager.getInstance().isSavedSettings()){
            openOverlayWindow(event, FileConstants.NO_PARAM_GENERATED, new NoParametersPage());
            return;
        }
        openWindow(event, FileConstants.SEND_IMAGE_PAGE_FXML, new SendImagePage());
    }

    /**
     * Open the vector sending page.
     * @param event
     * @throws IOException
     */
    @FXML
    void sendMVector(MouseEvent event) throws IOException {
        if (!DataManager.getInstance().isSavedSettings()){
            openOverlayWindow(event, FileConstants.NO_PARAM_GENERATED, new NoParametersPage());
            return;
        }
        openWindow(event, FileConstants.SEND_VECTOR_PAGE_FXML, new SendVectorPage());
    }

    /**
     * Open the text sending page.
     * @param event
     * @throws IOException
     */
    @FXML
    void sendText(MouseEvent event) throws IOException {
        if (!DataManager.getInstance().isSavedSettings()){
            openOverlayWindow(event, FileConstants.NO_PARAM_GENERATED, new NoParametersPage());
            return;
        }
        openWindow(event, FileConstants.SEND_TEXT_PAGE_FXML, new SendTextPage());
    }

    /**
     * Open the settings page.
     * @param event
     * @throws IOException
     */
    @FXML
    void setSettings(MouseEvent event) throws IOException {
        openWindow(event, FileConstants.SETTINGS_PAGE_FXML, new SettingsController());
    }

    @FXML
    void showProgramInfo(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}