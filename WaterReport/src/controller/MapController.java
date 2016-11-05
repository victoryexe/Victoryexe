package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import fxapp.Main;
import javafx.fxml.Initializable;
import javafx.stage.Window;
import model.report.*;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by grizz on 10/19/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class MapController implements Initializable, MapComponentInitializedListener {

    private final GoogleMapView mapView;

    private static GoogleMap map;

    private final List<Location> locations = new ArrayList<>();

    public MapController(GoogleMapView mapView) {
        this.mapView = mapView;
        List<Report> qreports = ReportsList.getQualityReportsList();
        List<Report> wreports = ReportsList.getWaterReportsList();
        locations.addAll(wreports.stream().map(Report::getLocation).collect(Collectors.toList()));
        qreports.stream().filter(rep -> !locations.contains(rep.getLocation())).forEach(rep -> locations.add(rep.getLocation()));
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
        if (wrep.size() != 0) {
            WaterReport watRep = wrep.get(0);
            message += "Water Type: " + watRep.getWaterType().name()
                    + "<br /> Water Condition: " + watRep.getWaterCondition().name()
                    + "<br />";
        } else {
                message += "Water Type: Information Unavailable." +
                        "<br /> Water Condition: Information Unavailable.<br />";
        }
        List<QualityReport> qrep = SortReports.filterQualityReportsByLocation(location);
        if (qrep.size() != 0) {
            QualityReport quaRep = qrep.get(0);
            message += "Overall Condition: " + quaRep.getWaterCondition().name()
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
