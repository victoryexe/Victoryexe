package model.report;

/**
 * Created by Alexandra on 10/12/2016.
 * Temp class
 */
public class Location {
    // TODO
    //Temporary constructor & getters
    private String latitude;
    private String longitude;
    public Location (String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    // Will require GMap integration
}
