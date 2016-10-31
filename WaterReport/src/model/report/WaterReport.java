package model.report;

import model.Users.User;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class WaterReport extends Report {
    private static int reportCount = 0;
    private WaterType waterType;
    private WaterCondition waterCondition;

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
        setRID(reportCount++);
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

    /**
     * Sets this source's WaterType to the provided one
     * @param type the updated type of this water
     */
    public void setWaterType(WaterType type) {
        waterType = type;
    }

    /**
     * Sets this source's WaterCondition to the provided one
     * @param condition the updated condition of this water
     */
    public void setWaterCondition(WaterCondition condition) {
        waterCondition = condition;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WaterReport)) {
            return false;
        }
        return ((WaterReport) obj).getReportID() == this.getReportID();
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
