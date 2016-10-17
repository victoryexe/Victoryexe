package model.report;

import model.Users.Worker;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class QualityReport extends Report{
    private static int reportCount = 0;
    private OverallCondition waterCondition;
    private int virusPPM;
    private int contaminantPPM;

    /**
     * Makes a new QaulityReport with the given params and a timestamp
     * @param worker the Worker submitting a report
     * @param location the location of the water source
     * @param condition the condition of the water
     * @param virusPPM the virus PPM of the water
     * @param contaminantPPM the contaminant PPM of this source
     */
    public QualityReport(Worker worker, Location location,
                         OverallCondition condition, int virusPPM, int contaminantPPM) {
        super(worker, location);
        waterCondition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        setRID(reportCount++);
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
    public int getVirusPPM() {
        return virusPPM;
    }

    /**
     * Gets the contaminant PPM of this source
     * @return the contaminant PPM of this source
     */
    public int getContaminantPPM() {
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
    public void setVirusPPM(int virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Sets this source's contaminantPPM to the provided one
     * @param contaminantPPM the new contaminantPPM of this source
     */
    public void setContaminantPPM(int contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
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
}