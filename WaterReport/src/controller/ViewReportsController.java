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

    private static int reportType;

    private Stage dialogStage;

    private static Report report;

    /**
     * Used to acquire the Water report being displayed
     *
     * @param nreport the water report currently being displayed
     */
    public static void setReport(WaterReport nreport) {
        report = nreport;
        reportType = 0;
    }

    public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage;}

    /**
     * Used to acquire the Quality report being displayed
     *
     * @param nreport the quality report currently being displayed
     */
    public static void setReport(QualityReport nreport) {
        report = nreport;
        reportType = 1;
    }

    @FXML
    private void initialize() {
        if (reportType == 0) {
            showWaterReport((WaterReport)report);
        } else if (reportType == 1) {
            showQualityReport((QualityReport) report);
        }
    }
    private void showWaterReport(WaterReport report) {
        dateedit.setText(String.valueOf(report.getDate()));
        timeedit.setText(String.valueOf(report.getTime()));
        reportnumberedit.setText("W"
                + String.valueOf(report.getReportID()));
        nameofreporteredit.setText(report.getSubmitterName());
        locationofwateredit.setText(report.getCoordinates());
        if (WaterType.OTHER.equals(report.getWaterType())) {
            typeofwateredit.setText("" + report.getWaterType() + ": " + report.getOther());
        } else {
            typeofwateredit.setText("" + report.getWaterType());
        }
        conditionofwateredit.setText("" + report.getWaterCondition());
    }
    private void showQualityReport(QualityReport report) {
        qdateedit.setText(String.valueOf(report.getDate()));
        qtimeedit.setText(String.valueOf(report.getTime()));
        qreportnumberedit.setText("Q"
                + String.valueOf(report.getReportID()));
        qnameofworkeredit.setText(report.getSubmitterName());
        qlocationofwateredit.setText(report.getCoordinates());
        qoverallconditionedit.setText(
                ("" + report.getWaterCondition()));
        qvirusppmedit.setText(""
                + report.getVirusPPM() + "ppm");
        qcontaminantppmedit.setText(""
                + report.getContaminantPPM() + "ppm");
    }
}
