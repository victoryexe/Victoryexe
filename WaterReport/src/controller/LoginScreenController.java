package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users.Account;
import model.Users.User;
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

    @FXML
    private Hyperlink register;

    public static User currUser;

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
                            "Username and Password fields cannot be empty", ButtonType.CLOSE);
                    alert.show();
                } else if (Login.login(subject, pass)) {
                    currUser = (User) UserList.getUserAccount(subject);
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

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage;
                Parent root;
                stage = (Stage) register.getScene().getWindow();
                try {
                    root = FXMLLoader.load(getClass().getResource("../view/Registration.fxml"));
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