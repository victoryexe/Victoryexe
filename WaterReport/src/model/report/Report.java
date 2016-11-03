package model.report;

import model.Users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
     * Creates a report, using the local machine's date and time, the user
     * responsible for the report, and the water's location
     * @param submitter the User who created this report
     * @param location the location of this source
     */
    public Report(User submitter, Location location) {
        timestamp = LocalDateTime.now();
        submitterID = submitter;
        this.location = location;
    }

    /**
     * Gets the id of this report
     * @return the id of this report
     */
    public abstract int getReportID();

    /**
     * Gets the User that submitted this report
     * @return the User that submitted this report
     */
    public User getSubmitterID() {
        return submitterID;
    }

    /**
     * Gets the location which this report corresponds to
     * @return the location of the water source
     */
    public Location getLocation() {
        return location;
    }

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
    public String getOther() { return other; }

    /**
     * Sets the string representative of other water source
     * to the one specified
     * @param other the custom water source entered by user
     */
    public void setOther(String other) { this.other = other; }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Report)) {
            return false;
        }
        return this.getReportID() == ((Report) obj).getReportID();
    }

    @Override
    public int compareTo(Report r) {
        return this.getReportID() - r.getReportID();
    }
}
