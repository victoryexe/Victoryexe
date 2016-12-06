package db;
import java.sql.*;
import javax.sql.*;
import model.Users.*;
import model.log.*;
import model.registration.Authentication;
import model.registration.UserList;
import model.report.*;
import java.util.*;


/*STRUCTURE  OF DATABASE
DB Info: 
mysql server @ ozzyarmas.net
port 3306
database name: water_report

Actual Tables:
+-------------------------+
| Tables_in_water_report  |
+-------------------------+
| account                 |
| maps                    |
| bannedAccountLog        |
| deletedAccountLog       |
| deletedReportAccountLog |
| loginAttmptLog          |
| qualityReports          |
| unblockAccountLog       |
| waterReports            |
+-------------------------+

*/

/**
 * @author Osvaldo Armas
 * @version 1.0
 * Finalized Date 11/20/2016
 */
public class DB {
    private static boolean connected;
    private static Connection conn = null;
    /**
     * Connect to DB
     *@return if the connection was succesful
     */
    public static boolean connect() {
        try {
            if(!connected) {
                String url = "jdbc:mysql://104.131.110.247:3306/water_report";
                conn = DriverManager.getConnection(url, "Victoryexe", "PwD");
                connected = true;
            }
            return connected;
        } catch(Exception e) {
            e.printStackTrace();
            return connected;
        }
    }
    /**
     * Creates Accounts
     * @param acc Account to create
     * @return if adding Account was successful
     */
    public static boolean addAccount(Account acc) {
        String userDef;
        if(acc.getHomeAddress()!= null) {
            userDef = "('" + acc.getName() + "'," + acc.getUserID() + ",'"
                    + acc.getHomeAddress()
                    + "','" + acc.getTitle() + "','" + acc.getAuthLevel() + "'," + acc.getIsBlocked()
                    + "," + acc.getIsBanned() + ",'" + acc.getEmail() + "')";
        } else {
            userDef = "('" + acc.getName() + "'," + acc.getUserID() + ",'"
                    + "','" + "','" + acc.getAuthLevel() + "'," + acc.getIsBlocked()
                    + "," + acc.getIsBanned() + ",'" + acc.getEmail() + "')";
        }
        if(connected) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO account VALUES " + userDef);
                stmt.executeUpdate("INSERT INTO maps VALUES " + "('" + acc.getEmail() +
                        "','')");
                changePassword(Authentication.getHash(acc.getEmail()), acc.getEmail());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            connect();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO maps VALUES " + "('" + acc.getEmail() +
                        "','')");
                stmt.executeUpdate("INSERT INTO account VALUES " + userDef);
                changePassword(Authentication.getHash(acc.getEmail()), acc.getEmail());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    /*
            Account Description:
            +-----------+-------------+------+-----+---------+-------+
            | Field     | Type        | Null | Key | Default | Extra |
            +-----------+-------------+------+-----+---------+-------+
            | name      | varchar(20) | NO   |     | NULL    |       |
            | id        | int(11)     | NO   |     | NULL    |       |
            | address   | varchar(50) | NO   |     | NULL    |       |
            | title     | varchar(10) | NO   |     | NULL    |       |
            | authLevel | varchar(10) | NO   |     | NULL    |       |
            | isBlocked | tinyint(1)  | NO   |     | NULL    |       |
            | isBanned  | tinyint(1)  | NO   |     | NULL    |       |
            | email     | varchar(50) | NO   |     | NULL    |       |
            +-----------+-------------+------+-----+---------+-------+

    */

    /**
     * Loads all accounts from DB
     * @return ArrayList containing all accounts
     */
    public static ArrayList<Account> loadAllAccounts() {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM account");
                while(rs.next() && !rs.isAfterLast()) {
                    String name = rs.getString(1);
                    //System.out.println(name);
                    int id = rs.getInt(2);
                    String fullAddress = rs.getString(3);
                    String title = rs.getString(4);
                    String authLevel = rs.getString(5);
                    //System.out.println(authLevel);
                    boolean isBlocked = rs.getBoolean(6);
                    boolean isBanned = rs.getBoolean(7);
                    String email = rs.getString(8);
                    fullAddress = fullAddress.trim();
                    String[] address = fullAddress.split("/");
                    Address add;
                    if(address.length == 6) {
                        add = new Address(address[0],
                                Integer.parseInt(address[1]),
                                address[2],
                                address[3],
                                Integer.parseInt(address[4]),
                                address[5]);
                    } else {
                        add = null;
                    }
                    Account acc;
                    if (authLevel.equals("USER")) {
                        //System.out.println(name + email);
                        acc = new User(name, email, id);
                    } else if (authLevel.equals("ADMIN")) {
                        //System.out.println(name + email);
                        acc = new Admin(name, email, id);
                    } else if (authLevel.equals("WORKER")) {
                        //System.out.println(name + email);
                        acc = new Worker(name, email, id);
                    } else if (authLevel.equals("MANAGER")) {
                        //System.out.println(name + email);
                        acc = new Manager(name, email, id);
                    } else {
                        //System.out.println(name + email);
                        acc = null;
                    }
                    if (acc != null) {
                        acc.setHomeAddress(add);
                        acc.setTitle(title);
                        if (isBlocked) {
                            acc.setBlocked(true);
                        }
                        if (isBanned) {
                            acc.setIsBanned();
                        }
                        accountList.add(acc);
                        //System.out.println(acc.getEmail());
                        //System.out.println(acc.getHomeAddress());

                    }
                }
                return accountList;
            } catch (Exception e) {
                e.printStackTrace();
                return accountList;
            }
        } else {
            return null;
        }
    }
    /*
            Maps Description:
            +-------+-------------+------+-----+---------+-------+
            | Field | Type        | Null | Key | Default | Extra |
            +-------+-------------+------+-----+---------+-------+
            | email | varchar(30) | NO   | PRI | NULL    |       |
            | pass  | varchar(50) | NO   |     | NULL    |       |
            +-------+-------------+------+-----+---------+-------+
    */

    /**
     * Changes Emails in DB for account and maps tables
     * @param newEmail new email
     * @param oldEmail old email
     */
    public static void changeEmail(String newEmail, String oldEmail) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE maps SET email='" + newEmail
                        + "' WHERE email='" + oldEmail + "'");
                stmt.executeUpdate("UPDATE account SET email='" + newEmail
                        + "' WHERE email='" + oldEmail + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Changes Emails in DB for account and maps tables
     * @param newAddress new email
     * @param email old email
     */
    public static void changeAddress(String newAddress, String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE account SET address='" + newAddress
                        + "' WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * changes password in maps
     * @param newPass new password
     * @param email email the password is linked to
     */
    public static void changePassword(String newPass, String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE maps SET pass='" + newPass
                        + "' WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static HashMap<String, CharSequence> loadMap() {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            HashMap<String, CharSequence> map = new HashMap<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM maps");
                while(rs.next() && !rs.isAfterLast()) {
                    String email = rs.getString(1);
                    CharSequence pw = (CharSequence) rs.getString(2);
                    //System.out.println(email + " --- " + pw);
                    map.put(email, pw);
                    //rs.next();
                }
                return map;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Bans account based on email information
     * @param email of account to ban
     */
    public static void ban(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE account SET isBanned=" + 1
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * blocks account based on email information
     * @param email of account to ban
     */
    public static void block(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE account SET isBlocked=" + 1
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Unbans account based on email
     * @param email of account to ublock
     */
    public static void unban(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE account SET isBanned=" + 0
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * unbloacks account based on email
     * @param email of account to unblock
     */
    public static void unblock(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE account SET isBlocked=" + 0
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds Log to DB
     * @param log to add
     */
    public static void addLog(Log log) {
        if (log instanceof DeletedReportLog) {
            if(connect()) {
                Statement stmt = null;
                ResultSet rs = null;
                ArrayList<Account> accountList = new ArrayList<>();
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO deletedReportLog VALUES ("
                            + log.toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (log instanceof BannedAccountLog) {
            if(connect()) {
                Statement stmt = null;
                ResultSet rs = null;
                ArrayList<Account> accountList = new ArrayList<>();
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO bannedAccountLog VALUES ("
                            + log.toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (log instanceof LoginAttemptLog) {
            if(connect()) {
                Statement stmt = null;
                ResultSet rs = null;
                ArrayList<Account> accountList = new ArrayList<>();
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO loginAttemptLog VALUES ("
                            + log.toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (log instanceof UnblockAccountLog) {
            if(connect()) {
                Statement stmt = null;
                ResultSet rs = null;
                ArrayList<Account> accountList = new ArrayList<>();
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO unblockAccountLog VALUES ("
                            + log.toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (log instanceof DeletedAccountLog) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO deletedAccountLog VALUES ("
                        + log.toString() + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*
            Banned Account Log Description:
            +--------------------+-------------+------+-----+---------+-------+
            | Field              | Type        | Null | Key | Default | Extra |
            +--------------------+-------------+------+-----+---------+-------+
            | resposinbleAccount | varchar(50) | NO   |     | NULL    |       |
            | bannedAccountID    | varchar(50) | NO   |     | NULL    |       |
            | timeStamp          | varchar(30) | NO   |     | NULL    |       |
            +--------------------+-------------+------+-----+---------+-------+
            Deleted Account Log Description:
            +--------------------+-------------+------+-----+---------+-------+
            | Field              | Type        | Null | Key | Default | Extra |
            +--------------------+-------------+------+-----+---------+-------+
            | resposinbleAccount | varchar(50) | NO   |     | NULL    |       |
            | deletedAccountID   | varchar(50) | NO   |     | NULL    |       |
            | timeStamp          | varchar(30) | NO   |     | NULL    |       |
            +--------------------+-------------+------+-----+---------+-------+
            Deleted Report Log Description:
            +--------------------+-------------+------+-----+---------+-------+
            | Field              | Type        | Null | Key | Default | Extra |
            +--------------------+-------------+------+-----+---------+-------+
            | resposinbleAccount | varchar(50) | NO   |     | NULL    |       |
            | reportID           | varchar(50) | NO   |     | NULL    |       |
            | timeStamp          | varchar(30) | NO   |     | NULL    |       |
            +--------------------+-------------+------+-----+---------+-------+
            Login Attempt Log Description:
            +--------------------+-------------+------+-----+---------+-------+
            | Field              | Type        | Null | Key | Default | Extra |
            +--------------------+-------------+------+-----+---------+-------+
            | resposinbleAccount | varchar(50) | NO   |     | NULL    |       |
            | sucessStatus       | tinyint(1)  | NO   |     | NULL    |       |
            | timeStamp          | varchar(30) | NO   |     | NULL    |       |
            +--------------------+-------------+------+-----+---------+-------+
            Unblock Account Log Description:
            +--------------------+-------------+------+-----+---------+-------+
            | Field              | Type        | Null | Key | Default | Extra |
            +--------------------+-------------+------+-----+---------+-------+
            | resposinbleAccount | varchar(50) | NO   |     | NULL    |       |
            | unblockAccountID   | varchar(50) | NO   |     | NULL    |       |
            | timeStamp          | varchar(30) | NO   |     | NULL    |       |
            +--------------------+-------------+------+-----+---------+-------+


    */

    /**
     * Loads all log data in an Array of ArrayLists of Length 5
     * Index-0 represents BannedAccountLog
     * Index-1 represents DeletedAccountLog
     * Index-2 represents DeletedReportLog
     * Index-3 represents LoginAttemptLog
     * Index-4 represents unblockAccountLog
     * @return the ArrayList that fits the description above
     */
    public static ArrayList<Log>[] loadLogData() {
        ArrayList<Log>[] logData = new ArrayList[5];
        logData[0] = new ArrayList<>();
        logData[1] = new ArrayList<>();
        logData[2] = new ArrayList<>();
        logData[3] = new ArrayList<>();
        logData[4] = new ArrayList<>();
        if (connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM bannedAccountLog");
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String bannedAccountID;
                Log log;
                while (rs.next() && !rs.isAfterLast()) {
                    resAccount = UserList.getUserAccount(rs.getString(1));
                    bannedAccountID = rs.getString(2);
                    tStamp = rs.getString(3);
                    log = new BannedAccountLog((Admin) resAccount, bannedAccountID, tStamp);
                    logData[0].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM deletedAccountLog");
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String deletedAccountID;
                Log log;
                while (rs.next() && !rs.isAfterLast()) {
                    resAccount = UserList.getUserAccount(rs.getString(1));
                    deletedAccountID = rs.getString(2);
                    tStamp = rs.getString(3);
                    log = new DeletedAccountLog((Admin) resAccount, deletedAccountID, tStamp);
                    logData[1].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM deletedReportLog");
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String deletedReportID;
                Log log;
                while (rs.next() && !rs.isAfterLast()) {
                    resAccount = UserList.getUserAccount(rs.getString(1));
                    deletedReportID = rs.getString(2);
                    tStamp = rs.getString(3);
                    log = new DeletedReportLog((Manager) resAccount, deletedReportID, tStamp);
                    logData[2].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM loginAttemptLog");
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                boolean successStatus;
                Log log;
                while (rs.next() && !rs.isAfterLast()) {
                    resAccount = UserList.getUserAccount(rs.getString(1));
                    successStatus = rs.getBoolean(2);
                    tStamp = rs.getString(3);
                    log = new LoginAttemptLog(resAccount, successStatus, tStamp);
                    logData[3].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM unblockAccountLog");
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String bannedAccountID;
                Log log;
                while (rs.next() && !rs.isAfterLast()) {
                    resAccount = UserList.getUserAccount(rs.getString(1));
                    bannedAccountID = rs.getString(2);
                    tStamp = rs.getString(3);
                    log = new UnblockAccountLog((Admin) resAccount, bannedAccountID, tStamp);
                    logData[4].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return logData;
        }
        return null;
    }

    /*
           Quality Report Description:
           +---------------------+--------------+------+-----+---------+-------+
           | Field               | Type         | Null | Key | Default | Extra |
           +---------------------+--------------+------+-----+---------+-------+
           | submitterID         | varchar(30)  | YES  |     | NULL    |       |
           | other               | varchar(100) | YES  |     | NULL    |       |
           | location            | varchar(50)  | NO   |     | NULL    |       |
           | reportID            | int(11)      | NO   |     | NULL    |       |
           | virusPPM            | double       | NO   |     | NULL    |       |
           | waterCondition      | varchar(50)  | YES  |     | NULL    |       |
           | waterContaminantPPM | double       | NO   |     | NULL    |       |
           | timeStamp           | varchar(30)  | NO   |     | NULL    |       |
           +---------------------+--------------+------+-----+---------+-------+
           Water Report Description
           +----------------+--------------+------+-----+---------+-------+
           | Field          | Type         | Null | Key | Default | Extra |
           +----------------+--------------+------+-----+---------+-------+
           | submitterID    | varchar(30)  | YES  |     | NULL    |       |
           | other          | varchar(100) | YES  |     | NULL    |       |
           | location       | varchar(50)  | NO   |     | NULL    |       |
           | reportID       | int(11)      | NO   |     | NULL    |       |
           | waterType      | varchar(30)  | NO   |     | NULL    |       |
           | waterCondition | varchar(30)  | NO   |     | NULL    |       |
           | timeStamp      | varchar(30)  | NO   |     | NULL    |       |
           +----------------+--------------+------+-----+---------+-------+
   */

    /**
     * Adds Report to Database
     * @param report report to Add to Database
     */
    public static void addReport(Report report) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                if (report instanceof QualityReport) {
                    String reportDef = "('"
                            + report.getSubmitterID() + "','"
                            + report.getOther() + "','"
                            + report.getLocation() + "','"
                            + report.getReportID() + "',"
                            + ((QualityReport) report).getVirusPPM() + ",'"
                            + ((QualityReport) report).getWaterCondition() + "',"
                            + ((QualityReport) report).getContaminantPPM() + ",'"
                            + report.getTimestamp() + "'"
                            + ")";
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO qualityReports VALUES " + reportDef);
                } else if (report instanceof WaterReport) {
                    String reportDef = "('"
                            + report.getSubmitterID() + "','"
                            + report.getOther() + "','"
                            + report.getLocation() + "',"
                            + report.getReportID() + ",'"
                            + ((WaterReport) report).getWaterType() + "','"
                            + ((WaterReport) report).getWaterCondition() + "','"
                            + report.getTimestamp() + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO waterReports VALUES " + reportDef);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * I think I broke some rules on this one
     * Loads all reports and directly accesses ReportsList to the reports to it
     */
    public static void loadAllReports() {
        if (connect()) {

            //ReportsList.makeWaterReport(report);
            Statement stmt = null;
            ResultSet rs = null;
            /*
            Quality Report Description:
            +---------------------+--------------+------+-----+---------+-------+
            | Field               | Type         | Null | Key | Default | Extra |
            +---------------------+--------------+------+-----+---------+-------+
            | submitterID         | varchar(30)  | YES  |     | NULL    |       |
            | other               | varchar(100) | YES  |     | NULL    |       |
            | location            | varchar(50)  | NO   |     | NULL    |       |
            | reportID            | int(11)      | NO   |     | NULL    |       |
            | virusPPM            | double       | NO   |     | NULL    |       |
            | waterCondition      | varchar(50)  | YES  |     | NULL    |       |
            | waterContaminantPPM | double       | NO   |     | NULL    |       |
            | timeStamp           | varchar(30)  | NO   |     | NULL    |       |
            +---------------------+--------------+------+-----+---------+-------+
            */
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM qualityReports");
                while (rs.next() && !rs.isAfterLast()) {
                    String timeStamp = rs.getString(8);
                    Account acc = UserList.getUserAccount(rs.getString(1));
                    Location loc = new Location(rs.getString(3));
                    OverallCondition condition;
                    if (rs.getString(6).equals("SAFE")) {
                        condition = OverallCondition.SAFE;

                    } else if (rs.getString(6).equals("TREATABLE")) {
                        condition = OverallCondition.TREATABLE;

                    } else if (rs.getString(6).equals("UNSAFE")) {
                        condition = OverallCondition.UNSAFE;
                    } else { //Default to Unsafe if Unknown
                        condition = OverallCondition.UNSAFE;
                    }
                    Double virusPpm = rs.getDouble(5);
                    Double waterContaminantPpm = rs.getDouble(7);
                    QualityReport report = new QualityReport(
                            timeStamp,
                            (Worker) acc,
                            loc,
                            condition,
                            virusPpm,
                            waterContaminantPpm
                    );
                    ReportsList.makeQualityReport(report);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*      Water Report Description
            +----------------+--------------+------+-----+---------+-------+
            | Field          | Type         | Null | Key | Default | Extra |
            +----------------+--------------+------+-----+---------+-------+
            | submitterID    | varchar(30)  | YES  |     | NULL    |       |
            | other          | varchar(100) | YES  |     | NULL    |       |
            | location       | varchar(50)  | NO   |     | NULL    |       |
            | reportID       | int(11)      | NO   |     | NULL    |       |
            | waterType      | varchar(30)  | NO   |     | NULL    |       |
            | waterCondition | varchar(30)  | NO   |     | NULL    |       |
            | timeStamp      | varchar(30)  | NO   |     | NULL    |       |
            +----------------+--------------+------+-----+---------+-------+
            */
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM waterReports");
                while (rs.next() && !rs.isAfterLast()) {
                    String timeStamp = rs.getString(7);
                    Account acc = UserList.getUserAccount(rs.getString(1));
                    Location loc = new Location(rs.getString(3));
                    WaterCondition condition;
                    WaterType waterType;
                    if (rs.getString(5).equals("BOTTLED")) {
                        waterType = WaterType.BOTTLED;
                    } else if (rs.getString(5).equals("LAKE")) {
                        waterType = WaterType.LAKE;
                    } else if (rs.getString(5).equals("OTHER")) {
                        waterType = WaterType.OTHER;
                    } else if (rs.getString(5).equals("STREAM")) { //Default to Unsafe if Unknown
                        waterType = WaterType.STREAM;
                    } else if (rs.getString(5).equals("WELL")) {
                        waterType = WaterType.WELL;
                    } else { //Default Other if unknown
                        waterType = WaterType.OTHER;
                    }
                    if (rs.getString(6).equals("POTABLE")) {
                        condition = WaterCondition.POTABLE;

                    } else if (rs.getString(6).equals("TREATABLE_CLEAR")) {
                        condition = WaterCondition.TREATABLE_CLEAR;

                    } else if (rs.getString(6).equals("TREATABLE_MUDDY")) {
                        condition = WaterCondition.TREATABLE_MUDDY;
                    } else if (rs.getString(6).equals("WASTE")) {
                        condition = WaterCondition.WASTE;
                    } else { //Default to Waste if Unknown
                        condition = WaterCondition.WASTE;
                    }
                    WaterReport report = new WaterReport(
                            timeStamp,
                            (User) acc,
                            loc,
                            waterType,
                            condition
                    );
                    ReportsList.makeWaterReport(report);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Deletes Reports from DB
     * @param report to delete from DB
     * @return success status of report deletion
     */
    public static boolean deleteReport(Report report) {
        if (connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                if (report instanceof QualityReport) {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM qualityReports WHERE reportID='" +
                            report.getReportID() + "'");
                    return true;
                } else if (report instanceof WaterReport) {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM waterReports WHERE reportID='" +
                            report.getReportID() + "'");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Deletes Account from DB
     * @param account
     * @return success status of account deletion
     */
    public static boolean deleteAccount(Account account) {
        if (connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM account WHERE email='" +
                        account.getEmail() + "'");
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM maps WHERE email='" +
                        account.getEmail() + "'");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}