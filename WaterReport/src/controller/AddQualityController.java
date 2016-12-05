package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Users.Worker;
import model.report.Location;
import model.report.OverallCondition;
import model.report.QualityReport;
import model.report.ReportsList;
import db.DB;
import java.util.List;

/**
 * Created by grizz on 10/24/2016.
 * Handles adding Quality Reports to the system.
 */
class AddQualityController {

    private final TextField purityLat;
    private final TextField purityLon;
    private final TextField VirusPPM;
    private final TextField ContamPPM;
    private final ComboBox<OverallCondition> purityCond;

    /**
     *
     * @param purityLat TextField where user submits the latitude of water location
     * @param purityLon TextField where user submits the longitude of water location
     * @param VirusPPM TextField where user submits the VirusPPM
     * @param ContamPPM TextField where user submits the Contaminant PPM
     * @param purityCond ComboBox where user selects the overall condition of the water
     */

    public  AddQualityController(TextField purityLat, TextField purityLon, TextField VirusPPM,
                                TextField ContamPPM, ComboBox<OverallCondition> purityCond) {
        this.purityLat = purityLat;
        this.purityLon = purityLon;
        this.VirusPPM = VirusPPM;
        this.ContamPPM = ContamPPM;
        this.purityCond = purityCond;
    }

    public void setSwitchButton(List<OverallCondition> conditions, TabPane pane, Tab water, Button qualtoave) {
        qualtoave.setOnAction((ActionEvent event) -> {

            purityLat.setText("");
            purityLon.setText("");
            purityCond.setValue(conditions.get(0));
            VirusPPM.setText("");
            ContamPPM.setText("");
            pane.getTabs().set(2, water);
            pane.getSelectionModel().select(water);
        });
    }

    public void setPurityButton(List<OverallCondition> conditions, Button SubmitPurity) {
        SubmitPurity.setOnAction((ActionEvent event) -> {

            // Checks for valid input before creating report
            if ("".equals(purityLat.getText()) || "".equals(purityLon.getText())
                    || "".equals(VirusPPM.getText()) || "".equals(ContamPPM.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "All Fields must be filled in.", ButtonType.CLOSE);
                alert.show();
            } else if ((Math.abs(Double.valueOf(purityLat.getText())) > Location.VALID_LATITUDE)
                    || (Math.abs(Double.valueOf(purityLon.getText())) > Location.VALID_LONGITUDE)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid Latitude or Longitude, please enter a valid location.", ButtonType.CLOSE);
                alert.show();
            } else {
                Location loc = new Location(Double.parseDouble(purityLat.getText()),
                        Double.parseDouble(purityLon.getText()));
                QualityReport report = ReportsList.makeReport((Worker) LoginScreenController.getCurrUser(), loc,
                        purityCond.getValue(), Double.parseDouble(VirusPPM.getText()),
                        Double.parseDouble(ContamPPM.getText()));
                DB.addReport(report);
                purityLat.setText("");
                purityLon.setText("");
                purityCond.setValue(conditions.get(0));
                VirusPPM.setText("");
                ContamPPM.setText("");
                MapController.addMarker(loc);
                if (report != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            //This will only ever have a problem if we have 2^32 reports and have reports with dulpicate
                            //ID's
                            "Report #Q" + report.getReportID() + " has been submitted.\n" +
                                    "Thank you for your contribution.", ButtonType.CLOSE);
                    alert.show();
                    // updates the View Reports ListView
                    ReportListController.updateList();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Failed to submit Report.", ButtonType.CLOSE);
                    alert.show();
                }
            }
        });
    }
}
