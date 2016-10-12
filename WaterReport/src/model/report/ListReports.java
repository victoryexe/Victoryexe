package model.report;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alexandra on 10/12/2016.
 */
public class ListReports {
    private static List<Report> reports = ReportsList.getReportsList();

    private static void updateReports() {
        reports = ReportsList.getReportsList();
    }

    public static List<Report> sortByReportID() {
        updateReports();
        List<Report> reportClone = new ArrayList<>(reports);
        Collections.sort(reportClone, (Report r1, Report r2) -> {
            // TODO
        });
        return reportClone;
    }
}
