package model.report;

/**
 * Created by Alexandra on 10/12/2016.
 * Represents a Location
 */
public class Location implements Comparable<Location> {
    private static final double EARTH_RADIUS = 6373; // kilometers
    private static final double KM_TO_MI =  0.621371;
    private final double latitude;
    private final double longitude;

    /**
     * Makes a new Location object with the given params
     * @param latitude the Location's latitude
     * @param longitude the Location's longitude
     */
    public Location(double latitude, double longitude) {
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

    /**
     * Calculates the great circle distance between two Locations
     * @param l1 the first Location
     * @param l2 the second Location
     * @return the distance between the two Locations
     */
    public static double calculateDistance(Location l1, Location l2) {
        double lat1 = Math.toRadians(l1.getLatitude());
        double lat2 = Math.toRadians(l2.getLatitude());
        double long1 = Math.toRadians(l1.getLongitude());
        double long2 = Math.toRadians(l2.getLongitude());
        double latDiff = lat1 - lat2;
        double longDiff = long1 - long2;

        double a = Math.pow(Math.sin(latDiff / 2), 2) + (Math.cos(lat1)
                * Math.cos(lat2) * Math.pow(Math.sin((longDiff / 2)), 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * KM_TO_MI * c;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object obj) {
        // IntelliJ wants to simplify this entire method into one statement.
        // For readability, I prefer to keep this method as is.
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return (obj instanceof Location)
                && (this.latitude == ((Location) obj).latitude)
                && (this.longitude == ((Location) obj).longitude);
    }

    @Override
    public int hashCode() {
        return (97 * ((Double) latitude).hashCode())
                + (211 * ((Double) longitude).hashCode());
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
