package model.log;

import model.Users.Account;
import model.Users.Admin;
import model.Users.Manager;
import model.report.Report;
import db.DB;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 10/31/2016.
 * A class that holds all Logs generated by the system, permits access to these
 * logs, and creates and adds new Logs to these lists.
 */
public class LogList {
    private static final List<BannedAccountLog> bannedAccountLog =
            new LinkedList<>();
    private static final List<DeletedAccountLog> deletedAccountLog =
            new LinkedList<>();
    private static final List<DeletedReportLog> deletedReportLog =
            new LinkedList<>();
    private static final List<LoginAttemptLog> loginAttemptLog =
            new LinkedList<>();
    private static final List<UnblockAccountLog> unblockAccountLog =
            new LinkedList<>();

    // All unused methods in this class are only because those features
    // have not yet been implemented.

    /**
     * Gets the List of all BannedAccountLogs
     * @return a List of all BannedAccountLogs
     */
    public static List<BannedAccountLog> getBannedAccountLog() {
        return new LinkedList<>(bannedAccountLog);
    }

    /**
     * Gets the List of all DeletedAccountLogs
     * @return a List of all DeletedAccountLogs
     */
    public static List<DeletedAccountLog> getDeletedAccountLog() {
        return new LinkedList<>(deletedAccountLog);
    }

    /**
     * Gets the List of all DeletedReportLogs
     * @return a List of all DeletedReportLogs
     */
    public static List<DeletedReportLog> getDeletedReportLog() {
        return new LinkedList<>(deletedReportLog);
    }

    /**
     * Gets the List of all LoginAttemptLogs
     * @return a List of all LoginAttemptLogs
     */
    public static List<LoginAttemptLog> getLoginAttemptLog() {
        return new LinkedList<>(loginAttemptLog);
    }

    /**
     * Gets the List of all UnblockAccountLogs
     * @return a List of all UnlockAccountLogs
     */
    public static List<UnblockAccountLog> getUnblockAccountLog() {
        return new LinkedList<>(unblockAccountLog);
    }

    /**
     * Makes a new BannedAccountLog with the given params and adds it to the
     * appropriate list
     * @param responsibleAccount the Admin banning an account
     * @param bannedAccountID the userid of the banned account
     */
    public static void makeBannedAccountEntry(Admin responsibleAccount,
                                              String bannedAccountID) {
        BannedAccountLog log = new BannedAccountLog(responsibleAccount,
                bannedAccountID);
        bannedAccountLog.add(log);
        DB.addLog(log);
    }

    /**
     * Makes a new DeletedAccountLog with the given params and adds it to the
     * appropriate list
     * @param responsibleAccount the Admin deleting an account
     * @param deletedAccountID the userid of the deleted account
     */
    public static void makeDeletedAccountEntry(Admin responsibleAccount,
                                               String deletedAccountID) {
        DeletedAccountLog log = new DeletedAccountLog(responsibleAccount,
                deletedAccountID);
        deletedAccountLog.add(log);
        DB.addLog(log);
        //System.out.println("deletedAccount");

    }

    /**
     * Logs to be added to all Logs
     * @param logs
     */
    public static void addNewLogs(List<Log>[] logs) {
        if (logs[0] != null) {
            for (Log log : logs[0]) {
                bannedAccountLog.add((BannedAccountLog) log);
            }
            for (Log log : logs[1]) {
                deletedAccountLog.add((DeletedAccountLog) log);
            }
            for (Log log : logs[2]) {
                deletedReportLog.add((DeletedReportLog) log);
            }
            for (Log log : logs[3]) {
                loginAttemptLog.add((LoginAttemptLog) log);
            }
            for (Log log : logs[4]) {
                unblockAccountLog.add((UnblockAccountLog) log);
            }
        }
    }
    /**
     * Makes a new DeletedReportLog with the given params and adds it to the
     * appropriate list
     * @param responsibleAccount the Manager deleting an account
     * @param report the deleted Report
     */
    public static void makeDeletedReportEntry(Manager responsibleAccount,
                                              Report report) {
        DeletedReportLog log = new DeletedReportLog(responsibleAccount, report);
        deletedReportLog.add(log);
        DB.addLog(log);

    }

    /**
     * Makes a new LoginAttemptLog with the given params and adds it to the
     * appropriate list
     * @param responsibleAccount the Account logging in
     * @param successStatus boolean representing the Account's login success
     */
    public static void makeLoginAttemptEntry(Account responsibleAccount,
                                             boolean successStatus) {
        LoginAttemptLog log = new LoginAttemptLog(responsibleAccount,
                successStatus);
        loginAttemptLog.add(log);
        DB.addLog(log);

    }

    /**
     * Makes a new UnblockAccountLog with the given params and adds it to
     * the appropriate list
     * @param responsibleAccount the Admin unblocking an Account
     * @param unblockedAccountID the unblockedAccount's userid
     */
    public static void makeUnblockAccountEntry(Admin responsibleAccount,
                                               String unblockedAccountID) {
        UnblockAccountLog log = new UnblockAccountLog(responsibleAccount,
                unblockedAccountID);
        unblockAccountLog.add(log);
        DB.addLog(log);

    }
}
