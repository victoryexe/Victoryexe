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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.Users.Profile;

import java.io.IOException;

/**
 * The controller for the root/main window
 *
 */
public class MainController {

    @FXML
    private Button Logout;
    @FXML
    private TextField nametextbox;
    @FXML
    private TextField salutationtextbox;
    @FXML
    private TextField useridtextbox;
    @FXML
    private TextField emailtextbox;
    @FXML
    private TextField homeaddresstextbox;
    @FXML
    private TextField authorizationleveltextbox;
    @FXML
    private Button salutationedit;
    @FXML
    private Button submit;

    private Profile currProfile;

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
                    LoginScreenController.currUser = null;
                    root = FXMLLoader.load(getClass().getResource("../view/LoginScreenView.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        submit.setVisible(false);
        currProfile = new Profile(LoginScreenController.currUser);
        nametextbox.setEditable(false);
        nametextbox.setText(currProfile.getName());
        salutationtextbox.setEditable(false);
        salutationtextbox.setText(currProfile.getTitle());
        useridtextbox.setEditable(false);
        useridtextbox.setText("" + LoginScreenController.currUser.getUserID());
        emailtextbox.setEditable(false);
        emailtextbox.setText(currProfile.getEmail());
        homeaddresstextbox.setEditable(false);
        homeaddresstextbox.setText(currProfile.getAddress());
        authorizationleveltextbox.setEditable(false);
        authorizationleveltextbox.setText(LoginScreenController.currUser.getAuthLevel().name());

        salutationedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                salutationedit.setVisible(false);
                submit.setVisible(true);
                nametextbox.setEditable(true);
                salutationtextbox.setEditable(true);
                emailtextbox.setEditable(true);
                homeaddresstextbox.setEditable(true);
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                submit.setVisible(false);
                salutationedit.setVisible(true);
                currProfile.changeAddress(homeaddresstextbox.getText());
                homeaddresstextbox.setEditable(false);
                currProfile.changeTitle(salutationtextbox.getText());
                salutationtextbox.setEditable(false);
                if (!currProfile.getEmail().equals(emailtextbox.getText())) {

                }
            }
        });

    }

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
        if (firstName == "" || lastName == "" || userid == ""
                || password1 == "" || password2 == "") { // null checks
            return false;
        }

        if (userMap.containsKey(userid) || !userid.contains("@")) {
            return false;
        }

        if (!password1.equals(password2)) { // check that passwords match
            return false;
        }

        return true;
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
}
