package org.KTKT.CodingPages;

import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import org.KTKT.Coding.CodingManager;
import org.KTKT.Coding.ESDResultRecords.ImageESDResult;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
    private Label time;

    /**
     * Choose image from file system
     * @param event
     */
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

    /**
     * Validate probability input
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
     * Update probability slider
     * @param event
     */
    @FXML
    void probabilitySlider(MouseEvent event) {
        probInputField.setText(String.valueOf(probabilitySlider.getValue()));
    }

    /**
     * Send image to ESD
     * @param event
     */
    @FXML
    void sendImage(MouseEvent event) {
        if (selectedFile == null) {
            sendLabel.setText("Prašau pasirinkti nuotrauką.");
            sendC.setStyle("-fx-fill: red");
            return;
        }

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
            CodingManager.getInstance().ESDImage(selectedFile, (float) probabilitySlider.getValue(), this);
        } catch (Exception e) {
            sendLabel.setText(e.getMessage());
            sendC.setStyle("-fx-fill: red");
            return;
        }

        sendLabel.setText("Siunčiama nuotrauka.");
        sendC.setStyle("-fx-fill: yellow");


    }

    /**
     * Convert BufferedImage to Image
     * @param image
     * @return
     */
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


    BufferedImage noCode;
    BufferedImage decImage;
    /**
     * Receive result from ESD
     * @param res
     */
    public void  receiveResult(ImageESDResult res) {
        Platform.runLater(() -> {
            Image decImage = convertToFxImage(res.decodedImage());
            decodedImage.setImage(decImage);
            this.decImage = res.decodedImage();
            Image noCode = convertToFxImage(res.noCodeImage());
            noCodeImage.setImage(noCode);
            this.noCode = res.noCodeImage();

            sendLabel.setText("Nuotrauka išsiųsta.");
            sendC.setStyle("-fx-fill: green");
        });
    }

    /**
     * Update ESD status
     * @param status
     */
    @Override
    public void setESDStatus(Double status, Long time) {
        Platform.runLater(() -> updateProgressBar(status, time));
    }

    /**
     * Update progress bar
     * @param status
     */
    private void updateProgressBar(Double status, Long time) {
        progressBar.setProgress(status);
        DecimalFormat df = new DecimalFormat("0.00");
        this.time.setText("Trukmė: " + df.format(time / 1000.0) + "s");
    }

    /**
     * Initialize the controller
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

    }


    @FXML
    void saveDecoded(MouseEvent event) throws IOException {
        if (decImage == null) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Decoded Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        File file = fileChooser.showSaveDialog(currentStage);
        ImageIO.write(decImage, "png", file);
    }

    @FXML
    void saveNoCode(MouseEvent event) {
        if (noCode == null) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save No Code Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        File file = fileChooser.showSaveDialog(currentStage);
        try {
            ImageIO.write(noCode, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
