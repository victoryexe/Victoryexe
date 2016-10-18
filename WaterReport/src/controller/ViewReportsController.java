package controller;

import fxapp.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.report.Report;
import model.report.WaterReport;

import java.io.IOException;

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
    @FXML
    private Button exit;
    private static WaterReport report;

    private Stage dialogStage;

    private Main mainApp;

    public void setMainApp(Main main) { mainApp = main; }

    public static void setReport(WaterReport wreport) {report = wreport;}

    @FXML
    private void initialize() {
        dateedit.setText(String.valueOf(report.getDate()));
        timeedit.setText(String.valueOf(report.getTime()));
        reportnumberedit.setText(String.valueOf(report.getReportID()));
        nameofreporteredit.setText(report.getSubmitterID().getName());
        locationofwateredit.setText(report.getLocation().getLatitude()
                + ", " + report.getLocation().getLongitude());
        typeofwateredit.setText(report.getWaterType().name());
        conditionofwateredit.setText(report.getWaterCondition().name());

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root;
                Stage stage;
                stage = (Stage) exit.getScene().getWindow();
                try {
                    root = FXMLLoader.load(getClass().getResource("../view/MainScreenView.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


}
