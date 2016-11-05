package model.log;

import model.Users.Manager;
import model.report.Report;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds information specific to Report deletion
 */
public class DeletedReportLog extends Log {
    private final String reportID;

    /**
     * Makes a DeletedReport log with a timestamp and reportID
     * @param responsibleAccount the Manager that deleted the report
     * @param report the report that is being deleted
     */
    public DeletedReportLog(Manager responsibleAccount, Report report) {
        super(responsibleAccount);
        reportID = report.toString();
    }

    /**
     * Gets the deleted report, type and id
     * @return String containing this log's report type and ID
     */
    public String getReportID() {
        return reportID;
    }
}
