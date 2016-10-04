package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    @FXML
    public void initialize() {
        ArrayList<AuthLevel> auth = new ArrayList<>();
        for (AuthLevel a : AuthLevel.values()) {
            auth.add(a);
        }
        authLevels.setItems(javafx.collections.FXCollections.observableList(auth));
        authLevels.setValue(auth.get(0));

        regCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage;
                Parent root;
                stage = (Stage) regCancel.getScene().getWindow();
                try {
                    root = FXMLLoader.load(getClass().getResource("../view/LoginScreenView.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
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

                if (MainController.isInputValid(first, last, mail, pass, pass2)) {
                    Registration.createAccount(first, last, mail, pass, pass2, authLevel);
                    user = UserList.getUserAccount(mail);
                    LoginScreenController.currUser = (User) user;
                    Stage stage;
                    Parent root;
                    stage = (Stage) regSubmit.getScene().getWindow();
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/MainScreenView.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
