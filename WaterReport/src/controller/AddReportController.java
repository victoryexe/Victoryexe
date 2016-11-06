package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Users.AuthLevel;
import model.Users.User;
import model.report.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by grizz on 10/16/2016.
 * Handles adding Water Reports to the system.
 */
public class AddReportController {

    /**
     * Takes in the information from the Main Controller and handles the functions of the Submit Report tab
     * @param latitude TextField where user submits the latitude of water location
     * @param longitude TextField where user submits the longitude of water location
     * @param sourceBox ComboBox where user selects the water source
     * @param conditionBox ComboBox where user selects the water condition
     * @param submitRepBox Button used to submit the water report to the system
     * @param other TextField where the user may submit a custom water source
     * @param avetoqual Button used to switch to the quality report
     * @param pane TabPane where all tabs are located
     * @param quality Tab where quality reports may be created
     */
    public AddReportController (TextField latitude, TextField longitude,
                                ComboBox sourceBox, ComboBox conditionBox, Button submitRepBox,
                                TextField other, Button avetoqual, TabPane pane, Tab quality) {
        //Initializes the water source and water condition ComboBoxes
        ArrayList<WaterType> source = new ArrayList<>();
        source.addAll(Arrays.asList(WaterType.values()));

        ArrayList<WaterCondition> condition = new ArrayList<>();
        condition.addAll(Arrays.asList( WaterCondition.values()));

        MainController.populateComboBox(sourceBox, source);
        MainController.populateComboBox(conditionBox, condition);
        MainController.restrictToNums(latitude);
        MainController.restrictToNums(longitude);

        submitRepBox.setOnAction((ActionEvent event) -> {
                // Checks for valid input before creating report
                if (latitude.getText().equals("") || longitude.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Both Latitude and Longitude must be filled in.", ButtonType.CLOSE);
                    alert.show();
                } else if (sourceBox.getValue().equals(WaterType.OTHER)
                        && other.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Please Specify Water Source.", ButtonType.CLOSE);
                    alert.show();
                } else if (Double.valueOf(latitude.getText()) > 90.0
                        || Double.valueOf(latitude.getText()) < -90.0
                        || Double.valueOf(longitude.getText()) > 180.0
                        || Double.valueOf(longitude.getText()) < -180.0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Invalid Latitude or Longitude, please enter a valid location.", ButtonType.CLOSE);
                    alert.show();
                } else {
                    // creates report and stores in ReportList
                    Location loc = new Location(Double.parseDouble(latitude.getText()),
                            Double.parseDouble(longitude.getText()));
                    Report report = ReportsList.makeReport((User) LoginScreenController.currUser, loc,
                            (WaterType) sourceBox.getValue(), (WaterCondition) conditionBox.getValue());
                    if ( sourceBox.getValue().equals(WaterType.OTHER)) {
                        report.setOther(other.getText());
                    }
                    latitude.setText("");
                    longitude.setText("");
                    sourceBox.setValue(source.get(0));
                    conditionBox.setValue(condition.get(0));
                    MapController.addMarker(loc);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            //This will only ever have a problem if we have 2^32 reports and have reports with dulpicate
                            //ID's
                            "Report #W" + report.getReportID() + " has been submitted.\n" +
                                    "Thank you for your contribution.", ButtonType.CLOSE);
                    alert.show();
                    // updates the View Reports ListView
                    ReportListController.updateList();
                }
            });
        if (LoginScreenController.currUser.getAuthLevel() == AuthLevel.USER) {
            avetoqual.setVisible(false);
        }
        avetoqual.setOnAction((ActionEvent event) -> {
                latitude.setText("");
                longitude.setText("");
                sourceBox.setValue(source.get(0));
                conditionBox.setValue(condition.get(0));
                pane.getTabs().set(2, quality);
                pane.getSelectionModel().select(quality);
            });
    }
}
