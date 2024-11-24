package org.KTKT.CodingPages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Coding.ESDResultRecords.MessageESDResult;
import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.DataValidator;

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

    @FXML
    void probabilitySlider(MouseEvent event) {
        probInputField.setText(String.valueOf(probabilitySlider.getValue()));
    }

    @FXML
    void sendMessage(MouseEvent event) {

        if (!userInputValid) {
            return;
        }

        String text = textField.getText();
        CodingManager.getInstance().ESDText(text, (float) probabilitySlider.getValue(), this);

        sendC.setStyle("-fx-fill: yellow");
        sendLabel.setText("Sending message.");
    }

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

    @Override
    public void setESDStatus(Double status) {
        Platform.runLater(() -> updateProgressBar(status));

    }

    private void updateProgressBar(Double status) {
        progressBar.setProgress(status);
    }

    public void receiveResult(MessageESDResult result) {
        Platform.runLater(() -> {
            decodedMessage.setText(result.withDecoding);
            noDecoding.setText(result.withoutDecoding);
            sendC.setStyle("-fx-fill: green");
            sendLabel.setText("Message sent.");
        });
    }
}
