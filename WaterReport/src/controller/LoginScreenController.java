package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.login.*;
import fxapp.Main;
import javafx.fxml.FXML;

public class LoginScreenController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField _password;

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
            public void handle(ActionEvent event) {
                String subject = username.getText();
                String password = _password.getText();
                if (subject == null || password == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Username and Password cannot be empty", ButtonType.CLOSE);
                }

                if (Login.login(subject, password)) {
                    setMainApp(mainApp);
//                    Alert alert  = new Alert(Alert.AlertType.INFORMATION, "Success!", ButtonType.CLOSE);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Login failed.", ButtonType.CLOSE);
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
