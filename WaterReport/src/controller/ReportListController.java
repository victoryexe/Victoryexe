package controller;

import fxapp.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
        reports = ReportsList.getWaterReportsList();

        viewreport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WaterReport report = (WaterReport) reportlist.getSelectionModel().getSelectedItem();
                if (report == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "No Report Selected.", ButtonType.CLOSE);
                    alert.show();
                } else {
                    ViewReportsController.setReport(report);
                    Stage stage;
                    Parent root;
                    stage = (Stage) viewreport.getScene().getWindow();
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/WaterAveReport.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public static void updateList() {
        reports = ReportsList.getWaterReportsList();
        reportlist.setItems(javafx.collections.FXCollections.observableList(reports));
    }
}
