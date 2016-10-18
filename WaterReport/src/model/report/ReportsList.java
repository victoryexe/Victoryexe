package model.report;

import model.Users.User;
import model.Users.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class ReportsList {
    private ReportsList() {

    }

    private static Map<Integer, Report> waterReportMap = new HashMap<Integer, Report>();
    private static Map<Integer, Report> qualityReportMap = new HashMap<Integer, Report>();

    /**
     * Given an report ID, returns the Report associated with it
     * @param rID the ID of the desired report
     * @return the Report associated with rID
     */
    public static Report getWaterReport(Integer rID) {
        return waterReportMap.get(rID);
    }

    /**
     * Given an report ID, checks whether that report is contained in the waterReportMap
     * @param rID the ID of the desired report
     * @return true iff the Report is contained in waterReportMap
     */
    public static boolean containsWaterReport(Integer rID) {
        return waterReportMap.containsKey(rID);
    }

    /**
     * Returns a List of all reports in waterReportMap
     * @return a List of all reports in the map
     */
    public static List<Report> getWaterReportsList() {
        List<Report> reports = new ArrayList<>(waterReportMap.values());
        return reports;
    }

    /**
     * Makes a new WaterReport and adds it to the map
     * @param user the user who submitted the report
     * @param location the location of the water source
     * @param watertype the type of the water
     * @param waterCondition the condition of the water
     */
    public static void makeReport(User user, Location location,
                                  WaterType watertype, WaterCondition waterCondition) {
        WaterReport report = new WaterReport(user, location, watertype, waterCondition);
        waterReportMap.put(report.getReportID(), report);
    }

    /**
     * Retrieves a QualityReport from the map, or null if it does not exist
     * @param rID the ID of the desired QualityReport
     * @return the corresponding QualityReport
     */
    public static Report getQualityReport(Integer rID) {
        return qualityReportMap.get(rID);
    }

    /**
     * Checks whether the map contains a QualityReport with the given ID
     * @param rID the ID of the report in question
     * @return true iff the report is contained in the map
     */
    public static boolean containsQualityReport(Integer rID) {
        return qualityReportMap.containsKey(rID);
    }

    /**
     * Returns a List of all QualityReports
     * @return a List of all QualityReports
     */
    public static List<Report> getQualityReportsList() {
        List<Report> reports = new ArrayList<>(qualityReportMap.values());
        return reports;
    }

    /**
     * Makes a new QualityReport and adds it to the map
     * @param worker the worker submitting the report
     * @param location the location of the water
     * @param condition the condition of the water
     * @param virusPPM the virusPPM of the water
     * @param contaminantPPM the contaminantPPM of the water
     */
    public static void makeReport(Worker worker, Location location,
                                  OverallCondition condition, int virusPPM, int contaminantPPM) {
        QualityReport report = new QualityReport(worker, location, condition, virusPPM, contaminantPPM);
        qualityReportMap.put(report.getReportID(), report);
    }
}
