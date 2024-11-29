package org.KTKT.Utils.WindowManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.KTKT.Application;

import java.io.IOException;

public class WindowManager {
    /**
     * Get a new stage.
     * @param resource
     * @param controller
     * @return
     * @throws IOException
     */
    public static Stage getStage(String resource, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(resource));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        newStage.setTitle("Informacijos redagavimas.");
        newStage.setScene(scene);
        newStage.setResizable(false);
        return newStage;
    }

//    public static void openOverlayWindowWithJoinedCloseAction(MouseEvent event, String resource, Object controller) throws IOException {
//        Stage stage = getStage(resource, controller);
//
//        // Get the current stage
//        Node source = (Node) event.getSource();
//        Stage currentStage = (Stage) source.getScene().getWindow();
//
//        // Set the new stage as modal and set its owner to the current stage
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initOwner(currentStage);
//
//        stage.show();
//
//        stage.setOnCloseRequest(e -> {
//            currentStage.close();
//        });
//
//        currentStage.setOnCloseRequest(e -> {
//            stage.close();
//        });
//    }

    /**
     * Open a new window. Hide the current window.
     * @param event
     * @param resource
     * @param controller
     * @throws IOException
     */
    public static void openWindow(MouseEvent event, String resource, Object controller) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.hide(); // hide the current window

        Stage newStage = getStage(resource, controller);
        newStage.show();

        newStage.setOnCloseRequest(e -> {
            System.out.println("Closing window and showing main");
            stage.show();
        });
    }

    public static void openOverlayWindow(MouseEvent event, String resource, Object controller) throws IOException {
        Stage stage = getStage(resource, controller);

        // Get the current stage
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();

        // Set the new stage as modal and set its owner to the current stage
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);

        stage.show();
    }

    public static void openOverlayWindow(Node node, String resource, Object controller) throws IOException {
        Stage stage = getStage(resource, controller);

        // Get the current stage
        Stage currentStage = (Stage) node.getScene().getWindow();

        // Set the new stage as modal and set its owner to the current stage
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentStage);

        stage.show();
    }

    public static void openAlertWindow(MouseEvent event, String resource, Object controller) throws IOException {
        Stage stage = getStage(resource, controller);

        // Get the current stage
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();

        // Set the new stage as modal and set its owner to the current stage
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(currentStage);

        stage.show();
    }
}
