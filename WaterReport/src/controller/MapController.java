package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import model.report.Location;
import model.report.Report;
import model.report.ReportsList;
import model.report.WaterReport;
import model.report.QualityReport;
import model.report.SortReports;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by grizz on 10/19/2016.
 * Handles the Map View.
 */

class MapController implements Initializable, MapComponentInitializedListener {

    private final GoogleMapView mapView;

    private static GoogleMap map;

    private final List<Location> locations = new ArrayList<>();

    public MapController(GoogleMapView mapView, TextField lat, TextField lon, Button search) {
        this.mapView = mapView;
        List<Report> qreports = ReportsList.getQualityReportsList();
        List<Report> wreports = ReportsList.getWaterReportsList();
        locations.addAll(wreports.stream().map(Report::getLocation).collect(Collectors.toList()));
        qreports.stream().filter(rep -> !locations.contains(
                rep.getLocation())).forEach(rep -> locations.add(rep.getLocation()));
        MainController.restrictToNums(lat);
        MainController.restrictToNums(lon);
        search.setOnAction((ActionEvent) -> {
            if (!"".equals(lat.getText()) && !"".equals(lon.getText())) {
                if ((Math.abs(Double.valueOf(lat.getText())) > Location.VALID_LATITUDE)
                        || (Math.abs(Double.valueOf(lon.getText())) > Location.VALID_LONGITUDE)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Invalid Latitude or Longitude, please enter a valid location.", ButtonType.CLOSE);
                    alert.show();
                } else {
                    MapOptions options = new MapOptions();

                    //set up the center location for the map
                    LatLong center = new LatLong(Double.valueOf(lat.getText()), Double.valueOf(lon.getText()));

                    options.center(center)
                            .zoom(9)
                            .overviewMapControl(false)
                            .panControl(false)
                            .rotateControl(false)
                            .scaleControl(false)
                            .streetViewControl(false)
                            .zoomControl(false)
                            .mapType(MapTypeIdEnum.TERRAIN);

                    map = mapView.createMap(options);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        //set up the center location for the map
        LatLong center = new LatLong(33.777553, -84.396112);

        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);

        locations.forEach(MapController::addMarker);
    }
    public static void addMarker(Location location) {
        String message = "";
        List<WaterReport> wrep = SortReports.filterWaterReportsByLocation(location);
        if (!wrep.isEmpty()) {
            WaterReport watRep = wrep.get(0);
            message += "Water Type: " + watRep.getWaterType()
                    + "<br /> Water Condition: " + watRep.getWaterCondition()
                    + "<br />";
        } else {
                message += "Water Type: Information Unavailable." +
                        "<br /> Water Condition: Information Unavailable.<br />";
        }
        List<QualityReport> qrep = SortReports.filterQualityReportsByLocation(location);
        if (!qrep.isEmpty()) {
            QualityReport quaRep = qrep.get(0);
            message += "Overall Condition: " + quaRep.getWaterCondition()
                    + "<br /> Virus PPM: " + quaRep.getVirusPPM()
                    + "<br /> Contaminant PPM: " + quaRep.getContaminantPPM();
        } else {
            message += "Overall Condition: Information Unavailable."
                    + "<br /> Virus PPM: Information Unavailable."
                    + "<br /> Contaminant PPM: Information Unavailable.";
        }

        MarkerOptions markerOptions = new MarkerOptions();
        LatLong loc = new LatLong(location.getLatitude(), location.getLongitude());
        markerOptions.position(loc)
                .visible(Boolean.TRUE);

        Marker marker = new Marker(markerOptions);
        String finalMessage = message;
        map.addUIEventHandler(marker,
                 UIEventType.click,
                 (JSObject obj) -> {
                     InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                     infoWindowOptions.content(finalMessage);

                     InfoWindow window = new InfoWindow(infoWindowOptions);
                     window.open(map, marker);
                 });

        map.addMarker(marker);
    }
}
