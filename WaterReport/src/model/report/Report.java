package model.report;

import model.Users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;

/**
 * Created by Alexandra on 10/12/2016.
 * Represents a Report
 */
public abstract class Report implements Comparable<Report> {
    private final LocalDateTime timestamp;
    private final User submitterID;
    private String other;
    private boolean isDeleted;
    private final Location location;

    /**
     * Creates a report using a given date and time, user responsible
     * for the report, water's location
     * @param submitter the User who created this report
     * @param location the location of this source
     * @param timestamp the date and time of submission for report
     */
    Report(User submitter, Location location, LocalDateTime timestamp) {
        this.timestamp = timestamp;
        submitterID = submitter;
        this.location = location;
    }
    Report(User submitter, Location location, String tStamp) {
        this(submitter, location, LocalDateTime.parse(tStamp));
    }

    /**
     * Creates a report, using the local machine's date and time, the user
     * responsible for the report, and the water's location
     * @param submitter the User who created this report
     * @param location the location of this source
     */
    Report(User submitter, Location location) {
        this(submitter, location, LocalDateTime.now());
    }
    /**
     * Gets the id of this report
     * @return the id of this report
     */
    public abstract int getReportID();

    /**
     * Gets the name of the User who submitted this report
     * @return the name of the User who submitted this report
     */
    public String getSubmitterName() { return submitterID.getName();}
    /**
     * Gets the name of the User who submitted this report
     * @return the email of the User who submitted this report
     */
    public String getSubmitterID() { return submitterID.getEmail();}

    /**
     * Gets the location which this report corresponds to
     * @return the location of the water source
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the coordinates of this Location
     * @return the coordinates as a String
     */
    public String getCoordinates() { return location.toString();}

    /**
     * Gets the date this report was submitted on
     * @return the LocalDate this report was submitted one
     */
    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }

    /**
     * Gets the time that this report was submitted at
     * @return the LocalTime this report was submitted at
     */
    public LocalTime getTime() {
        return timestamp.toLocalTime();
    }

    /**
     * Gets the day and time this report was submitted on
     * @return the LocalDateTime this report was submitted at
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the water type should the user have entered WaterType.OTHER
     * @return the string representing the WaterType.OTHER water source
     */
    public String getOther() {
        return other;
    }

    /**
     * Sets the string representative of other water source
     * to the one specified
     * @param other the custom water source entered by user
     */
    public void setOther(String other) {
        this.other = other;
    }

    /**
     * Gets this Report's deleted status
     * @return true if this Report has been deleted, false otherwise
     */
    public boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * Flips this Report's isDeleted marker from true to false or false to true
     */
    public void setIsDeleted() {
        isDeleted = !isDeleted;
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
        return (obj instanceof Report)
                && (this.getReportID() == ((Report) obj).getReportID());
    }

    @Override
    public int compareTo(Report r) {
        return this.getReportID() - r.getReportID();
    }
}
