package org.KTKT.SettingsPage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;

import java.util.Objects;

public class SettingsController {
    int rows_k = 0, columns_n = 0;
    double probability_p = 0;
    boolean k_error = true, n_error = true, p_error = true;

    @FXML
    private Label MatrixInputErrorLabel;

    @FXML
    private Label numberInputErrorLabel;

    @FXML
    private Label saveErrorLabel;

    @FXML
    private GridPane G_Matrix;

    @FXML
    private TextField k_input;

    @FXML
    private TextField n_input;

    @FXML
    private TextField p_input;

    @FXML
    private Circle kStatus;

    @FXML
    private Label kStatusLabel;

    @FXML
    private Circle nStatus;

    @FXML
    private Label nStatusLabel;

    @FXML
    private Circle pStatus;

    @FXML
    private Label pStatusLabel;


    @FXML
    void generate_random_G_matrix(MouseEvent event) {
        try {
            MatrixInputErrorLabel.setText("");
            DataManager.getInstance().generateG_matrix();
            G_Matrix.getChildren().clear();
            G_Matrix.setHgap(5);
            G_Matrix.setVgap(2);

            G_Matrix.getRowConstraints().clear();
            G_Matrix.getColumnConstraints().clear();



            for (int i = 0; i < DataManager.getInstance().getRows_k(); i++) {
                for (int j = 0; j < DataManager.getInstance().getColumns_n(); j++) {
                    if (j < DataManager.getInstance().getRows_k()){
                        Text text = new Text(String.valueOf(DataManager.getInstance().getG_matrix().get(i, j)));
                        text.setStyle("-fx-font-size: 12px;");
                        G_Matrix.add(text, j, i);
                    } else {
                        TextField textField = new TextField();
                        textField.setPrefSize(50, 50);
                        textField.setText(String.valueOf(DataManager.getInstance().getG_matrix().get(i, j)));
                        G_Matrix.add(textField, j, i);
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            MatrixInputErrorLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    void k_changed(KeyEvent event) {
        validateK();
    }

    private void validateK(){
        try {
            numberInputErrorLabel.setText("");
            k_error = true;
            if (Objects.equals(k_input.getText(), "")){
                throw new NumberFormatException("Empty k input");
            }
            rows_k = Integer.parseInt(k_input.getText());
            DataValidator.ValidateRows(rows_k);
            if (!Objects.equals(n_input.getText(), "")){
                DataValidator.ValidateRowsColumnsCount(rows_k, columns_n);
            }
            kStatus.setFill(Color.YELLOW);
            kStatusLabel.setText("Valid");
            k_error = false;
        } catch (Exception e) {
            numberInputErrorLabel.setText("Error: " + e.getMessage());
            kStatus.setFill(Color.RED);
            kStatusLabel.setText("Invalid");
        }
    }

    @FXML
    void n_changed(KeyEvent event) {
        validateN();
    }

    private void validateN(){
        try {
            numberInputErrorLabel.setText("");
            n_error = true;
            if (Objects.equals(n_input.getText(), "")){
                throw new NumberFormatException("Empty n input");
            }
            columns_n = Integer.parseInt(n_input.getText());
            DataValidator.ValidateColumns(columns_n);
            if (!Objects.equals(k_input.getText(), "")){
                DataValidator.ValidateRowsColumnsCount(rows_k, columns_n);
            }
            nStatus.setFill(Color.YELLOW);
            nStatusLabel.setText("Valid");
            n_error = false;
        } catch (Exception e) {
            numberInputErrorLabel.setText("Error: " + e.getMessage());
            nStatus.setFill(Color.RED);
            nStatusLabel.setText("Invalid");
        }
    }

    @FXML
    void p_changed(KeyEvent event) {
        try {
            numberInputErrorLabel.setText("");
            p_error = true;
            if (Objects.equals(p_input.getText(), "")){
                throw new NumberFormatException("Empty p input");
            }
            probability_p = Double.parseDouble(p_input.getText());
            DataValidator.ValidateProbability(probability_p);
            pStatus.setFill(Color.YELLOW);
            pStatusLabel.setText("Valid");
            p_error = false;
        } catch (Exception e) {
            numberInputErrorLabel.setText("Error: " + e.getMessage());
            pStatus.setFill(Color.RED);
            pStatusLabel.setText("Invalid");
        }
    }

    @FXML
    void save_settings(MouseEvent event) {

    }

    @FXML
    void saveVariables(MouseEvent event) {
        StringBuilder error = new StringBuilder();
        System.out.println("Saving variables");
        try {
            numberInputErrorLabel.setText("");
            if (k_error && n_error){
                throw new NumberFormatException("k and n are invalid");
            }
            if (k_error){
                throw new NumberFormatException("k is invalid");
            }
            if (n_error){
                throw new NumberFormatException("n is invalid");
            }
            DataManager.getInstance().setRowsColumnsCount(rows_k, columns_n);
            nStatus.setFill(Color.GREEN);
            kStatus.setFill(Color.GREEN);
            nStatusLabel.setText("Saved");
            kStatusLabel.setText("Saved");
        } catch (Exception e) {
            error.append("Error: ").append(e.getMessage()).append(". ");
        }

        try {
            if (p_error){
                throw new NumberFormatException("p is invalid");
            }
            DataManager.getInstance().setProbability(probability_p);
            pStatus.setFill(Color.GREEN);
            pStatusLabel.setText("Saved");
        } catch (Exception e) {
            error.append("Error: ").append(e.getMessage()).append(". ");
        }

        if (error.length() > 0){
            numberInputErrorLabel.setText(error.toString());
        }
    }

    @FXML
    void SaveMatrix(MouseEvent event) {

    }
}
