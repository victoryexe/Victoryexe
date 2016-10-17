package controller;

import fxapp.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.report.Report;
import model.report.ReportsList;
import model.report.WaterReport;

import java.io.IOException;
import java.util.List;

/**
 * Created by grizz on 10/17/2016.
 */
public class ReportListController {

    private static ListView reportlist;
    private Button viewreport;
    private static List reports;

    private Main mainApp;

    public void setMainApp(Main main) { mainApp = main; }

    public ReportListController(ListView reportlist, Button viewreport) {
        this.reportlist = reportlist;
        this.viewreport = viewreport;
        reports = ReportsList.getReportsList();

        viewreport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WaterReport report = (WaterReport)reportlist.getUserData();
                ViewReportsController view = new ViewReportsController(report);
            }
        });
    }


    public static void updateList() {
        reports = ReportsList.getReportsList();
        reportlist.setItems(javafx.collections.FXCollections.observableList(reports));
    }
}
