package model.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class SortReports {
    private static List<Report> reports;

    private static void updateReports() {
        reports = ReportsList.getWaterReportsList();
        reports.addAll(ReportsList.getQualityReportsList());
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

    public static Set<WaterReport> filterByWaterType(WaterType ... type) {
        updateReports();
        Set<WaterReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            if (r instanceof WaterReport) { // type check
                for (int i = 0; i < type.length; i++) {
                    // checks if the report's water type is a param
                    if (((WaterReport) r).getWaterType().equals(type[i])) {
                        reportSet.add((WaterReport) r);
                        i = type.length; // exit array check
                    }
                }
            }
        }
        return reportSet;
    }

    public static Set<WaterReport> filterByWaterCondition(WaterCondition ... condition) {
        updateReports();
        Set<WaterReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            if (r instanceof WaterReport) { // type check
                for (int i = 0; i < condition.length; i++) {
                    // checks if the report's water type is a param
                    if (((WaterReport) r).getWaterType().equals(condition[i])) {
                        reportSet.add((WaterReport) r);
                        i = condition.length; // exit array check
                    }
                }
            }
        }
        return reportSet;
    }

    // TODO Location, OverallCondition
}
