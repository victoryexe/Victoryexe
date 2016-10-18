package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import model.Users.User;
import model.report.*;

import java.util.ArrayList;

/**
 * Created by grizz on 10/16/2016.
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
     */
    public AddReportController (TextField latitude, TextField longitude,
                                ComboBox sourceBox, ComboBox conditionBox, Button submitRepBox,
                                TextField other) {
        //Initializes the water source and water condition ComboBoxes
        ArrayList<WaterType> source = new ArrayList<WaterType>();
        for (WaterType type : WaterType.values()) {
            source.add(type);
        }
        ArrayList<WaterCondition> condition = new ArrayList<WaterCondition>();
        for (WaterCondition con : WaterCondition.values()) {
            condition.add(con);
        }
        MainController.populateComboBox(sourceBox, source);
        MainController.populateComboBox(conditionBox, condition);
        MainController.restrictToNums(latitude);
        MainController.restrictToNums(longitude);

        submitRepBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                    Report report = ReportsList.makeReport((User) LoginScreenController.currUser,
                            new Location(latitude.getText(), longitude.getText()),
                            (WaterType) sourceBox.getValue(), (WaterCondition) conditionBox.getValue());
                                        latitude.setText("");
                    if ( sourceBox.getValue().equals(WaterType.OTHER)) {
                        report.setOther(other.getText());
                    }
                    longitude.setText("");
                    sourceBox.setValue(source.get(0));
                    conditionBox.setValue(condition.get(0));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Report #W" + report.getReportID() + " has been submitted.\n" +
                                    "Thank you for your contribution.", ButtonType.CLOSE);
                    alert.show();
                    // updates the View Reports ListView
                    ReportListController.updateList();
                }
            }
        });
    }
}
