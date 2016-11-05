package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users.Account;
import model.login.*;
import fxapp.Main;
import javafx.fxml.FXML;

public class LoginScreenController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink register;

    public static Account currUser;

    /** a link back to Main */
    private Main mainApp;

    /**
     * allows for calling back to the main code if necessary
     * @param main  the reference to the FX Application instance
     */
    public void setMainApp(Main main) { mainApp = main; }

    @FXML
    private void initialize() {

        loginButton.setOnAction((ActionEvent event) -> {
                String subject = username.getText();
                String pass = password.getText();
                if (subject.equals("") || pass.equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Username and Password fields cannot be empty", ButtonType.CLOSE);
                    alert.show();
                } else if (Login.login(subject, pass)) {
                    currUser = UserList.getUserAccount(subject);
                    mainApp.showMain((Stage) loginButton.getScene().getWindow());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Login failed.", ButtonType.CLOSE);
                    alert.show();
                }
            });

        register.setOnAction((ActionEvent event) ->
                mainApp.showRegistration((Stage) register.getScene().getWindow()));
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
}