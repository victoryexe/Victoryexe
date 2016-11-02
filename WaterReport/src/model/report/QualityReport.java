package model.report;

import model.Users.Worker;

/**
 * Created by Alexandra on 10/12/2016.
 * Represents a Water Quality Report
 */
public class QualityReport extends Report{
    private static int reportCount = 0;
    private final int rID;
    private OverallCondition waterCondition;
    private double virusPPM;
    private double contaminantPPM;

    /**
     * Makes a new QaulityReport with the given params and a timestamp
     * @param worker the Worker submitting a report
     * @param location the location of the water source
     * @param condition the condition of the water
     * @param virusPPM the virus PPM of the water
     * @param contaminantPPM the contaminant PPM of this source
     */
    public QualityReport(Worker worker, Location location,
                         OverallCondition condition, double virusPPM, double contaminantPPM) {
        super(worker, location);
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

    /**
     * Sets this source's overall condition to the provided one
     * @param condition the new OverallCondition of this water
     */
    public void setOverallCondition(OverallCondition condition) {
        waterCondition = condition;
    }

    /**
     * Sets this source's virusPPM to the provided one
     * @param virusPPM the new virusPPM of this source
     */
    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Sets this source's contaminantPPM to the provided one
     * @param contaminantPPM the new contaminantPPM of this source
     */
    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    @Override
    public int getReportID() {
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
        if (!(obj instanceof QualityReport)) {
            return false;
        }
        return ((QualityReport) obj).getReportID() == this.getReportID();
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
