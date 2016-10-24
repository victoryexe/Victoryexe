package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.report.Report;
import model.report.WaterReport;
import model.report.WaterType;

/**
 * Created by grizz on 10/17/2016.
 */
public class ViewReportsController {

    @FXML
    private Text dateedit;
    @FXML
    private Text timeedit;
    @FXML
    private Text reportnumberedit;
    @FXML
    private Text nameofreporteredit;
    @FXML
    private Text locationofwateredit;
    @FXML
    private Text typeofwateredit;
    @FXML
    private Text conditionofwateredit;

    private Stage dialogStage;

    private static WaterReport report;

    /**
     * Used to acquire the report being displayed
     *
     * @param wreport the water report currently being displayed
     */
    public static void setReport(WaterReport wreport) {report = wreport;}

    /**
     * Sets the stage for the dialog
     *
     * @param dialogStage the stage for the dialog
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        dateedit.setText(String.valueOf(report.getDate()));
        timeedit.setText(String.valueOf(report.getTime()));
        reportnumberedit.setText(String.valueOf(report.getReportID()));
        nameofreporteredit.setText(report.getSubmitterID().getName());
        locationofwateredit.setText(report.getLocation().getLatitude()
                + ", " + report.getLocation().getLongitude());
        if (report.getWaterType().equals(WaterType.OTHER)) {
            typeofwateredit.setText(report.getWaterType().name() + ": "
                    + report.getOther());
        } else {
            typeofwateredit.setText(report.getWaterType().name());
        }
        conditionofwateredit.setText(report.getWaterCondition().name());
    }
}
