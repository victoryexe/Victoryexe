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
import javafx.scene.text.Text;
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
    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;
    @FXML
    private ComboBox sourceBox;
    @FXML
    private Button submitRepBox;
    @FXML
    private ComboBox conditionBox;
    @FXML
    private ListView reportlist;
    @FXML
    private Button viewreport;
    @FXML
    private TextField othertype;


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
        // Delegates control of report submission to AddReportController
        AddReportController addReport = new AddReportController(latitude,
                longitude, sourceBox, conditionBox, submitRepBox, othertype);
        // Delegates control of the View Report screen to ReportListController
        ReportListController reportList = new ReportListController(reportlist, viewreport);
        // Delegates control of the profile view to ProfileController
        ProfileController profile = new ProfileController(lastnametextbox, firstnametextbox, streetaddresstextbox,
                statetextbox, countrytextbox, citytextbox, aptnumtextbox, zipcodetextbox, emailtextbox, salutationcombobox,
                salutationedit, submit, currsalutation);
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
    /**
     * Forces only decimal numbers to be entered as values in corresponding
     * TextField
     */

    protected static void restrictToNums(TextField field) {
        DecimalFormat format = new DecimalFormat( "#" );
        field.setTextFormatter( new TextFormatter<>(c ->
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

    }

    /**
     * Takes a ComboBox and an ArrayList containing its elements then populates the ComboBox and sets
     * the default value to the first entry in the list
     * @param box the ComboBox being populated
     * @param list the ArrayList being used to populate box
     */
    protected static void populateComboBox(ComboBox box, ArrayList list) {
        box.setItems(javafx.collections.FXCollections.observableList(list));
        box.setValue(list.get(0));
    }
}
