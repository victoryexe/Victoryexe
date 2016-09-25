package controller;

import model.login.*;
import lib.PasswordStorage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import fxapp.Main;
import javafx.fxml.FXML;

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
    private void initialize() throws PasswordStorage.CannotPerformOperationException,
            PasswordStorage.InvalidHashException {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String subject = username.getText();
                    String pass = password.getText();
                    if (subject == null || password == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Username and Password cannot be empty", ButtonType.CLOSE);
                    }

                    if (Login.login(subject, pass)) {
                        setMainApp(mainApp);
//                    Alert alert  = new Alert(Alert.AlertType.INFORMATION, "Success!", ButtonType.CLOSE);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Login failed.", ButtonType.CLOSE);
                    }
                } catch (Exception e) {
                    // TODO plz no
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
