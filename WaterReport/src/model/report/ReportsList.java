package model.report;

import model.Users.User;
import model.Users.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandra on 10/12/2016.
 * A helper class that stores all Reports in the system as ReportID - Report
 * entries. Also contains helper methods for making, updating, deleting, and
 * check for existence of Reports.
 */
public class ReportsList {

    private static final Map<Integer, WaterReport> waterReportMap =
            new HashMap<>();
    private static final Map<Integer, QualityReport> qualityReportMap =
            new HashMap<>();

    /**
     * Returns a List of all reports in waterReportMap
     * @return a List copy of all reports in the map
     */
    public static List<Report> getWaterReportsList() {
        return new ArrayList<>(waterReportMap.values());
    }

    /**
     * Makes a new WaterReport and adds it to the map
     * @param user the user who submitted the report
     * @param location the location of the water source
     * @param waterType the type of the water
     * @param waterCondition the condition of the water
     * @return the WaterReport made, or null if none was made
     */
    public static WaterReport makeReport(User user, Location location,
                                         WaterType waterType,
                                         WaterCondition waterCondition) {
        WaterReport report = new WaterReport(user, location, waterType,
                waterCondition);
        if (waterReportMap.containsKey(report.getReportID())) {
            return null;
        }
        waterReportMap.put(report.getReportID(), report);
        return report;
    }

    /**
     * Returns a List of all QualityReports
     * @return a List copy of all QualityReports
     */
    public static List<Report> getQualityReportsList() {
        return new ArrayList<>(qualityReportMap.values());
    }

    /**
     * Makes a new QualityReport and adds it to the map
     * @param worker the worker submitting the report
     * @param location the location of the water
     * @param condition the condition of the water
     * @param virusPPM the virusPPM of the water
     * @param contaminantPPM the contaminantPPM of the water
     * @return the QualityReport made or null if none was made
     */
    public static QualityReport makeReport(Worker worker, Location location,
                                           OverallCondition condition,
                                           double virusPPM,
                                           double contaminantPPM) {
        QualityReport report = new QualityReport(worker, location, condition,
                virusPPM, contaminantPPM);
        if (qualityReportMap.containsKey(report.getReportID())) {
            return null;
        }
        qualityReportMap.put(report.getReportID(), report);
        return report;
    }

    // TODO Delete Reports
}
