package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.report.Location;
import model.report.SortReports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grizz on 11/1/2016.
 * Handles displaying of the Historical Report
 */
public class HistoricalReportController {

    @FXML
    private TextField hisLat;
    @FXML
    private TextField hisLon;
    @FXML
    private ComboBox<String> Year;
    @FXML
    private ComboBox Ptype;
    @FXML
    private Button searchBar;
    @FXML
    private BarChart HistoricalChart;

    private Stage dialogStage;

    private final int DEFAULT_RADIUS = 50;

    private ObservableList<XYChart.Data<String, Double>> sdata;

    private XYChart.Series<String, Double> series;

    private final List<String> years = new ArrayList<>();

    private final List<String> type = new ArrayList<>();

    private NumberAxis yAxis;

    private double[] data;
    private static final int MIN_YEAR = 2016;
    private static final int MAX_YEAR = 3016;

    public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage;}

    @FXML
    private void initialize() {
        for(int i = MIN_YEAR; i < MAX_YEAR; i++) {
            years.add(String.valueOf(i));
        }
        MainController.populateComboBox(Year, years);
        MainController.restrictToNums(hisLat);
        MainController.restrictToNums(hisLon);
        type.add("Contaminant");
        type.add("Virus");
        MainController.populateComboBox(Ptype, type);
        CategoryAxis xAxis = (CategoryAxis) HistoricalChart.getXAxis();
        yAxis = (NumberAxis)HistoricalChart.getYAxis();
        xAxis.setLabel("Month");
        series = new XYChart.Series<>();
        sdata = series.getData();
        sdata.add(new XYChart.Data<>("January", 0.0));
        sdata.add(new XYChart.Data<>("February", 0.0));
        sdata.add(new XYChart.Data<>("March", 0.0));
        sdata.add(new XYChart.Data<>("April", 0.0));
        sdata.add(new XYChart.Data<>("May", 0.0));
        sdata.add(new XYChart.Data<>("June", 0.0));
        sdata.add(new XYChart.Data<>("July", 0.0));
        sdata.add(new XYChart.Data<>("August", 0.0));
        sdata.add(new XYChart.Data<>("September", 0.0));
        sdata.add(new XYChart.Data<>("October", 0.0));
        sdata.add(new XYChart.Data<>("November", 0.0));
        sdata.add(new XYChart.Data<>("December", 0.0));
        series.setData(sdata);
        HistoricalChart.getData().addAll(series);

        setUpSearch();

    }

    private void setUpSearch() {
        searchBar.setOnAction((ActionEvent) -> {
            if("".equals(hisLat.getText()) || "".equals(hisLon.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Both Latitude and Longitude must be filled in.",
                        ButtonType.CLOSE);
                alert.show();
            } else if ((Math.abs(Double.valueOf(hisLat.getText())) > Location.VALID_LATITUDE)
                    || (Math.abs(Double.valueOf(hisLon.getText())) > Location.VALID_LONGITUDE)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid Latitude or Longitude, please enter"
                                + " a valid location.", ButtonType.CLOSE);
                alert.show();
            } else {
                Location loc = new Location(
                        Double.parseDouble(hisLat.getText()),
                        Double.parseDouble(hisLon.getText()));
                if ((Ptype.getValue()).equals(Ptype.getItems().get(0))) {
                    data = SortReports.
                            generateHistoricalReportByContaminantPPM(loc, DEFAULT_RADIUS,
                                    Integer.parseInt(Year.getValue()));
                    yAxis.setLabel("ContaminantPPM");
                    series.setName("Contaminant");

                } else {
                    data = SortReports.generateHistoricalReportByVirusPPM(loc,
                            DEFAULT_RADIUS, Integer.parseInt(Year.getValue()));
                    yAxis.setLabel("VirusPPM");
                    series.setName("Virus");
                }
                boolean exists = false;
                for (double aData : data) {
                    if (aData != 0) {
                        exists = true;
                    }
                }
                if (exists) {
                    setData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "No Reports listed around requested location for requested date.",
                            ButtonType.CLOSE);
                    alert.show();
                }
            }
        });

    }

    private void setData() {
        final int DECEMBER = 11;
        sdata.set(0, new XYChart.Data<>("January", data[0]));
        sdata.set(1, new XYChart.Data<>("February", data[1]));
        sdata.set(2, new XYChart.Data<>("March", data[2]));
        sdata.set(3, new XYChart.Data<>("April", data[3]));
        sdata.set(4, new XYChart.Data<>("May", data[4]));
        sdata.set(5, new XYChart.Data<>("June", data[5]));
        sdata.set(6, new XYChart.Data<>("July", data[6]));
        sdata.set(7, new XYChart.Data<>("August", data[7]));
        sdata.set(8, new XYChart.Data<>("September", data[8]));
        sdata.set(9, new XYChart.Data<>("October", data[9]));
        sdata.set(10, new XYChart.Data<>("November", data[10]));
        sdata.set(DECEMBER, new XYChart.Data<>("December", data[DECEMBER]));
        series.setData(sdata);
        HistoricalChart.getData().setAll(series);
    }
}
