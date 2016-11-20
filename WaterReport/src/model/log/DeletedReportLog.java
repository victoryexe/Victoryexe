package model.log;

import model.Users.Manager;
import model.report.Report;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds information specific to Report deletion
 */
public class DeletedReportLog extends Log {
    private final String reportID;

    // For all logs, we kept the responsibleAccount specific as possible
    // because not all user types can initiate actions. For example, a
    // regular User should not be able to ban an account; only an admin can.

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
     * @param responsibleAccount The Adming that banned the account
     * @param reportID the userID of the banned account
     * @param timeStamp the String format of the timestamp
     */
    public DeletedReportLog(Manager responsibleAccount, String reportID,
                             String timeStamp) {
        super(timeStamp, responsibleAccount);
        this.reportID = reportID;
    }
    @Override
    public String toString() {
        if (reportID != null) {
            return "'" + getTimestamp().toString() + "','"
                    + getResponsibleAccount().getEmail() + "','"
                    + reportID + "'";
        } else {
            return "EMPTY LOG";
        }
    }
}
