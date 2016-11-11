package controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.report.QualityReport;
import model.report.Report;
import model.report.WaterReport;
import model.report.WaterType;

/**
 * Created by grizz on 10/17/2016.
 * Handles the displaying of Water Reports and Quality Reports
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
    @FXML
    private Text qdateedit;
    @FXML
    private Text qtimeedit;
    @FXML
    private Text qreportnumberedit;
    @FXML
    private Text qnameofworkeredit;
    @FXML
    private Text qlocationofwateredit;
    @FXML
    private Text qoverallconditionedit;
    @FXML
    private Text qvirusppmedit;
    @FXML
    private Text qcontaminantppmedit;

    private Stage dialogStage;

    private static Report report;

    /**
     * Used to acquire the report being displayed
     *
     * @param nreport the water report currently being displayed
     */
    public static void setReport(Report nreport) {report = nreport;}

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
        if (report instanceof WaterReport) {
            dateedit.setText(String.valueOf(report.getDate()));
            timeedit.setText(String.valueOf(report.getTime()));
            reportnumberedit.setText("W"
                    + String.valueOf(report.getReportID()));
            nameofreporteredit.setText(report.getSubmitterID().getName());
            locationofwateredit.setText(String.valueOf(
                    report.getLocation().getLatitude()) + ", "
                    + String.valueOf(report.getLocation().getLongitude()));
            if (((WaterReport)report).getWaterType().equals(WaterType.OTHER)) {
                typeofwateredit.setText(((WaterReport)report)
                        .getWaterType().name() + ": " + report.getOther());
            } else {
                typeofwateredit.setText(((WaterReport)report)
                        .getWaterType().name());
            }
            conditionofwateredit.setText(((WaterReport)report).
                    getWaterCondition().name());
        } else if (report instanceof QualityReport) {
            qdateedit.setText(String.valueOf(report.getDate()));
            qtimeedit.setText(String.valueOf(report.getTime()));
            qreportnumberedit.setText("Q"
                    + String.valueOf(report.getReportID()));
            qnameofworkeredit.setText(report.getSubmitterID().getName());
            qlocationofwateredit.setText(String.valueOf(
                    report.getLocation().getLatitude()) + ", "
                    + String.valueOf(report.getLocation().getLongitude()));
            qoverallconditionedit.setText(
                    ((QualityReport)report).getWaterCondition().name());
            qvirusppmedit.setText(""
                    + ((QualityReport)report).getVirusPPM() + "ppm");
            qcontaminantppmedit.setText(""
                    + ((QualityReport)report).getContaminantPPM() + "ppm");
        }
    }
}
