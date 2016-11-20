package controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lib.password_hashing.PasswordStorage;
import model.Users.Address;
import model.Users.Profile;
import model.registration.UserList;

/**
 * Created by grizz on 9/27/2016.
 * Handles User Profiles
 */
class ProfileController {

    private final TextField lastnametextbox;
    private final TextField firstnametextbox;
    private final TextField streetaddresstextbox;
    private final TextField citytextbox;
    private final TextField statetextbox;
    private final TextField countrytextbox;
    private final TextField aptnumtextbox;
    private final TextField zipcodetextbox;
    private final TextField emailtextbox;
    private final Profile currProfile;

    public ProfileController(TextField lastnametextbox, TextField firstnametextbox, TextField streetaddresstextbox,
                   TextField statetextbox, TextField countrytextbox, TextField citytextbox, TextField aptnumtextbox,
                   TextField zipcodetextbox, TextField emailtextbox, ComboBox<String> salutationcombobox,
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

        salutationcombobox.setVisible(false);
        salutationcombobox.setOnMousePressed(event -> salutationcombobox.requestFocus());
        currProfile = new Profile(LoginScreenController.getCurrUser());
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
            Address add = currProfile.getAddress();
            streetaddresstextbox.setText(add.getStreet());
            if (add.apartmentNumber() != -1) {
                aptnumtextbox.setText("" + add.apartmentNumber());
            }
            citytextbox.setText(add.getCity());
            statetextbox.setText(add.getState());
            countrytextbox.setText(add.getCountry());
            if (add.getZip() != -1) {
                zipcodetextbox.setText("" + add.getZip());
            }
        }

        submit.setOnAction(event -> {
            submit.setVisible(false);
            salutationedit.setVisible(true);
            currProfile.changeTitle(salutationcombobox.getValue());
            currsalutation.setText(currProfile.getTitle());
            currsalutation.setVisible(true);
            salutationcombobox.setVisible(false);
            setAllEditable(false);
            currProfile.changeName(firstnametextbox.getText() + " " + lastnametextbox.getText());
            int zip;
            int apt;
            if (!currProfile.getEmail().equals(emailtextbox.getText())) {
                try {
                    UserList.updateMap(currProfile.getEmail(), emailtextbox.getText());
                    currProfile.changeEmail(emailtextbox.getText());
                } catch (PasswordStorage.CannotPerformOperationException e) {
                    throw new RuntimeException(e);
                }
            }
            Address add = currProfile.getAddress();
            if (add == null) {
                if ("".equals(zipcodetextbox.getText())) {
                    zip = -1;
                } else {
                    zip = Integer.valueOf(zipcodetextbox.getText());
                }
                if ("".equals(aptnumtextbox.getText())) {
                    apt = -1;
                } else {
                    apt = Integer.valueOf(aptnumtextbox.getText());
                }
                add = new Address(streetaddresstextbox.getText(), apt,
                        citytextbox.getText(), statetextbox.getText(), zip, countrytextbox.getText());
            } else {
                add.setStreet(streetaddresstextbox.getText());
                add.setCity(citytextbox.getText());
                add.setState(statetextbox.getText());
                add.setCountry(countrytextbox.getText());
                if ("".equals(zipcodetextbox.getText())) {
                    add.setZip(-1);
                } else {
                    add.setZip(Integer.valueOf(zipcodetextbox.getText()));
                }
                if ("".equals(aptnumtextbox.getText())) {
                    add.setApartmentNum(-1);
                } else {
                    add.setApartmentNum(Integer.valueOf(aptnumtextbox.getText()));
                }
            }
            currProfile.changeAddress(add);
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
