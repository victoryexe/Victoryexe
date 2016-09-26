package controller;

import fxapp.Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for the root/main window
 *
 */
public class MainController {

    @FXML
    private Button Logout;

    /** reference back to mainApplication if needed */
    private Main mainApp;

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     * */
    public void setMainApp(Main main) {
        mainApp = main;
    }

    @FXML
    private void initialize() {

        Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage;
                Parent root;
                stage = (Stage) Logout.getScene().getWindow();
                try {
                    root = FXMLLoader.load(getClass().getResource("../view/LoginScreenView.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
}
