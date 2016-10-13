package model.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class SortReports {
    private static List<Report> reports;

    private static void updateReports() {
        reports = ReportsList.getReportsList();
    }

    public static List<Report> sortByReportID() {
        updateReports();
        List<Report> reportClone = new ArrayList<>(reports);
        Collections.sort(reportClone);
        return reportClone;
    }

    public static List<Report> sortByMostRecent() {
        updateReports();
        List<Report> reportClone = new ArrayList<>(reports);
        Collections.sort(reportClone, (Report r1, Report r2) -> {
            return r1.getTimestamp().compareTo(r2.getTimestamp());
        });
        return reportClone;
    }

    // TODO WaterType, WaterCondition, Location
}
