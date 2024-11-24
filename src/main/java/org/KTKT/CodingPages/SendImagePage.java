package org.KTKT.CodingPages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Coding.ESDResultRecords.ImageESDResult;
import org.KTKT.Data.DataValidator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class SendImagePage implements ESDStatus, Initializable {
    File selectedFile = null;

    @FXML
    private Label devodedLabel;

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
    private ImageView decodedImage;

    @FXML
    private ImageView noCodeImage;

    @FXML
    private ImageView originalImage;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void chooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(currentStage);

        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                originalImage.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

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
    void sendImage(MouseEvent event) {
        if (selectedFile == null) {
            sendLabel.setText("Please select an image.");
            sendC.setStyle("-fx-fill: red");
            return;
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setOnCloseRequest(e -> CodingManager.stopESD());

        try {
            CodingManager.getInstance().ESDImage(selectedFile, (float) probabilitySlider.getValue(), this);
        } catch (Exception e) {
            sendLabel.setText(e.getMessage());
            sendC.setStyle("-fx-fill: red");
            return;
        }

        sendLabel.setText("Sending image.");
        sendC.setStyle("-fx-fill: yellow");


    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public void  receiveResult(ImageESDResult res) {
        Platform.runLater(() -> {
            Image decImage = convertToFxImage(res.decodedImage());
            decodedImage.setImage(decImage);
            Image noCode = convertToFxImage(res.noCodeImage());
            noCodeImage.setImage(noCode);

            sendLabel.setText("Image sent.");
            sendC.setStyle("-fx-fill: green");
        });
    }

    @Override
    public void setESDStatus(Double status) {
        Platform.runLater(() -> updateProgressBar(status));
    }

    private void updateProgressBar(Double status) {
        progressBar.setProgress(status);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
