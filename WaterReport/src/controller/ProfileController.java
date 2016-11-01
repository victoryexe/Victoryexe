package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Users.Address;
import model.Users.Profile;
import model.Users.User;
import model.login.Authentication;
import model.login.UserList;

import java.util.ArrayList;

import static controller.MainController.populateComboBox;
import static controller.MainController.restrictToNums;

/**
 * Created by grizz on 9/27/2016.
 */
public class ProfileController {

    private TextField lastnametextbox;
    private  TextField firstnametextbox;
    private TextField streetaddresstextbox;
    private TextField citytextbox;
    private TextField statetextbox;
    private TextField countrytextbox;
    private TextField aptnumtextbox;
    private TextField zipcodetextbox;
    private TextField emailtextbox;
    private ComboBox salutationcombobox;
    private Button salutationedit;
    private Button submit;
    private Label currsalutation;
    private Profile currProfile;

    public ProfileController(TextField lastnametextbox, TextField firstnametextbox, TextField streetaddresstextbox,
                   TextField statetextbox, TextField countrytextbox, TextField citytextbox, TextField aptnumtextbox,
                   TextField zipcodetextbox, TextField emailtextbox, ComboBox salutationcombobox,
                   Button salutationedit, Button submit, Label currsalutation) {
        this.lastnametextbox = lastnametextbox;
        this.firstnametextbox = firstnametextbox;
        this.streetaddresstextbox = streetaddresstextbox;
        this.statetextbox = statetextbox;
        this.countrytextbox = countrytextbox;
        this.citytextbox = citytextbox;
        this.aptnumtextbox = aptnumtextbox;
        this.zipcodetextbox = zipcodetextbox;
        this.emailtextbox = emailtextbox;
        this.salutationcombobox = salutationcombobox;
        this.salutationedit = salutationedit;
        this.submit = submit;
        this.currsalutation = currsalutation;

        restrictToNums(aptnumtextbox);
        restrictToNums(zipcodetextbox);

        ArrayList<String> salutation = new ArrayList<String>();
        salutation.add("");
        salutation.add("Mr.");
        salutation.add("Ms.");
        salutation.add("Mrs.");
        salutation.add("Dr.");
        populateComboBox(salutationcombobox, salutation);
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
        salutationedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                salutationedit.setVisible(false);
                submit.setVisible(true);
                currsalutation.setVisible(false);
                salutationcombobox.setVisible(true);
                if (currProfile.getTitle() != null) {
                    switch (currProfile.getTitle()) {
                        case "":
                            salutationcombobox.setValue(salutation.get(0));
                            break;
                        case "Mr.":
                            salutationcombobox.setValue(salutation.get(1));
                            break;
                        case "Ms.":
                            salutationcombobox.setValue(salutation.get(2));
                            break;
                        case "Mrs.":
                            salutationcombobox.setValue(salutation.get(3));
                            break;
                        case "Dr.":
                            salutationcombobox.setValue(salutation.get(4));
                            break;
                        default:
                            salutationcombobox.setValue(salutation.get(0));
                            break;
                    }
                }
                setAllEditable(true);
            }
        });
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
     * @param value editable or non-editable
     */
    private void setAllEditable(boolean value) {
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
}
