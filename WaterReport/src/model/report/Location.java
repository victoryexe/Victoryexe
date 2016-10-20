package model.report;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class Location implements Comparable<Location> {
    private double latitude;
    private double longitude;
    public Location (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    // TODO Will require GMap integration

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        return this.latitude == ((Location) obj).latitude
                && this.longitude == ((Location) obj).longitude;
    }

    @Override
    public int hashCode() {
        return 97 * ((Double) latitude).hashCode()
                + 211 * ((Double) longitude).hashCode();
    }

    public int compareTo(Location loc) {
        if (this.latitude != loc.latitude) {
            return Double.compare(this.latitude, loc.latitude);
        } else {
            return Double.compare(this.longitude, loc.longitude);
        }
    }
}
