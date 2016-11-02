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

    /**
     * Calculates the average virusPPM per month, where each month
     * is denoted by the array index
     * [0] January, [1] February, ... [11] December
     * @param loc the Location for which to generate data
     * @param radius the radius in miles that a Location must be relative
     * to @param loc to be included in the HistoricalReport
     * @param year the desired year
     * @return a double[] containing the average virusPPM per month
     */
    public static double[] generateHistoricalReportByVirusPPM(
            Location loc, double radius, int year) {
        reports = ReportsList.getQualityReportsList();
        List<List<Double>> reportsByMonth = new ArrayList<>(12);
        for (Report r : reports) {
            if (Location.calculateDistance(loc, r.getLocation()) <= radius
                    && r.getTimestamp().getYear() == year) { // check against params
                int index = r.getTimestamp().getMonthValue() - 1;
                if (reportsByMonth.get(index) == null) { // instantiate list
                    reportsByMonth.set(index, new LinkedList<>());
                }
                reportsByMonth.get(index).add(((QualityReport) r).getVirusPPM());
            }
        }

        double[] virusPPMByMonth = new double[12];
        for (int i = 0; i < reportsByMonth.size(); i++) {
            double sum = 0;
            for (int j = 0; j < reportsByMonth.get(i).size(); j++) {
                sum += reportsByMonth.get(i).get(j);
            }
            virusPPMByMonth[i] = sum / reportsByMonth.get(i).size();
        }
        return virusPPMByMonth;
    }

    /**
     * Calculates the average contaminant per month, where each month
     * is denoted by the array index
     * [0] January, [1] February, ... [11] December
     * @param loc the Location for which to generate data
     * @param radius the radius in miles that a Location must be relative
     * to @param loc to be included in the HistoricalReport
     * @param year the desired year
     * @return a double[] containing the average contaminantPPM per month
     */
    public static double[] generateHistoricalReportByContaminantPPM(
            Location loc, double radius, int year) {
        reports = ReportsList.getQualityReportsList();
        List<List<Double>> reportsByMonth = new ArrayList<>(12);
        for (Report r : reports) {
            if (Location.calculateDistance(loc, r.getLocation()) <= radius
                    && r.getTimestamp().getYear() == year) { // check against params
                int index = r.getTimestamp().getMonthValue() - 1;
                if (reportsByMonth.get(index) == null) { // instantiate list
                    reportsByMonth.set(index, new LinkedList<>());
                }
                reportsByMonth.get(index).add(((QualityReport) r).getContaminantPPM());
            }
        }

        double[] contaminantPPMByMonth = new double[12];
        for (int i = 0; i < reportsByMonth.size(); i++) {
            double sum = 0;
            for (int j = 0; j < reportsByMonth.get(i).size(); j++) {
                sum += reportsByMonth.get(i).get(j);
            }
            contaminantPPMByMonth[i] = sum / reportsByMonth.get(i).size();
        }
        return contaminantPPMByMonth;
    }
}
