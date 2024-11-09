package org.KTKT.CodingPages;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;

public class SendTextPage {
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
        var result = CodingManager.getInstance().encodeSendDecodeText(text, (float) probabilitySlider.getValue());
        decodedMessage.setText(result.withDecoding);
        noDecoding.setText(result.withoutDecoding);
    }

    @FXML
    void userTextType(KeyEvent event) {
        String text = textField.getText();
        try {
            if (text.isEmpty()) {
                throw new IllegalArgumentException(DataValidator.EMPTY_TEXT);
            }
        } catch (IllegalArgumentException e) {
            userInputValid = false;
            sendLabel.setText(e.getMessage());
            sendC.setStyle("-fx-fill: red");
            return;
        }

        userInputValid = true;
        sendLabel.setText(DataValidator.VALID);
        sendC.setStyle("-fx-fill: green");
    }
}
