package db;
import java.sql.*;
import javax.sql.*;
import model.Users.*;
import model.log.*;
import model.registration.UserList;
import model.report.*;
import java.util.*;
/*STRUCTURE  OF DATABASE
DB Info: 
mysql server @ ozzyarmas.net
port 3306
database name: water_report

CLASSES THAT USE THIS DATABASE
Authentication
UserList DONE
LogList
UserFactory
ReportsList

TABLES
Accounts
Logs
Reports
Maps (Auth and UseList)

TABLE STRUCTURES

----------------------------------------
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

ACCOUNT TABLE


Missing:
Report List Stuff
Fixing Some stuff with other class
    
*/
public class DB {
    private static boolean connected;
    private static Connection conn = null;
    /** Connect to DB
    *
    *
     */
    public static boolean connect() {
        try {
            if(!connected) {
                String url = "jdbc:mysql://104.131.110.247:3306/water_report";
                conn = DriverManager.getConnection (url, "Victoryexe", "PwD");
                connected = true;
            }
            return connected;
        } catch(Exception e) {
            connected = false;
            return connected;
        }
    }
    /** Creates Accounts
     *
     *
     */
    public static boolean addAccount(Account acc) {
        String userDef = "('" + acc.getName() + "'," + acc.getUserID()  + ",'"
                + acc.getHomeAddress()
                + "','"+ acc.getTitle() + "','" +  acc.getAuthLevel()  + "'," + acc.getIsBlocked()
                + ",'" + acc.getIsBanned() + "','"+ acc.getEmail() + "')";
        if(connected) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("INSERT INTO account VALUES " + userDef);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            connect();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("INSERT INTO account VALUES" + userDef);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
    /** loads all accounts
     *
     *
     */
    public static ArrayList<Account> loadAllAccounts() {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM account");
                rs.first();
                while(!rs.isAfterLast()) {
                    String name = rs.getString(1);
                    int id = rs.getInt(2);
                    String fullAddress = rs.getString(3);
                    fullAddress = fullAddress.trim();
                    String[] address = fullAddress.split(" ");
                    Address add;
                    if(address.length == 5) {
                        add = new Address(address[0],address[1], address[2],
                                Integer.parseInt(address[3]),
                                address[4]);
                    } else if (address.length == 6){
                        add = new Address(address[0] + address[1], address[2], address[3],
                                Integer.parseInt(address[3]) , address[5]);
                    } else {
                        add = null;
                    }
                    String title = rs.getString(5);
                    String authLevel = rs.getString(6);
                    boolean isBlocked = rs.getBoolean(7);
                    boolean isBanned = rs.getBoolean(8);
                    String email = rs.getString(9);
                    String domain = rs.getString(10);
                    Account acc;
                    if (authLevel == "USER") {
                        acc = new User(name, email + "@" + domain, id);
                    } else if (authLevel == "ADMIN") {
                        acc = new Admin(name, email + "@" + domain, id);
                    } else if (authLevel == "WORKER") {
                        acc = new Worker(name, email + "@" + domain, id);
                    } else if (authLevel == "MANAGER") {
                        acc = new Manager(name, email + "@" + domain, id);
                    } else {
                        acc = null;
                    }
                    if((add != null) && (acc !=null)) {
                        acc.setHomeAddress(add);
                        acc.setTitle(title);
                        if (isBlocked) {
                            acc.setIsBlocked();
                        }
                        if (isBanned) {
                            acc.setIsBanned();
                        }
                        accountList.add(acc);
                    }
                    rs.next();

                }
                return accountList;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
    /*
    * Finds user based on email then changes that users email
    * Commands to Execute
    * UPDATE maps SET email=new@email.com WHERE email=old@email.com
    * UPDATE accounts SET email=new@email.com WHERE email=old@email.com
    *
    */
    public static void changeEmail(String newEmail, String oldEmail) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("UPDATE maps SET email='" + newEmail
                        + "' WHERE email='" + oldEmail + "'");
                rs = stmt.executeQuery("UPDATE accounts SET email='" + newEmail
                        + "' WHERE email='" + oldEmail + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void changePassword(String newPass, String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("UPDATE maps SET pass='" + newPass
                        + "' WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void ban(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("UPDATE accounts SET isBanned=" + 1
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void block(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("UPDATE accounts SET isBlocked=" + 1
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void unban(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("UPDATE accounts SET isBanned=" + 0
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void unblock(String email) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("UPDATE accounts SET isBlocked=" + 0
                        + " WHERE email='" + email + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void addLog(Log log) {
        if (log instanceof DeletedReportLog) {
            if(connect()) {
                Statement stmt = null;
                ResultSet rs = null;
                ArrayList<Account> accountList = new ArrayList<>();
                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("INSERT INTO deletedReportLogs VALUES ("
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
                    rs = stmt.executeQuery("INSERT INTO bannedAccountLog VALUES ("
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
                    rs = stmt.executeQuery("INSERT INTO loginAttemptLog VALUES ("
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
                    rs = stmt.executeQuery("INSERT INTO unblockAccountLog VALUES ("
                            + log.toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    public static ArrayList<Log>[] loadLogData() {
        ArrayList<Log>[] logData = new ArrayList[5];
        if (connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM bannedAccountLog");
                rs.first();
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String bannedAccountID;
                Log log;
                while (!rs.isAfterLast()) {
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
                rs.first();
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String deletedAccountID;
                Log log;
                while (!rs.isAfterLast()) {
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
                rs.first();
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String deletedReportID;
                Log log;
                while (!rs.isAfterLast()) {
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
                rs.first();
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                boolean successStatus;
                Log log;
                while (!rs.isAfterLast()) {
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
                rs.first();
                String tStamp;
                Account resAccount;// = UserList.getUserAccount(rs.getString(1));
                String bannedAccountID;
                Log log;
                while (!rs.isAfterLast()) {
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
    */
    /*
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
    public static void addReport(Report report) {
        if(connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                if (report instanceof QualityReport) {
                    String reportDef = "("
                            + Timestamp.valueOf(report.getTimestamp()) + ",'"
                            + report.getSubmitterID() + "','"
                            + report.getOther() + "','"
                            + report.getLocation() + "','"
                            + report.getReportID() + "',"
                            + ((QualityReport) report).getVirusPPM() + "',"
                            + ((QualityReport) report).getWaterCondition() + "',"
                            + ((QualityReport) report).getContaminantPPM()
                            + ")";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("INSERT INTO qualityReports VALUES " + reportDef);
                } else if (report instanceof WaterReport) {
                    String reportDef = "("
                            + report.getTime() + ",'"
                            + report.getSubmitterID() + "','"
                            + report.getOther() + "','"
                            + report.getLocation() + "','"
                            + report.getReportID() + "','"
                            + ((WaterReport) report).getWaterType() + "','"
                            + ((WaterReport) report).getWaterCondition() + "'"
                            + ")";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("INSERT INTO waterReports VALUES " + reportDef);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
                rs = stmt.executeQuery("SELECT * FROM qualityReport");
                rs.first();
                while (!rs.isAfterLast()) {
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
                rs = stmt.executeQuery("SELECT * FROM waterReport");
                rs.first();
                while (!rs.isAfterLast()) {
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
}