package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Users.Worker;
import model.report.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by grizz on 10/24/2016.
 * Handles adding Quality Reports to the system.
 */
public class AddQualityController {
    /**
     *
     * @param purityLat TextField where user submits the latitude of water location
     * @param purityLon TextField where user submits the longitude of water location
     * @param VirusPPM TextField where user submits the VirusPPM
     * @param ContamPPM TextField where user submits the Contaminant PPM
     * @param purityCond ComboBox where user selects the overall condition of the water
     * @param SubmitPurity Button where user submits the report
     * @param qualtoave Button where user switches to the quality report screen
     * @param pane TabPane where all Tabs are located
     * @param water Tab where water reports may be created
     */

    public AddQualityController(TextField purityLat, TextField purityLon, TextField VirusPPM,
                                TextField ContamPPM, ComboBox purityCond, Button SubmitPurity,
                                Button qualtoave, TabPane pane, Tab water) {
        MainController.restrictToNums(purityLat);
        MainController.restrictToNums(purityLon);
        MainController.restrictToNums(VirusPPM);
        MainController.restrictToNums(ContamPPM);
        ArrayList<OverallCondition> conditions = new ArrayList<>();
        conditions.addAll(Arrays.asList(OverallCondition.values()));

        MainController.populateComboBox(purityCond, conditions);

        SubmitPurity.setOnAction((ActionEvent event) -> {

                // Checks for valid input before creating report
                if (purityLat.getText().equals("") || purityLon.getText().equals("")
                        || VirusPPM.getText().equals("") || ContamPPM.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "All Fields must be filled in.", ButtonType.CLOSE);
                    alert.show();
                } else if (Double.valueOf(purityLat.getText()) > 90.0
                        || Double.valueOf(purityLat.getText()) < -90.0
                        || Double.valueOf(purityLon.getText()) > 180.0
                        || Double.valueOf(purityLon.getText()) < -180.0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Invalid Latitude or Longitude, please enter a valid location.", ButtonType.CLOSE);
                    alert.show();
                } else {
                    Location loc = new Location(Double.parseDouble(purityLat.getText()),
                            Double.parseDouble(purityLon.getText()));
                    Report report = ReportsList.makeReport((Worker) LoginScreenController.currUser, loc,
                            (OverallCondition) purityCond.getValue(), Double.parseDouble(VirusPPM.getText()),
                            Double.parseDouble(ContamPPM.getText()));
                    purityLat.setText("");
                    purityLon.setText("");
                    purityCond.setValue(conditions.get(0));
                    VirusPPM.setText("");
                    ContamPPM.setText("");
                    MapController.addMarker(loc);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            //This will only ever have a problem if we have 2^32 reports and have reports with dulpicate
                            //ID's
                            "Report #Q" + report.getReportID() + " has been submitted.\n" +
                                    "Thank you for your contribution.", ButtonType.CLOSE);
                    alert.show();
                    // updates the View Reports ListView
                    ReportListController.updateList();
                }
            });

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
}
