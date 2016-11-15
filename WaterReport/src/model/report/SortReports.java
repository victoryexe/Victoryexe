package model.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexandra on 10/12/2016.
 * A class with utility methods, mostly regarding sorting Reports, which
 * are taken from the ReportsList class.
 */
public class SortReports {
    private static List<Report> reports;

    /**
     * Updates @code reports by pulling from ReportsList
     */
    private static void updateReports() {
        reports = ReportsList.getWaterReportsList();
        reports.addAll(ReportsList.getQualityReportsList());

        Iterator itr = reports.iterator();
        while (itr.hasNext()) { // remove reports marked as delete
            if (((Report) itr.next()).getIsDeleted()) {
                itr.remove();
            }
        }
    }

    /**
     * Updates @code reports with non-deleted WaterReports
     */
    private static void updateWaterReports() {
        reports = ReportsList.getWaterReportsList();

        Iterator itr = reports.iterator();
        while (itr.hasNext()) { // don't sort reports that are deleted
            if (((Report) itr.next()).getIsDeleted()) {
                itr.remove();
            }
        }
    }

    /**
     * Updates @code reports with non-deleted QualityReports
     */
    private static void updateQualityReports() {
        reports = ReportsList.getQualityReportsList();

        Iterator itr = reports.iterator();
        while (itr.hasNext()) { // remove deleted reports
            if (((Report) itr.next()).getIsDeleted()) {
                itr.remove();
            }
        }
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
        Collections.sort(reportClone, (Report r1, Report r2) ->
                -1 * r1.getTimestamp().compareTo(r2.getTimestamp()));
        return reportClone;
    }

