package model.report;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class Location implements Comparable<Location> {
    private double latitude;
    private double longitude;
    // TODO Requires GMap Integration

    /**
     * Makes a new Location object with the given params
     * @param latitude the Location's latitude
     * @param longitude the Location's longitude
     */
    public Location (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the latitude associated with this Location
     * @return the latitude associated with this Location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude associated with this Location
     * @return the longitude associated with this Location
     */
    public double getLongitude() {
        return longitude;
    }

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

    @Override
    public int compareTo(Location loc) {
        if (this.latitude != loc.latitude) {
            return Double.compare(this.latitude, loc.latitude);
        } else {
            return Double.compare(this.longitude, loc.longitude);
        }
    }
}
