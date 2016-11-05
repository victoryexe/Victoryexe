package controller;

import fxapp.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.report.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by grizz on 10/17/2016.
 */
public class ReportListController {

    private static ListView reportlist;
    private final Button viewreport;
    private static List reports;
    private FXMLLoader loader;

    private Main mainApp;

    public void setMainApp(Main main) {
        mainApp = main;
    }

    public ReportListController(ListView reportlist, Button viewreport) {
        this.reportlist = reportlist;
        this.viewreport = viewreport;
        updateList();

        viewreport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Report report = (Report) reportlist.getSelectionModel().getSelectedItem();
                if (report == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "No Report Selected.", ButtonType.CLOSE);
                    alert.show();
                } else if (report instanceof WaterReport){
                    ViewReportsController.setReport(report);
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../view/WaterAveReport.fxml"));
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
                    ViewReportsController.setReport(report);
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../view/WaterQualityReport.fxml"));
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
            }
        });
    }


    public static void updateList() {
        reports = SortReports.sortByMostRecent();
        reportlist.setItems(javafx.collections.FXCollections.observableList(reports));
    }
}