package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.login.*;
import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginScreenController {
    @FXML
    private TextField username, _password;

    /** a link back to Main */
    private Main mainApp;

    /**
     * allows for calling back to the main code if necessary
     * @param main  the reference to the FX Application instance
     */
    public void setMainApp(Main main) { mainApp = main; }

    /**
     * Logs in the user
     * If successful, user is redirected to main page.
     */
    @FXML
    public void login() {
        // TODO on button click...
        String subject = username.getText();
        String password = _password.getText();
        if (subject == null || password == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Username and Password cannot be empty", ButtonType.CLOSE);
        }

        if (Login.login(subject, password)) {
            // TODO navigate to welcome page
            Alert alert  = new Alert(Alert.AlertType.INFORMATION, "Success!", ButtonType.CLOSE);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Login failed.", ButtonType.CLOSE);
        }
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
}
