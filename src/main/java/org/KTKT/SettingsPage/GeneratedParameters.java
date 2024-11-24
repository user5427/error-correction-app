package org.KTKT.SettingsPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.DataValidator;
import org.KTKT.Data.Matrix.Matrix;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GeneratedParameters implements Initializable {

    @FXML
    private TableView<CosetSyndromWeight> Closet_Table;

    @FXML
    private GridPane H_Grid;

    @FXML
    private Label statusLabel;

    @FXML
    private TableColumn<CosetSyndromWeight, String> SyndromColumn;

    @FXML
    private TableColumn<CosetSyndromWeight, String> WeightColumn;

    @FXML
    private TableColumn<CosetSyndromWeight, String> clossetColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Matrix H_Matrix = DataManager.getInstance().getH_matrix();
            updateGrid(H_Matrix);
            List<CosetSyndromWeight> cosets = DataManager.getInstance().getCosetSyndromWeights();
            SyndromColumn.setCellValueFactory(new PropertyValueFactory<CosetSyndromWeight, String>("stringSyndrom"));
            WeightColumn.setCellValueFactory(new PropertyValueFactory<CosetSyndromWeight, String>("stringWeight"));
            clossetColumn.setCellValueFactory(new PropertyValueFactory<CosetSyndromWeight, String>("stringCosset"));
            Closet_Table.getItems().clear();
            Closet_Table.getItems().addAll(cosets);
        } catch (Exception e) {
//            throw e;
            statusLabel.setText(ErrorConstants.ERROR + e.getMessage());
        }
    }

    /**
     * Updates grid
     * Show H matrix
     * @param H_Matrix
     */
    private void updateGrid(Matrix H_Matrix) {
        H_Grid.getChildren().clear();
        H_Grid.setHgap(5);
        H_Grid.setVgap(2);

        H_Grid.getRowConstraints().clear();
        H_Grid.getColumnConstraints().clear();

        for (int i = 0; i < H_Matrix.getRows(); i++) {
            for (int j = 0; j < H_Matrix.getColumns(); j++) {
                Text text = new Text(String.valueOf(H_Matrix.get(i, j)));
                text.setStyle("-fx-font-size: 12px;");
                H_Grid.add(text, j, i);
            }
        }
    }
}