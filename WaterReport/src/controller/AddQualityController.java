package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import model.Users.Worker;
import model.report.*;

import java.util.ArrayList;

/**
 * Created by grizz on 10/24/2016.
 */
public class AddQualityController {

    public AddQualityController(TextField purityLat, TextField purityLon, TextField VirusPPM,
                                TextField ContamPPM, ComboBox purityCond, Button SubmitPurity,
                                Button qualtoave, TabPane pane, Tab water) {
        MainController.restrictToNums(purityLat);
        MainController.restrictToNums(purityLon);
        MainController.restrictToNums(VirusPPM);
        MainController.restrictToNums(ContamPPM);
        ArrayList<OverallCondition> conditions = new ArrayList<>();
        for (OverallCondition con : OverallCondition.values()) {
            conditions.add(con);
        }
        MainController.populateComboBox(purityCond, conditions);

        SubmitPurity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                            "Report #Q" + report.getReportID() + " has been submitted.\n" +
                                    "Thank you for your contribution.", ButtonType.CLOSE);
                    alert.show();
                    // updates the View Reports ListView
                    ReportListController.updateList();
                }
            }
        });

        qualtoave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                purityLat.setText("");
                purityLon.setText("");
                purityCond.setValue(conditions.get(0));
                VirusPPM.setText("");
                ContamPPM.setText("");
                pane.getTabs().set(2, water);
                pane.getSelectionModel().select(water);
            }
        });
    }
}
