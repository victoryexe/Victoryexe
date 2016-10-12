package model.report;

import model.Users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Alexandra on 10/12/2016.
 */
public abstract class Report implements Comparable<Report> {
    private static int reportCounter = 0; // TODO Stringify
    private LocalDateTime timestamp;
    private int rID;
    private User submitterID;
    private Location location;

    /**
     * Creates a report, using the local machine's date and time, the user
     * responsible for the report, and the water's location
     * @param submitter the User who created this report
     * @param location the location of this source
     */
    public Report(User submitter, Location location) {
        timestamp = LocalDateTime.now();
        rID = reportCounter++;
        submitterID = submitter;
        this.location = location;
    }

//    private String generateReportID() {
//        String modified = "";
//        boolean changed = false;
//        for (int i = 0; i < reportCounter.length(); i++) {
//            char current = reportCounter.charAt(i);
//            if (current < 122 && !changed) {
//                modified += ++current;
//                changed = true;
//            } else {
//                modified += current;
//            }
//            if (current == 122 && !changed) {
//                modified += "A";
//                changed = true;
//            }
//        }
//        reportCounter = modified;
//        return modified;
//    }

    /**
     * Gets the id of this report
     * @return the id of this report
     */
    public int getReportID() {
        return rID;
    }

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

    @Override
    public int hashCode() {
        return rID;
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
