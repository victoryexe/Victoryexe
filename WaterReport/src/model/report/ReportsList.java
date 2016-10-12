package model.report;

import java.util.HashMap;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class ReportsList {
    private ReportsList() {

    }

    private static Map<Integer, Report> reportMap = new HashMap<Integer, Report>();

    /**
     * Given an report ID, returns the Report associated with it
     * @param rID the ID of the desired report
     * @return the Report associated with rID
     */
    public static Report getReport(Integer rID) {
        return reportMap.get(rID);
    }

    /**
     * Given an report ID, checks whether that report is contained in the reportMap
     * @param rID the ID of the desired report
     * @return true iff the Report is contained in reportMap
     */
    public static boolean containsReport(Integer rID) {
        return reportMap.contains(rID);
    }

    /**
     * Returns a List of all reports in reportMap
     * @return a List of all reports in the map
     */
    public static List<Report> getReportsList() {
        List<Report> reports = new List<>(reportMap.values());
        return reports;
    }

    public static void makeReport() {
        // TODO
    }
}
