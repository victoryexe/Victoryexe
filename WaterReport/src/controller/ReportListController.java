package controller;

import db.DB;
import fxapp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Users.Manager;
import model.report.*;


import java.io.IOException;
import java.util.List;

/**
 * Created by grizz on 10/17/2016.
 * Handles the Report List View
 */
class ReportListController {

    private static ListView<Report> reportlist;
    private FXMLLoader loader;

    ReportListController(ListView<Report> reportlist, Button viewreport, Button histReport, Button deleteReport) {
        setList(reportlist);
        updateList();

        deleteReport.setOnAction(event -> {
            Report report = reportlist.getSelectionModel().getSelectedItem();
            if (report == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "No Report Selected.", ButtonType.CLOSE);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete selected Report?");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            ReportsList.deleteReport((Manager) LoginScreenController.getCurrUser(),
                                    report);
                            DB.deleteReport(report);
                            updateList();
                        });
            }
        });

        viewreport.setOnAction(event -> {
            Report report = reportlist.getSelectionModel().getSelectedItem();
            if (report == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "No Report Selected.", ButtonType.CLOSE);
                alert.show();
            } else if (report instanceof WaterReport){
                ViewReportsController.setReport((WaterReport) report);
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("../view/WaterAveReport.fxml"));
                    AnchorPane page = loader.load();

                    // Create the dialog Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Water Report");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(viewreport.getScene().getWindow());
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Set the person into the controller.
                    ViewReportsController controller = loader.getController();
                    controller.setDialogStage(dialogStage);

                    // Show the dialog and wait until the user closes it
                    dialogStage.showAndWait();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(report instanceof QualityReport) {
                ViewReportsController.setReport((QualityReport) report);
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("../view/WaterQualityReport.fxml"));
                    AnchorPane page = loader.load();

                    // Create the dialog Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Quality Report");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(viewreport.getScene().getWindow());
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Set the person into the controller.
                    ViewReportsController controller = loader.getController();
                    controller.setDialogStage(dialogStage);

                    // Show the dialog and wait until the user closes it
                    dialogStage.showAndWait();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        histReport.setOnAction((ActionEvent) ->{try {
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/historicalReport.fxml"));
            SplitPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Historical Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(histReport.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            HistoricalReportController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }});
    }


    static void updateList() {
        List<Report> reports = SortReports.sortByMostRecent();
        reportlist.setItems(javafx.collections.FXCollections.observableList(reports));
    }

    private void setList(ListView<Report> repo) {
        reportlist = repo;
    }
}
