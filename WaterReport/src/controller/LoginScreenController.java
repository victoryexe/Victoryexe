package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.login.*;
import fxapp.Main;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginScreenController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    /** a link back to Main */
    private Main mainApp;

    /**
     * allows for calling back to the main code if necessary
     * @param main  the reference to the FX Application instance
     */
    public void setMainApp(Main main) { mainApp = main; }

    @FXML
    private void initialize() {

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Stage stage;
                Parent root;
                String subject = username.getText();
                String pass = password.getText();
                if (subject.equals("") || pass.equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Username and Password cannot be empty", ButtonType.CLOSE);
                    alert.show();
                }

                if (Login.login(subject, pass)) {
                    stage = (Stage) loginButton.getScene().getWindow();
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/MainScreenView.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Login failed.", ButtonType.CLOSE);
                    alert.show();
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