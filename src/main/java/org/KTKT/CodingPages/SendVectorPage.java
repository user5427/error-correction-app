package org.KTKT.CodingPages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.KTKT.Application;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Coding.CodingUtils.BinaryUtils;
import org.KTKT.Coding.Errors.ErrorPosition;
import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import org.fxmisc.richtext.StyleClassedTextArea;

public class SendVectorPage implements Initializable {

    boolean userInputValid = false;
    boolean channelInputValid = false;

    private String userInput;

    @FXML
    private VBox textAreaContainer;

    private StyleClassedTextArea channelOutput;

    @FXML
    private Circle decodeC;

    @FXML
    private Label decodeLabel;

    @FXML
    private TextFlow decodedVector;

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
    private TableView<ErrorPosition> errorPositions;

    @FXML
    private TableColumn<ErrorPosition, Integer> errors;

    @FXML
    private Label errorCount;

    /**
     * Change the probability
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


    private static String myDecodedVector;
    /**
     * Decode the vector and display it
     * @param event
     */
    @FXML
    void decodeVector(MouseEvent event) {
        if (!channelInputValid){
            return;
        }
        myDecodedVector = "";
        int[] binaryVector = BinaryUtils.convertBinaryStringToIntArray(channelOutput.getText());
        int [] decoded = CodingManager.getInstance().decodeMessage(binaryVector);
        myDecodedVector = BinaryUtils.convertIntArrayToBinaryString(decoded);
        decodedVector.getChildren().clear();
        for (int i = 0; i < myDecodedVector.length(); i++) {
            Text text = new Text(String.valueOf(myDecodedVector.charAt(i)));
            if (userInput.charAt(i) != myDecodedVector.charAt(i)){
                text.setStyle("-fx-fill: red");
            } else {
                text.setStyle("-fx-fill: green");
            }
            decodedVector.getChildren().add(text);
        }

        Text text = new Text("   ");
        decodedVector.getChildren().add(text);
    }

    /**
     * Slider change
     * @param event
     */
    @FXML
    void probabilitySlider(MouseEvent event) {
        probInputField.setText(String.valueOf(probabilitySlider.getValue()));
    }

    /**
     * Send vector button click
     * Send the vector to the channel
     * Add noise to the vector
     * Display the noisy vector
     * @param event
     */
    @FXML
    void sendVector(MouseEvent event) {
        if (!userInputValid){
            return;
        }
        int[] binaryVector = BinaryUtils.convertBinaryStringToIntArray(textField.getText());
        int [] encoded = CodingManager.getInstance().encodeMessage(binaryVector);
        String converted = BinaryUtils.convertIntArrayToBinaryString(encoded);
        encodedVector.setText(converted);

        int [] sendToChannel = CodingManager.getInstance().sendBinaryMessageToChannel(encoded, (float) probabilitySlider.getValue());
        String noisyVector = BinaryUtils.convertIntArrayToBinaryString(sendToChannel);
        channelOutput.clear();
        for (int i = 0; i < noisyVector.length(); i++) {
            if (converted.charAt(i) != noisyVector.charAt(i)){

                channelOutput.append(Character.toString(noisyVector.charAt(i)), "red-text");
            } else {
                channelOutput.append(Character.toString(noisyVector.charAt(i)), "green-text");
            }
        }


        channelInputValid = true;
        decodeLabel.setText(ErrorConstants.VALID);
        decodeC.setStyle("-fx-fill: green");
        decodedVector.getChildren().clear();
        myDecodedVector = "";

        // save the current user input for later analysis
        userInput = textField.getText();


        // find all the error positions and save these positions to table
        var errors = FindErrors(converted, noisyVector);
        errorPositions.getItems().clear();
        errorPositions.getItems().addAll(errors);

        errorCount.setText("Klaidos: " + errors.size());
    }

    private List<ErrorPosition> FindErrors(String decoded, String noisyVector) {
        List<ErrorPosition> errorPositions = new ArrayList<ErrorPosition>();

        for (int i = 0; i < decoded.length(); i++) {
            if (decoded.charAt(i) != noisyVector.charAt(i)) {
                errorPositions.add(new ErrorPosition(i+1));
            }
        }

        return errorPositions;
    }

    /**
     * Get user input
     * @param event
     */
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
        sendLabel.setText(ErrorConstants.VALID);
        sendC.setStyle("-fx-fill: green");
    }

    /**
     * Get the user input from channel output editor
     * @param event
     */
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
        decodeLabel.setText(ErrorConstants.VALID);
        decodeC.setStyle("-fx-fill: green");

        if (!Objects.equals(encodedVector.getText(), "")){
            var errors = FindErrors(encodedVector.getText(), channelOutput.getText());
            errorPositions.getItems().clear();
            errorPositions.getItems().addAll(errors);

            errorCount.setText(ErrorConstants.ERROR + errors.size());
        }

        String noisyVector = encodedVector.getText();
        String converted = channelOutput.getText();
        for (int i = 0; i < noisyVector.length(); i++) {
            if (converted.charAt(i) != noisyVector.charAt(i)){

                channelOutput.setStyle(i, i + 1, List.of("red-text"));
            } else {
                channelOutput.setStyle(i, i + 1, List.of("green-text"));
            }
        }
    }

    /**
     * Initialize the page
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLength.setText("k: " + (DataManager.getInstance().getRows_k()));
        encodedLenght.setText("n: " + (DataManager.getInstance().getColumns_n()));

        errors.setCellValueFactory(new PropertyValueFactory<ErrorPosition, Integer>("position"));

        channelOutput = new StyleClassedTextArea();
        channelOutput.setPrefWidth(200);
        channelOutput.setPrefHeight(157);

        channelOutput.setWrapText(true);

        channelOutput.getStylesheets().add(Objects.requireNonNull(Application.class.getResource("style.css")).toExternalForm());
        channelOutput.setOnKeyTyped(this::channelTextType);

        textAreaContainer.getChildren().add(channelOutput);
    }

    @FXML
    void saveDecoded(MouseEvent event) {
        StringSelection stringSelection = new StringSelection(myDecodedVector);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void saveEncoded(MouseEvent event) {
        StringSelection stringSelection = new StringSelection(encodedVector.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
