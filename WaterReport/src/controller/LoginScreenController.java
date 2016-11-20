package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lib.password_hashing.PasswordStorage;
import model.Users.Account;
import fxapp.Main;
import javafx.fxml.FXML;
import model.Users.AuthLevel;
import model.registration.Login;
import model.registration.UserList;

/**
 * Created by grizz on 09/24/2016
 * Handles the processes of logging in to the System
 */
public class LoginScreenController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink register;

    private static Account currUser;

    /** a link back to Main */
    private Main mainApp;

    /**
     * allows for calling back to the main code if necessary
     * @param main  the reference to the FX Application instance
     */
    public void setMainApp(Main main) { mainApp = main; }

    @FXML
    private void initialize() {
        setCurrUser(null);

        loginButton.setOnAction((ActionEvent event) -> {
            String subject = username.getText();
            String pass = password.getText();
            if ("".equals(subject) || "".equals(pass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Username and Password fields cannot be empty", ButtonType.CLOSE);
                alert.show();
            } else {
                try {
                    if (Login.login(subject, pass)) {
                        setCurrUser(UserList.getUserAccount(subject));
                        if (AuthLevel.ADMIN == currUser.getAuthLevel()) {
                            mainApp.showAdmin();
                        } else {
                            mainApp.showMain();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Login failed.", ButtonType.CLOSE);
                        alert.show();
                    }
                } catch (PasswordStorage.CannotPerformOperationException
                        | PasswordStorage.InvalidHashException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        register.setOnAction((ActionEvent event) -> mainApp.showRegistration());
    }

    /**
     * sets the account of the current user
     * @param acc the Account of the current user
     */
    static void setCurrUser(Account acc) {
        currUser = acc;
    }

    /**
     * Returns the Account of the current user of the application
     * @return the Account currently using the application
     */

    static Account getCurrUser() {
        return currUser;
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        //    PersistenceHandler.saveUsers(UserList.getUserList());
        //    Authentication.savePass();
        //    PersistenceHandler.saveWaterReports(ReportsList.getWaterReportsList());
        //    PersistenceHandler.saveQualityReports(ReportsList.getQualityReportsList());
        System.exit(0);

    }
}