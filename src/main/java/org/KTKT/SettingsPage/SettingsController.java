package org.KTKT.SettingsPage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.KTKT.Constants.Constants;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;
import org.KTKT.Data.Matrix.Matrix;
import org.KTKT.Utils.WindowManager.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsController {
    private int rows_k = 0, columns_n = 0;
    private double probability_p = 0;
    private boolean k_error = true, n_error = true, p_error = true;
    private boolean matrix_error = true;
    private List<TextField> invalidTextFields = new ArrayList<>();
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
    private Circle matrixStatus;

    @FXML
    private Label matrixStatusLabel;

    @FXML
    void generate_from_previous(MouseEvent event) {
        MatrixInputErrorLabel.setText("");
        if (checkIfGridValid() && checkIfMatrixExists()) {
            Matrix matrixFromGri = getMatrixFromGrid();
            DataManager.getInstance().setPreviousG_matrix(matrixFromGri);
        } else {
            MatrixInputErrorLabel.setText(DataValidator.ERROR + DataValidator.MATRIX_INVALID);
        }

        updateMatrix(MatrixUpdateStatus.PREVIOUS, event);
    }

    @FXML
    void generate_random_G_matrix(MouseEvent event) {
        updateMatrix(MatrixUpdateStatus.RANDOM, event);
    }

    @FXML
    void createMatrix(MouseEvent event) {
        updateMatrix(MatrixUpdateStatus.CLEAN, event);
    }

    private void updateMatrix(MatrixUpdateStatus matrixUpdateStatus, MouseEvent event){
        try {
            DataValidator.ValidateRowsColumnsCount(rows_k, columns_n);
            if (checkIfMatrixChanged() && checkIfMatrixExists() && matrixUpdateStatus != MatrixUpdateStatus.PREVIOUS){
                CancelNewMatrix window = new CancelNewMatrix(this, matrixUpdateStatus);
                WindowManager.openAlertWindow(event, Constants.CHANGE_MATRIX_FXML, window);
            } else if (checkIfMatrixChanged() && checkIfMatrixExists() && matrixUpdateStatus == MatrixUpdateStatus.PREVIOUS){
                CancelNewMatrix window = new CancelNewMatrix(this, matrixUpdateStatus);
                WindowManager.openAlertWindow(event, Constants.CREATE_FROM_PREVIOUS_FXML, window);
            }
            else {
                generateMatrix(matrixUpdateStatus);
            }
        } catch (Exception e){
            MatrixInputErrorLabel.setText(DataValidator.ERROR + e.getMessage());
        }
    }

    private boolean checkIfMatrixExists(){
        return DataManager.getInstance().getG_matrix() != null;
    }

    private boolean checkIfMatrixDimmensionsValid(){
        if (!checkIfMatrixExists())
            return false;
        return G_Matrix.getChildren().size() == DataManager.getInstance().getRows_k() * DataManager.getInstance().getColumns_n();
    }

    private boolean checkIfGridValid(){
        if (!checkIfMatrixDimmensionsValid()){
            return false;
        }
        for (int i = 0; i < DataManager.getInstance().getRows_k(); i++) {
            for (int j = DataManager.getInstance().getRows_k(); j < DataManager.getInstance().getColumns_n(); j++) {
                    try {
                        TextField textField = (TextField) G_Matrix.getChildren().get(i * DataManager.getInstance().getColumns_n() + j);
                        // check if the text field is number
                        Integer.parseInt(textField.getText());
                    } catch (NumberFormatException e){
                        return false;
                    }
                }
        }
        return true;
    }

    private boolean checkIfMatrixChanged() throws NumberFormatException{
        if (!checkIfGridValid()){
            return true;
        }
        Matrix matrixFromGrid = getMatrixFromGrid();
        if (matrixFromGrid != null && DataManager.getInstance().getG_matrix() != null){
            return !matrixFromGrid.equals(DataManager.getInstance().getG_matrix());
        }

        return true;
    }

    private Matrix getMatrixFromGrid() throws NumberFormatException {
        Matrix matrix = new Matrix(DataManager.getInstance().getRows_k(), DataManager.getInstance().getColumns_n());
        if (G_Matrix.getChildren().size() != DataManager.getInstance().getRows_k() * DataManager.getInstance().getColumns_n()){
            return null;
        }
        for (int i = 0; i < DataManager.getInstance().getRows_k(); i++) {
            for (int j = 0; j < DataManager.getInstance().getColumns_n(); j++) {
                if (j < DataManager.getInstance().getRows_k()){
                    matrix.set(i, j, DataManager.getInstance().getG_matrix().get(i, j));
                } else {
                    try {
                        TextField textField = (TextField) G_Matrix.getChildren().get(i * DataManager.getInstance().getColumns_n() + j);
                        matrix.set(i, j, Integer.parseInt(textField.getText()));
                    } catch (NumberFormatException e){
                        throw new NumberFormatException("Invalid matrix value");
                    }
                }
            }
        }
        return matrix;
    }

    public void generateMatrix(MatrixUpdateStatus matrixUpdateStatus){
        if (matrixUpdateStatus == MatrixUpdateStatus.CANCEL){
            return;
        } else if (matrixUpdateStatus == MatrixUpdateStatus.CLEAN){
            generateCleanMatrix();
        } else if (matrixUpdateStatus == MatrixUpdateStatus.RANDOM){
            generateRandomMatrix();
        } else if (matrixUpdateStatus == MatrixUpdateStatus.PREVIOUS){
            generatePreviousMatrix();
        }
    }

    private void generatePreviousMatrix() {
        // take as much data from previous matrix as possible
        try {
            MatrixInputErrorLabel.setText("");
            DataManager.getInstance().generateFromPreviousG_Matrix();
            updateGrid();
        } catch (IllegalArgumentException e) {
            MatrixInputErrorLabel.setText("Error: " + e.getMessage());
        }
    }

    private void generateRandomMatrix() {
        try {
            MatrixInputErrorLabel.setText("");
            DataManager.getInstance().generateRandomG_matrix();
            updateGrid();
        } catch (IllegalArgumentException e) {
            MatrixInputErrorLabel.setText("Error: " + e.getMessage());
        }
    }

    private void generateCleanMatrix() {
        try{
            MatrixInputErrorLabel.setText("");
            DataManager.getInstance().generateCleanG_matrix();
            updateGrid();
        } catch (IllegalArgumentException e) {
            MatrixInputErrorLabel.setText("Error: " + e.getMessage());
        }
    }

    private void updateGrid(){
        G_Matrix.getChildren().clear();
        G_Matrix.setHgap(5);
        G_Matrix.setVgap(2);

        G_Matrix.getRowConstraints().clear();
        G_Matrix.getColumnConstraints().clear();
        invalidTextFields.clear();

        matrixStatus.setFill(Color.YELLOW);
        matrixStatusLabel.setText(DataValidator.VALID);

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
                    // add event listener
                    textField.textProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            int num = Integer.parseInt(newValue);
                            if (num != 0 && num != 1){
                                throw new NumberFormatException(DataValidator.INVALID_NUMBER);
                            }

                            textField.setStyle("-fx-text-fill: black;");
                            invalidTextFields.remove(textField);
                            if (invalidTextFields.isEmpty()){
                                matrixStatus.setFill(Color.YELLOW);
                                matrixStatusLabel.setText(DataValidator.VALID);
                            }
                        } catch (NumberFormatException e){
                            textField.setStyle("-fx-text-fill: red;");
                            if (!invalidTextFields.contains(textField)){
                                invalidTextFields.remove(textField);
                            }
                            matrixStatus.setFill(Color.RED);
                            matrixStatusLabel.setText(DataValidator.MATRIX_INVALID);
                        }
                    });

                    G_Matrix.add(textField, j, i);
                }
            }
        }
    }

    @FXML
    void k_changed(KeyEvent event) {
        try {
            numberInputErrorLabel.setText("");
            DataValidator.ValidateRows(k_input.getText());
            rows_k = Integer.parseInt(k_input.getText());
            if (!Objects.equals(n_input.getText(), "")){
                DataValidator.ValidateRowsColumnsCount(rows_k, columns_n);
                setNValidation(true);
            }
            setKValidation(true);
        } catch (Exception e) {
            numberInputErrorLabel.setText("Error: " + e.getMessage());
            setKValidation(false);
        }
    }

    @FXML
    void n_changed(KeyEvent event) {
        try {
            numberInputErrorLabel.setText("");
            DataValidator.ValidateColumns(n_input.getText());
            columns_n = Integer.parseInt(n_input.getText());
            if (!Objects.equals(k_input.getText(), "")){
                DataValidator.ValidateRowsColumnsCount(rows_k, columns_n);
                setKValidation(true);
            }
            setNValidation(true);
        } catch (Exception e) {
            numberInputErrorLabel.setText("Error: " + e.getMessage());
            setNValidation(false);
        }
    }

    private void setKValidation(boolean valid) {
        this.k_error = !valid;
        if (k_error) {
            kStatus.setFill(Color.RED);
            kStatusLabel.setText("Invalid");
        } else {
            kStatus.setFill(Color.YELLOW);
            kStatusLabel.setText("Valid");
        }
    }

    private void setNValidation(boolean valid) {
        this.n_error = !valid;
        if (n_error) {
            nStatus.setFill(Color.RED);
            nStatusLabel.setText("Invalid");
        } else {
            nStatus.setFill(Color.YELLOW);
            nStatusLabel.setText("Valid");
        }
    }

    @FXML
    void p_changed(KeyEvent event) {
        try {
            numberInputErrorLabel.setText("");
            DataValidator.ValidateProbability(p_input.getText());
            probability_p = Double.parseDouble(p_input.getText());
            setPValidation(true);
        } catch (Exception e) {
            numberInputErrorLabel.setText("Error: " + e.getMessage());
            setPValidation(false);
        }
    }

    private void setPValidation(boolean valid) {
        this.p_error = !valid;
        if (p_error) {
            pStatus.setFill(Color.RED);
            pStatusLabel.setText("Invalid");
        } else {
            pStatus.setFill(Color.YELLOW);
            pStatusLabel.setText("Valid");
        }
    }

    @FXML
    void save_settings(MouseEvent event) {

    }

    @FXML
    void saveVariables(MouseEvent event) {
        StringBuilder error = new StringBuilder();
        try {
            numberInputErrorLabel.setText("");
            if (k_error && n_error){
                throw new NumberFormatException(DataValidator.K_N_INVALID);
            }
            if (k_error){
                throw new NumberFormatException(DataValidator.K_INVALID);
            }
            if (n_error){
                throw new NumberFormatException(DataValidator.N_INVALID);
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
                throw new NumberFormatException(DataValidator.PROBABILITY_INVALID);
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
        MySaveMatrix();
    }

    boolean MySaveMatrix(){
        try {
            MatrixInputErrorLabel.setText("");
            if (!checkIfMatrixExists()){
                throw new IllegalArgumentException(DataValidator.MATRIX_NOT_CREATED);
            }

            if (!checkIfMatrixDimmensionsValid()){
                throw new IllegalArgumentException(DataValidator.MATRIX_DIMENSIONS_INVALID);
            }

            if (checkIfGridValid()){
                Matrix matrix = getMatrixFromGrid();
                DataManager.getInstance().setG_matrix(getMatrixFromGrid());
                setMatrixValidation(true);
            } else {
                throw new IllegalArgumentException(DataValidator.MATRIX_INVALID);
            }
            return true;
        } catch (Exception e){
            MatrixInputErrorLabel.setText("Error: " + e.getMessage());
            setMatrixValidation(false);
        }
        return false;
    }



    private void setMatrixValidation(boolean valid){
        this.matrix_error = !valid;
        if (matrix_error){
            matrixStatus.setFill(Color.RED);
            matrixStatusLabel.setText("Invalid");
        } else {
            matrixStatus.setFill(Color.GREEN);
            matrixStatusLabel.setText("Saved");
        }
    }
}
