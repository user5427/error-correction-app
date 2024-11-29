package org.KTKT.CodingPages;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Coding.ESDResultRecords.MessageESDResult;
import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.DataValidator;

import java.text.DecimalFormat;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
public class SendTextPage implements ESDStatus {
    boolean userInputValid = false;

    @FXML
    private Label decodedMessage;

    @FXML
    private Label devodedLabel;

    @FXML
    private Label noDecoding;

    @FXML
    private Label noDecodingLabel;

    @FXML
    private Label probInput;

    @FXML
    private TextField probInputField;

    @FXML
    private Slider probabilitySlider;

    @FXML
    private Circle sendC;

    @FXML
    private Label sendLabel;

    @FXML
    private TextArea textField;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label time;

    /**
     * Changes probability when input field is changed
     * @param event
     */
    @FXML
    void probChanged(KeyEvent event) {
        try {
            DataValidator.ValidateProbability(probInputField.getText());
            double probability = Double.parseDouble(probInputField.getText());
            DataValidator.ValidateProbability(probability);
            probabilitySlider.setValue(probability);
        } catch (IllegalArgumentException e) {
            probInput.setText(e.getMessage());
            return;
        }

        probInput.setText("");
    }

    /**
     * Changes probability when slider is changed
     * @param event
     */
    @FXML
    void probabilitySlider(MouseEvent event) {
        probInputField.setText(String.valueOf(probabilitySlider.getValue()));
    }

    /**
     * Sends message to ESD
     * @param event
     */
    @FXML
    void sendMessage(MouseEvent event) {

        if (!userInputValid) {
            return;
        }

        String text = textField.getText();

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        EventHandler<WindowEvent> existingHandler = stage.getOnCloseRequest();

        stage.setOnCloseRequest(e -> {
            // Call the existing handler if it exists
            if (existingHandler != null) {
                existingHandler.handle(e);
            }

            CodingManager.stopESD();
        });

        try {
            CodingManager.getInstance().ESDText(text, (float) probabilitySlider.getValue(), this);
        } catch (Exception e) {
            sendLabel.setText(e.getMessage());
            sendC.setStyle("-fx-fill: red");
            return;
        }

        sendC.setStyle("-fx-fill: yellow");
        sendLabel.setText("Siunčiama žinutė.");


    }

    /**
     * Validates user input
     * @param event
     */
    @FXML
    void userTextType(KeyEvent event) {
        String text = textField.getText();
        try {
            if (text.isEmpty()) {
                throw new IllegalArgumentException(ErrorConstants.EMPTY_TEXT);
            }
        } catch (IllegalArgumentException e) {
            userInputValid = false;
            sendLabel.setText(e.getMessage());
            sendC.setStyle("-fx-fill: red");
            return;
        }

        userInputValid = true;
        sendLabel.setText(ErrorConstants.VALID);
        sendC.setStyle("-fx-fill: green");
    }

    /**
     * Sets ESD status
     * @param status
     */
    @Override
    public void setESDStatus(Double status, Long time) {
        Platform.runLater(() -> updateProgressBar(status, time));

    }

    /**
     * Updates progress bar
     * @param status
     */
    private void updateProgressBar(Double status, Long time) {
        progressBar.setProgress(status);
        DecimalFormat df = new DecimalFormat("0.00");
        this.time.setText("Trukmė: " + df.format(time / 1000.0) + "s");
    }

    /**
     * Receives result from ESD
     * @param result
     */
    public void receiveResult(MessageESDResult result) {
        Platform.runLater(() -> {
            decodedMessage.setText(result.withDecoding);
            noDecoding.setText(result.withoutDecoding);
            sendC.setStyle("-fx-fill: green");
            sendLabel.setText("Žinutė išsiųsta.");
        });
    }

    /**
     * Saves message without code to clipboard
     * @param event
     */
    @FXML
    void saveNoCode(MouseEvent event) {
        StringSelection stringSelection = new StringSelection(noDecoding.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * Saves message with code to clipboard
     * @param event
     */
    @FXML
    void saveWithCode(MouseEvent event) {
        StringSelection stringSelection = new StringSelection(decodedMessage.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

    }
}