    /**
     * Given a WaterType(s), returns a Set of all WaterReports that have
     * at least one matching WaterType
     * @param type one or more WaterTypes to filter by
     * @return a Set of WaterReports of the desired WaterType(s) or an
     * empty set if no WaterReports match
     */
    public static Set<WaterReport> filterByWaterType(WaterType ... type) {
        updateWaterReports();
        Set<WaterReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            boolean added = false;
            for (int i = 0; (i < type.length) && !added; i++) {
                // checks if the report's water type is a param
                if (((WaterReport) r).getWaterType().equals(type[i])) {
                    reportSet.add((WaterReport) r);
                    added = true;
                }
            }
        }
        return reportSet;
    }

    /**
     * Given a WaterCondition(s), finds all WaterReports with at least one given
     * WaterCondition(s)
     * @param condition one or more WaterConditions to filter by
     * @return a Set of all WaterReports of the desired WaterCondition(s) or
     * an empty Set if no WaterReports match
     */
    public static Set<WaterReport> filterByWaterCondition(WaterCondition ...
                                                                  condition) {
        updateWaterReports();
        Set<WaterReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            boolean added = false;
            for (int i = 0; (i < condition.length) && !added; i++) {
                // checks if the report's water condition is a param
                if (((WaterReport) r).getWaterCondition()
                        .equals(condition[i])) {
                    reportSet.add((WaterReport) r);
                    added = true; // exit innermost loop early
                }
            }
        }
        return reportSet;
    }

    /**
     * Given an OverallCondition(s), finds all QualityReports with at least one
     * given conditions
     * @param condition one or more OverallConditions to filter by
     * @return a Set of all QualityReports of the desired OverallCondition(s)
     * or an empty Set if no QualityReports match
     */
    public static Set<QualityReport> filterByOverallCondition(
            OverallCondition ... condition) {
        updateQualityReports();
        Set<QualityReport> reportSet = new HashSet<>();
        for (Report r : reports) {
            boolean added = false;
            for (int i = 0; (i < condition.length) && !added; i++) {
                if (((QualityReport) r).getWaterCondition()
                        .equals(condition[i])) {
                    reportSet.add((QualityReport) r);
                    added = true;
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
    public static List<WaterReport> filterWaterReportsByLocation(
            Location ... loc) {
        updateWaterReports();
        List<WaterReport> waterReports = new ArrayList<>();
        for (Report r : reports) {
            boolean added = false;
            for (int i = 0; (i < loc.length) && !added; i++) {
                if (r.getLocation().equals(loc[i])) {
                    waterReports.add((WaterReport) r);
                    added = true; // prevents duplicates and exits
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
    public static List<QualityReport> filterQualityReportsByLocation(
            Location ... loc) {
        updateQualityReports();
        List<QualityReport> qualityReports = new ArrayList<>();
        for (Report r : reports) {
            boolean added = false;
            for (int i = 0; (i < loc.length) && !added; i++) {
                if (r.getLocation().equals(loc[i])) {
                    qualityReports.add((QualityReport) r);
                    added = true; // prevents duplicates and exits
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
        List<Report> reports =
                new LinkedList<>(filterWaterReportsByLocation(loc));
        reports.addAll(filterQualityReportsByLocation(loc));
        return reports;
    }

    /**
     * Calculates the average virusPPM per month, where each month
     * is denoted by the array index
     * [0] January, [1] February, ... [11] December
     * @param loc the Location for which to generate data
     * @param radius the radius in miles that a Location must be relative
     * to loc to be included in the HistoricalReport
     * @param year the desired year
     * @return a double[] containing the average virusPPM per month
     */
    public static double[] generateHistoricalReportByVirusPPM(
            Location loc, double radius, int year) {
        updateQualityReports();
        // There is no way to resolve this unchecked cast since Java
        // does not allow instantiation of generic arrays.
        // The "magic number" 12 is because there are 12 months in a year.
        List<Double>[] reportsByMonth = (List<Double>[]) new List[12];

        //noinspection Convert2streamapi
        for (Report r : reports) {
            if ((Location.calculateDistance(loc, r.getLocation()) <= radius)
                    && (r.getTimestamp().getYear() == year)) {
                // check against params
                int index = r.getTimestamp().getMonthValue() - 1;
                if (reportsByMonth[index] == null) {
                    reportsByMonth[index] = new ArrayList<>();
                }
                reportsByMonth[index].add(((QualityReport) r)
                        .getVirusPPM());
            }
        }

        double[] virusPPMByMonth = new double[reportsByMonth.length];
        for (int i = 0; i < reportsByMonth.length; i++) {
            double sum = 0;
            if (reportsByMonth[i] == null) {
                virusPPMByMonth[i] = sum;
            } else {
                for (int j = 0; j < reportsByMonth[i].size(); j++) {
                    sum += reportsByMonth[i].get(j);
                }
                virusPPMByMonth[i] = sum / reportsByMonth[i].size();
            }
        }
        return virusPPMByMonth;
    }

    /**
     * Calculates the average contaminant per month, where each month
     * is denoted by the array index
     * [0] January, [1] February, ... [11] December
     * @param loc the Location for which to generate data
     * @param radius the radius in miles that a Location must be relative
     * to loc to be included in the HistoricalReport
     * @param year the desired year
     * @return a double[] containing the average contaminantPPM per month
     */
    public static double[] generateHistoricalReportByContaminantPPM(
            Location loc, double radius, int year) {
        updateQualityReports();
        // There is no way to resolve this unchecked cast since Java
        // does not allow instantiation of generic arrays.
        // The "magic number" 12 is because there are 12 months in a year.
        List<Double>[] reportsByMonth = (List<Double>[]) new List[12];

        //noinspection Convert2streamapi
        for (Report r : reports) {
            if ((Location.calculateDistance(loc, r.getLocation()) <= radius)
                    && (r.getTimestamp().getYear() == year)) {
                // check against params
                int index = r.getTimestamp().getMonthValue() - 1;
                if (reportsByMonth[index] == null) {
                    reportsByMonth[index] = new ArrayList<>();
                }
                reportsByMonth[index].add(((QualityReport) r)
                        .getContaminantPPM());
            }
        }

        double[] contaminantPPMByMonth = new double[reportsByMonth.length];
        for (int i = 0; i < reportsByMonth.length; i++) {
            double sum = 0;
            if (reportsByMonth[i] == null) {
                contaminantPPMByMonth[i] = sum;
            } else {
                for (int j = 0; j < reportsByMonth[i].size(); j++) {
                    sum += reportsByMonth[i].get(j);
                }
                contaminantPPMByMonth[i] = sum / reportsByMonth[i].size();
            }
        }
        return contaminantPPMByMonth;
    }
}
