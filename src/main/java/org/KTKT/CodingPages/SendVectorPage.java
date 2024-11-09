package org.KTKT.CodingPages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Coding.CodingUtils.TextUtils;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class SendVectorPage implements Initializable {

    boolean userInputValid = false;
    boolean channelInputValid = false;


    @FXML
    private TextArea channelOutput;

    @FXML
    private Circle decodeC;

    @FXML
    private Label decodeLabel;

    @FXML
    private Label decodedVector;

    @FXML
    private Label devodedLabel;

    @FXML
    private Rectangle encoded;

    @FXML
    private Label encodedVector;

    @FXML
    private Label messageLength;

    @FXML
    private Label probabilityLabel;

    @FXML
    private Slider probabilitySlider;

    @FXML
    private Circle sendC;

    @FXML
    private Label sendLabel;

    @FXML
    private TextArea textField;

    @FXML
    private Label encodedLenght;

    @FXML
    private Label probInput;

    @FXML
    private TextField probInputField;

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
    void decodeVector(MouseEvent event) {
        if (!channelInputValid){
            return;
        }
        int[] binaryVector = TextUtils.convertStringToIntArray(channelOutput.getText());
        int [] decoded = CodingManager.getInstance().decodeMessage(binaryVector);
        String myDecodedVector = TextUtils.convertIntArrayToString(decoded);
        decodedVector.setText(myDecodedVector);
    }

    @FXML
    void probabilitySlider(MouseEvent event) {
        probInputField.setText(String.valueOf(probabilitySlider.getValue()));
    }

    @FXML
    void sendVector(MouseEvent event) {
        if (!userInputValid){
            return;
        }
        int[] binaryVector = TextUtils.convertStringToIntArray(textField.getText());
        int [] encoded = CodingManager.getInstance().encodeMessage(binaryVector);
        String decoded = TextUtils.convertIntArrayToString(encoded);
        encodedVector.setText(decoded);

        int [] sendToChannel = CodingManager.getInstance().sendBinaryMessageToChannel(encoded, (float) probabilitySlider.getValue());
        String noisyVector = TextUtils.convertIntArrayToString(sendToChannel);
        channelOutput.setText(noisyVector);

        channelInputValid = true;
        decodeLabel.setText(DataValidator.VALID);
        decodeC.setStyle("-fx-fill: green");
        decodedVector.setText("");
    }

    @FXML
    void userTextType(KeyEvent event) {
        String text = textField.getText();
        try {
            DataValidator.ValidateUserInput(DataManager.getInstance().getRows_k(), text, 'k');
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

    @FXML
    void channelTextType(KeyEvent event) {
        // must be 0 or 1
        String text = channelOutput.getText();
        try {
            DataValidator.ValidateUserInput(DataManager.getInstance().getColumns_n(), text, 'n');
        } catch (IllegalArgumentException e) {
            channelInputValid = false;
            decodeLabel.setText(e.getMessage());
            decodeC.setStyle("-fx-fill: red");
            return;
        }

        channelInputValid = true;
        decodeLabel.setText(DataValidator.VALID);
        decodeC.setStyle("-fx-fill: green");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLength.setText("k: " + (DataManager.getInstance().getRows_k()));
        encodedLenght.setText("n: " + (DataManager.getInstance().getColumns_n()));
    }
}
