package model.report;

import model.Users.Worker;
import model.registration.UserList;
/**
 * Created by Alexandra on 10/12/2016.
 * Represents a Water Quality Report
 */
public class QualityReport extends Report {
    private static int reportCount = 0;
    private final int rID;
    private final OverallCondition waterCondition;
    private final double virusPPM;
    private final double contaminantPPM;

    /**
     * Makes a new QualityReport with the given params and a timestamp
     * @param worker the Worker submitting a report
     * @param location the location of the water source
     * @param condition the condition of the water
     * @param virusPPM the virus PPM of the water
     * @param contaminantPPM the contaminant PPM of this source
     */
    public QualityReport(Worker worker, Location location,
                              OverallCondition condition, double virusPPM,
                              double contaminantPPM) {
        super(worker, location);
        waterCondition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        rID = reportCount++;
    }
    /**
     * Makes a new QualityReport with the given params and a timestamp
     * @param tStamp String format of Time Stamp
     * @param worker the Worker submitting a report
     * @param location the location of the water source
     * @param condition the condition of the water
     * @param virusPPM the virus PPM of the water
     * @param contaminantPPM the contaminant PPM of this source
     */
    public QualityReport(String tStamp ,Worker worker, Location location,
                         OverallCondition condition, double virusPPM,
                         double contaminantPPM) {

        super(worker, location, tStamp);
        waterCondition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        rID = reportCount++;
    }

    /**
     * Gets the overall condition of this water source
     * @return the OverallCondition of this source
     */
    public OverallCondition getWaterCondition() {
        return waterCondition;
    }

    /**
     * Gets the virus PPM of this source
     * @return the virus PPM of this source
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * Gets the contaminant PPM of this source
     * @return the contaminant PPM of this source
     */
    public double getContaminantPPM() {
        return contaminantPPM;
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
        return (obj instanceof QualityReport)
                && (((QualityReport) obj).getReportID() == this.getReportID());
    }

    @Override
    public int hashCode() {
        return getReportID();
    }

    @Override
    public String toString() {
        return "Quality Report #Q" + getReportID();
    }
}
