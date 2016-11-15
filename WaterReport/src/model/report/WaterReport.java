package model.report;

import model.Users.User;

import java.time.LocalDateTime;

/**
 * Created by Alexandra on 10/12/2016.
 * Represents a Water Availability Report
 */
public class WaterReport extends Report {
    private static int reportCount = 0;
    private final int rID;
    private final WaterType waterType;
    private final WaterCondition waterCondition;

    // Constructor is used to load data from the DB

    /**
     * Creates a report using a given date and time, user responsible
     * for the report, water's location
     * @param submitter the User who created this report
     * @param location the location of this source
     * @param waterType the WaterType of this source
     * @param waterCondition the WaterCondition of this report
     * @param rID this report's ID number
     * @param timestamp the date and time of submission for report
     */
    public WaterReport(User submitter, Location location, WaterType waterType,
                       WaterCondition waterCondition, int rID,
                       LocalDateTime timestamp) {
        super(submitter, location, timestamp);
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.rID = rID;
    }

    /**
     * Creates a WaterReport with the given parameters and a timestamp
     * @param submitter the User who submitted this report
     * @param location the location of this source
     * @param waterType the type of water at this source
     * @param waterCondition the condition of the water at this source
     */
    public WaterReport(User submitter, Location location, WaterType waterType,
                       WaterCondition waterCondition) {
        super(submitter, location);
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        rID = reportCount++;
    }

    /**
     * Gets the WaterType of this water source
     * @return the WaterType of this source
     */
    public WaterType getWaterType() {
        return waterType;
    }

    /**
     * Gets the WaterCondition of the water this report is about
     * @return the WaterCondition of this source
     */
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    @Override
    public int getReportID() {
        return rID;
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
        return (obj instanceof WaterReport)
                && (((WaterReport) obj).getReportID() == this.getReportID());
    }

    @Override
    public int hashCode() {
        return getReportID();
    }

    @Override
    public String toString() {
        return "Availability Report #W" + getReportID();
    }
}
