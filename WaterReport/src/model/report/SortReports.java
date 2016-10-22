package model.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class SortReports {
    private static List<Report> reports;

    private static void updateReports() {
        reports = ReportsList.getWaterReportsList();
        reports.addAll(ReportsList.getQualityReportsList());
    }

    /**
     * Sorts all reports by their ReportID
     * @return a copy  of a list sorted by their ReportID
     */
    public static List<Report> sortByReportID() {
        updateReports();
        List<Report> reportClone = new ArrayList<>(reports);
        Collections.sort(reportClone);
        return reportClone;
    }

    /**
     * Sorts all reports by most recently added to oldest reports
     * @return a copy of a list sorted by newest to oldest reports
     */
    public static List<Report> sortByMostRecent() {
        updateReports();
        List<Report> reportClone = new ArrayList<>(reports);
        Collections.sort(reportClone, (Report r1, Report r2) -> {
            return -1 * r1.getTimestamp().compareTo(r2.getTimestamp());
        });
        return reportClone;
    }

    /**
     * Given a WaterType(s), returns a Set of all WaterReports of that
     * WaterType
     * @param type one or more WaterTypes to filter by
     * @return a Set of WaterReports of the desired WaterType(s)
     */
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

    /**
     * Given a WaterCondition(s), finds all WaterReports with the given
     * WaterCondition(s)
     * @param condition one or more WaterConditions to filter by
     * @return a Set of all WaterReports of the desired WaterCondition(s)
     */
    public static Set<WaterReport> filterByWaterCondition(WaterCondition ... condition) {
        updateReports();
        Set<WaterReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            if (r instanceof WaterReport) { // type check
                for (int i = 0; i < condition.length; i++) {
                    // checks if the report's water condition is a param
                    if (((WaterReport) r).getWaterCondition().equals(condition[i])) {
                        reportSet.add((WaterReport) r);
                        i = condition.length; // exit array check
                    }
                }
            }
        }
        return reportSet;
    }

    /**
     * Given an OverallCondition(s), finds all QualityReports with those
     * given conditions
     * @param condition one or more OverallConditions to filter by
     * @return a Set of all QualityReports of the desired OverallCondition(s)
     */
    public static Set<QualityReport> filterByOverallCondition(OverallCondition ... condition) {
        updateReports();
        Set<QualityReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            if (r instanceof QualityReport) {
                for (int i = 0; i < condition.length; i++) {
                    if (((QualityReport) r).getWaterCondition().equals(condition[i])) {
                        reportSet.add((QualityReport) r);
                        i = condition.length; // exit param check
                    }
                }
            }
        }
        return reportSet;
    }

    /**
     * Finds all WaterReports associated with a given Location(s)
     * @param loc one or more Locations to filter by
     * @return a List containing all WaterReports associated with @param loc
     */
    public static List<WaterReport> filterWaterReportsByLocation(Location ... loc) {
        updateReports();
        List<WaterReport> waterReports = new ArrayList<>();
        for (Report r : reports) {
            if (r instanceof WaterReport) {
                boolean added = false;
                for (int i = 0; i < loc.length && !added; i++) {
                    if (r.getLocation().equals(loc[i])) {
                        waterReports.add((WaterReport) r);
                        added = true; // prevents duplicates and exits
                    }
                }
            }
        }
        return waterReports;
    }

    /**
     * Finds all QualityReports associated with a given Location(s)
     * @param loc one or more Locations to filter by
     * @return a List containing all QualityReports associated with @param loc
     */
    public static List<QualityReport> filterQualityReportsByLocation(Location ... loc) {
        updateReports();
        List<QualityReport> qualityReports = new ArrayList<>();
        for (Report r : reports) {
            if (r instanceof QualityReport) {
                boolean added = false;
                for (int i = 0; i < loc.length && !added; i++) {
                    if (r.getLocation().equals(loc[i])) {
                        qualityReports.add((QualityReport) r);
                        added = true; // prevents duplicates and exits
                    }
                }
            }
        }
        return qualityReports;
    }

    /**
     * Finds all Reports associated with a given Location(s)
     * @param loc one or more Locations to filter by
     * @return a List containing all Reports associated with @param loc
     */
    public static List<Report> filterReportsByLocation(Location ... loc) {
        updateReports();
        List<Report> reports = new LinkedList<>(filterWaterReportsByLocation(loc));
        reports.addAll(filterQualityReportsByLocation(loc));
        return reports;
    }
}
