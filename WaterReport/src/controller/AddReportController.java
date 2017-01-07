package controller;

import db.DB;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import model.Users.Account;
import model.Users.AuthLevel;
import model.Users.User;
import model.report.Location;
import model.report.Report;
import model.report.ReportsList;
import model.report.WaterCondition;
import model.report.WaterType;

import java.util.List;

/**
 * Created by grizz on 10/16/2016.
 * Handles adding Water Reports to the system.
 */
class AddReportController {

    private final TextField latitude;
    private final TextField longitude;
    private final Button avetoqual;
    private final ComboBox<WaterType> sourceBox;
    private final ComboBox<WaterCondition> conditionBox;

    private final Account currUser;

    /**
     * Takes in the information from the Main Controller and handles the
     * functions of the Submit Report tab
     * @param latitude TextField where user submits the latitude of water
     *                 location
     * @param longitude TextField where user submits the longitude of water
     *                  location
     * @param sourceBox ComboBox where user selects the water source
     * @param conditionBox ComboBox where user selects the water condition
     * @param avetoqual Button used to switch to the quality report
     */
    public AddReportController (TextField latitude, TextField longitude,
                                ComboBox<WaterType> sourceBox, ComboBox<WaterCondition> conditionBox,
                                Button avetoqual) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.avetoqual = avetoqual;
        this.sourceBox = sourceBox;
        this.conditionBox = conditionBox;
        //Initializes the water source and water condition ComboBoxes

        currUser = LoginScreenController.getCurrUser();


        if (AuthLevel.USER == currUser.getAuthLevel()) {
            avetoqual.setVisible(false);
        }
    }

    public void setReportButton(Button submitRepBox, TextField other,
                                List<WaterType> source, List<WaterCondition> condition) {
        submitRepBox.setOnAction((ActionEvent event) -> {
            // Checks for valid input before creating report
            if ("".equals(latitude.getText())
                    || "".equals(longitude.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Both Latitude and Longitude must be filled in.",
                        ButtonType.CLOSE);
                alert.show();
            } else if (WaterType.OTHER.equals(sourceBox.getValue())
                    && "".equals(other.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Please Specify Water Source.", ButtonType.CLOSE);
                alert.show();
            } else if ((Math.abs(Double.valueOf(latitude.getText())) > Location.VALID_LATITUDE)
                    || (Math.abs(Double.valueOf(longitude.getText())) > Location.VALID_LONGITUDE)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid Latitude or Longitude, please"
                                + "enter a valid location.", ButtonType.CLOSE);
                alert.show();
            } else {
                // creates report and stores in ReportList
                Location loc =
                        new Location(Double.parseDouble(latitude.getText()),
                                Double.parseDouble(longitude.getText()));
                Report report = ReportsList.makeReport(
                        (User) currUser, loc, sourceBox.getValue(), conditionBox.getValue());
                DB.addReport(report);
                if (WaterType.OTHER.equals(sourceBox.getValue())) {
                    String otherText = "";
                    if (other.getText() != null) {
                        otherText = other.getText();
                    }
                    report.setOther(otherText);
                }
                latitude.setText("");
                longitude.setText("");
                sourceBox.setValue(source.get(0));
                conditionBox.setValue(condition.get(0));
                MapController.addMarker(loc);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        //This will only ever have a problem if we
                        // have 2^32 reports and have reports with
                        // duplicate IDs
                        "Report #W" + (report != null ? report.getReportID() : 0)
                                + " has been submitted.\n" +
                                "Thank you for your contribution.",
                        ButtonType.CLOSE);
                alert.show();
                // updates the View Reports ListView
                ReportListController.updateList();
            }
        });
    }
    public void switchToQual(TabPane pane, Tab quality, List<WaterType> source, List<WaterCondition> condition) {
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
