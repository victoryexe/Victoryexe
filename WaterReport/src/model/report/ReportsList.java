package model.report;

import db.DB;
import model.Users.Manager;
import model.Users.User;
import model.Users.Worker;
import model.log.LogList;

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
        int rID = report.getReportID();
        if (qualityReportMap.containsKey(rID)) {
            return null;
        }
        qualityReportMap.put(report.getReportID(), report);
        return report;
    }
    public static int makeQualityReport(QualityReport report) {
        int rID = report.getReportID();
        if (qualityReportMap.containsKey(rID)) {
            return -1;
        }
        qualityReportMap.put(report.getReportID(), report);
        return report.getReportID();
    }
    public static WaterReport makeWaterReport(WaterReport report) {
        if (waterReportMap.containsKey(report.getReportID())) {
            return null;
        }
        waterReportMap.put(report.getReportID(), report);
        return report;
    }
    // Delete Report functionality has not been added yet, so IntelliJ
    // flags these as unused.

    /**
     * "Deletes" a WaterReport by setting its isRemoved marker to true
     * @param manager the Manager deleting a WaterReport
     * @param rID the report ID of the WaterReport to be deleted
     * @throws java.util.NoSuchElementException if the desired Report does
     * not exist or had previously been deleted
     * @return true iff the WaterReport was successfully deleted
     */
    public static boolean deleteWaterReport(Manager manager, int rID) {
        WaterReport deleted = waterReportMap.get(rID);
        DB.deleteReport(deleted);
        if ((deleted == null) || deleted.getIsDeleted()) {
            throw new java.util.NoSuchElementException("No WaterReport with "
                    + "the ID " + rID +" exists.");
        }
        LogList.makeDeletedReportEntry(manager, deleted);
        deleted.setIsDeleted();
        return true;
    }

    /**
     * "Deletes" a QualityReport by setting its isRemoved marker to true
     * @param manager the Manager deleting a WaterReport
     * @param rID the report ID of the QualityReport to be deleted
     * @throws java.util.NoSuchElementException if the desired Report does
     * not exist or had previously been deleted
     * @return true iff the QualityReport was successfully deleted
     */
    public static boolean deleteQualityReport(Manager manager, int rID) {
        Report deleted = qualityReportMap.get(rID);
        DB.deleteReport(deleted);
        if ((deleted == null) || deleted.getIsDeleted()) {
            throw new java.util.NoSuchElementException("No QualityReport with "
                    + "the ID " + rID +" exists.");
        }
        LogList.makeDeletedReportEntry(manager, deleted);
        deleted.setIsDeleted();
        return true;
    }

//    /**
//     * Restores a previously deleted WaterReport
//     * @param manager the manager restoring the Report
//     * @param rID the ID of the WaterReport to restore
//     * @return true iff the Report was successfully restored
//     */
//    public static boolean restoreWaterReport(Manager manager, int rID) {
//        WaterReport deleted = waterReportMap.get(rID);
//        if ((deleted == null) || !deleted.getIsDeleted()) {
//            throw new java.util.NoSuchElementException("No WaterReport with "
//                    + "the ID " + rID + " has been deleted.");
//        }
//        deleted.setIsDeleted();
//        return true;
//    }

//    /**
//     * Restores a previously deleted QualityReport
//     * @param manager the manager restoring the Report
//     * @param rID the ID of the WaterReport to restore
//     * @return true iff the Report was successfully restored
//     */
//    public static boolean restoreQualityReport(Manager manager, int rID) {
//        QualityReport deleted = qualityReportMap.get(rID);
//        if ((deleted == null) || !deleted.getIsDeleted()) {
//            throw new java.util.NoSuchElementException("No WaterReport with "
//                    + "the ID " + rID + " has been deleted.");
//        }
//        deleted.setIsDeleted();
//        return true;
//    }
}
