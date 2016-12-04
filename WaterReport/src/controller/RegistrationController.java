package controller;

import db.DB;
import fxapp.Main;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import lib.password_hashing.PasswordStorage;
import model.Users.Account;
import model.Users.AuthLevel;
import model.registration.UserList;
import model.registration.Registration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by grizz on 9/27/2016.
 * Handles Registration of new users
 */
public class RegistrationController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private PasswordField passwordSet;
    @FXML
    private PasswordField passwordConfirm;
    @FXML
    private Button regSubmit;
    @FXML
    private Button regCancel;
    @FXML
    private ComboBox<AuthLevel> authLevels;


    private String first;
    private String last;
    private String mail;
    private String pass;
    private String pass2;
    private AuthLevel authLevel;
    private Main mainApp;

    /**
     * sets up a reference back to Main
     * @param main the reference back to main
     */
    public void setMainApp(Main main) {
        mainApp = main;
    }

    @FXML
    public void initialize() {
        List<AuthLevel> auth = new ArrayList<>();
        Collections.addAll(auth, AuthLevel.values());
        MainController.populateComboBox(authLevels, auth);
        regCancel.setOnAction(event -> mainApp.showLogin());

        regSubmit.setOnAction(event -> {
            first = firstName.getText();
            last = lastName.getText();
            mail = email.getText();
            pass = passwordSet.getText();
            pass2 = passwordConfirm.getText();
            authLevel = authLevels.getValue();
            Account user;

            if (isInputValid(first, last, mail, pass, pass2)) {
                try {
                    Registration.createAccount(first, last, mail, pass, pass2, authLevel);
                    user = UserList.getUserAccount(mail);
                    DB.addAccount(user);
                    LoginScreenController.setCurrUser(user);
                    if (authLevel == AuthLevel.ADMIN) {
                        mainApp.showAdmin();
                    } else {
                        mainApp.showMain();
                    }
                } catch (PasswordStorage.CannotPerformOperationException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    //TEST CODE ZONE WOOP WOOP
    /*GeoApiContext context = new GeoApiContext().setApiKey("AIza...");
    GeocodingResult[] results =  GeocodingApi.geocode(context,
            "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
System.out.println(results[0].formattedAddress);*/


    //TEST CODE ZONE WOOOP WOOP


    /**
     * Checks whether the user-provided input is valid
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userid the user's email
     * @param password1 the user's password
     * @param password2 confirm password
     * @return true iff all fields are valid
     */
    private boolean isInputValid(String firstName, String lastName, String userid,
                                       String password1, String password2) {
        if ("".equals(firstName) || "".equals(lastName) || "".equals(userid)
                || "".equals(password1) || "".equals(password2)) { // null checks
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "All fields must be filled in.", ButtonType.CLOSE);
            alert.show();
            return false;
        } else if (!userid.contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Must enter valid email address", ButtonType.CLOSE);
            alert.show();
            return false;
        } else if(UserList.containsUserID(userid)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Username already exists.", ButtonType.CLOSE);
            alert.show();
            return false;
        } else if (!password1.equals(password2)) { // check that passwords match
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Passwords must match.", ButtonType.CLOSE);
            alert.show();
            return false;
        }

        return true;
    }
}
