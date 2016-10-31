package controller;

import fxapp.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users.*;
import model.login.UserList;
import model.registration.Registration;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by grizz on 9/27/2016.
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
    private ComboBox authLevels;


    private String first;
    private String last;
    private String mail;
    private String pass;
    private String pass2;
    private String name;
    private AuthLevel authLevel;
    private Main mainApp;

    public void setMainApp(Main main) {
        mainApp = main;
    }

    @FXML
    public void initialize() {
        ArrayList<AuthLevel> auth = new ArrayList<>();
        for (AuthLevel a : AuthLevel.values()) {
            auth.add(a);
        }
        MainController.populateComboBox(authLevels, auth);
        regCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.showLogin((Stage) regCancel.getScene().getWindow());
            }
        });

        regSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                first = firstName.getText();
                last = lastName.getText();
                mail = email.getText();
                pass = passwordSet.getText();
                pass2 = passwordConfirm.getText();
                authLevel = (AuthLevel) authLevels.getValue();
                name = first + " " + last;
                Account user;

                if (isInputValid(first, last, mail, pass, pass2)) {
                    Registration.createAccount(first, last, mail, pass, pass2, authLevel);
                    user = UserList.getUserAccount(mail);
                    LoginScreenController.currUser = user;
                    mainApp.showMain((Stage) regSubmit.getScene().getWindow());
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
    public static boolean isInputValid(String firstName, String lastName, String userid,
                                       String password1, String password2) {
        if (firstName.equals("") || lastName.equals("")|| userid.equals("")
                || password1.equals("")|| password2.equals("")) { // null checks
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "All fields must be filled in.", ButtonType.CLOSE);
            alert.show();
            return false;
        } else if (UserList.containsUserID(userid) || !userid.contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Must enter valid email address", ButtonType.CLOSE);
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
