package controller;

import fxapp.Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Users.Address;
import model.Users.Profile;
import model.login.Authentication;
import model.login.UserList;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

/**
 * The controller for the root/main window
 *
 */
public class MainController {

    @FXML
    private Button Logout;
    @FXML
    private TextField lastnametextbox;
    @FXML
    private  TextField firstnametextbox;
    @FXML
    private TextField streetaddresstextbox;
    @FXML
    private TextField citytextbox;
    @FXML
    private TextField statetextbox;
    @FXML
    private TextField countrytextbox;
    @FXML
    private TextField aptnumtextbox;
    @FXML
    private TextField zipcodetextbox;
    @FXML
    private TextField emailtextbox;
    @FXML
    private ComboBox salutationcombobox;
    @FXML
    private Button salutationedit;
    @FXML
    private Button submit;
    @FXML
    private Label currsalutation;

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
        /**
         * Sets the function of the Logout button to return user to the
         * Login screen and clears the current user.
         */
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

        /**
         * Forces only decimal numbers to be entered as values in either
         * zipcodetextbox or aptnumtextbox
         */
        DecimalFormat format = new DecimalFormat( "#" );

        zipcodetextbox.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        aptnumtextbox.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        ArrayList<String> salutation = new ArrayList<String>();
        salutation.add("");
        salutation.add("Mr.");
        salutation.add("Ms.");
        salutation.add("Mrs.");
        salutation.add("Dr.");
        salutationcombobox.setItems(javafx.collections.FXCollections.observableList(salutation));
        salutationcombobox.setVisible(false);
        salutationcombobox.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                salutationcombobox.requestFocus();
            }
        });
        currProfile = new Profile(LoginScreenController.currUser);
        if (currProfile.getTitle() != null) {
            currsalutation.setText(currProfile.getTitle());
        }
        submit.setVisible(false);
        setAllEditable(false);
        String name = currProfile.getName();
        firstnametextbox.setText(name.substring(0, name.indexOf(" ")));
        lastnametextbox.setText(name.substring(name.indexOf(" ")));
        emailtextbox.setText(currProfile.getEmail());
        if (currProfile.getAddress() != null) {
            streetaddresstextbox.setText(currProfile.getAddress().getStreet());
            if (currProfile.getAddress().apartmentNumber() != -1) {
                aptnumtextbox.setText("" + currProfile.getAddress().apartmentNumber());
            }
            citytextbox.setText(currProfile.getAddress().getCity());
            statetextbox.setText(currProfile.getAddress().getState());
            countrytextbox.setText(currProfile.getAddress().getCountry());
            if (currProfile.getAddress().getZip() != -1) {
                zipcodetextbox.setText("" + currProfile.getAddress().getZip());
            }
        }
        /**
         * sets the function of the edit button to set all fields editable and replace
         * itself with the submit button
         */
        salutationedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                salutationedit.setVisible(false);
                submit.setVisible(true);
                currsalutation.setVisible(false);
                salutationcombobox.setVisible(true);
                setAllEditable(true);
            }
        });
         /**
         * Sets the function of the submit button to make all fields uneditable
         * and to save any changes made to the profile page
         */
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                submit.setVisible(false);
                salutationedit.setVisible(true);
                currProfile.changeTitle((String) salutationcombobox.getValue());
                currsalutation.setText(currProfile.getTitle());
                currsalutation.setVisible(true);
                salutationcombobox.setVisible(false);
                setAllEditable(false);
                currProfile.changeName(firstnametextbox.getText() + " " + lastnametextbox.getText());
                int zip;
                int apt;
                if (!currProfile.getEmail().equals(emailtextbox.getText())) {
                    UserList.updateMap(currProfile.getEmail(), emailtextbox.getText());
                    Authentication.updateEmail(currProfile.getEmail(), emailtextbox.getText());
                    currProfile.changeEmail(emailtextbox.getText());
                }
                if (currProfile.getAddress() == null) {
                    if (zipcodetextbox.getText().equals("")) {
                        zip = -1;
                    } else {
                        zip = Integer.valueOf(zipcodetextbox.getText());
                    }
                    if (aptnumtextbox.getText().equals("")) {
                        apt = -1;
                    } else {
                        apt = Integer.valueOf(aptnumtextbox.getText());
                    }
                    currProfile.changeAddress(new Address(streetaddresstextbox.getText(), apt,
                            citytextbox.getText(), statetextbox.getText(), zip, countrytextbox.getText()));
                } else {
                    currProfile.getAddress().setStreet(streetaddresstextbox.getText());
                    currProfile.getAddress().setCity(citytextbox.getText());
                    currProfile.getAddress().setState(statetextbox.getText());
                    if (zipcodetextbox.getText().equals("")) {
                        currProfile.getAddress().setZip(-1);
                    } else {
                        currProfile.getAddress().setZip(Integer.valueOf(zipcodetextbox.getText()));
                    }
                    if (aptnumtextbox.getText().equals("")) {
                        currProfile.getAddress().setApartmentNum(-1);
                    } else {
                        currProfile.getAddress().setApartmentNum(Integer.valueOf(aptnumtextbox.getText()));
                    }
                }
            }
        });

    }
    /**
     * Sets all texts fields as either editable or non-editable
     * @param value
     */
    public void setAllEditable(boolean value) {
        lastnametextbox.setEditable(value);
        firstnametextbox.setEditable(value);
        emailtextbox.setEditable(value);
        streetaddresstextbox.setEditable(value);
        citytextbox.setEditable(value);
        statetextbox.setEditable(value);
        countrytextbox.setEditable(value);
        aptnumtextbox.setEditable(value);
        zipcodetextbox.setEditable(value);
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
}
