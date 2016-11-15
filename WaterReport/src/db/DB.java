package db;
import java.sql.*;
import javax.sql.*;
import model.Users.*;
import model.log.*;
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
                System.out.println("Database connection established");
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
    public static boolean addAccount(String name, String email,
        int id, String address, String title, String authLevel,
        boolean isBlocked, boolean isBanned) {
        String userDef = "('" + name + "'," + id  + ",'" + address  + "','"
             + title  + "','" +  authLevel  + "'," + isBlocked  + ",'" + isBanned + "','"+ email + "')";
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
                rs = stmt.executeQuery("INSERT INTO account " + userDef);
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
    // Get Log data from DB:
    // Email, Time Stamp, Respective ID
    // Use Email and ID data Log List to create adequate logs.
    public static ArrayList<String>[] loadLogData() {
        ArrayList<String>[] logData = new ArrayList[5];
        if (connect()) {
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Account> accountList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM bannedAccountLog");
                rs.first();
                while (!rs.isAfterLast()) {
                    String log = "";
                    log = rs.getString(1);
                    log = log + " " + rs.getString(2);
                    log = log + " " + rs.getString(3);
                    rs.next();
                    logData[0].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM deletedAccountLog");
                rs.first();
                while (!rs.isAfterLast()) {
                    String log = "";
                    log = rs.getString(1);
                    log = log + " " + rs.getString(2);
                    log = log + " " + rs.getString(3);
                    rs.next();
                    logData[1].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM deletedReportLog");
                rs.first();
                while (!rs.isAfterLast()) {
                    String log = "";
                    log = rs.getString(1);
                    log = log + " " + rs.getString(2);
                    log = log + " " + rs.getString(3);
                    rs.next();
                    logData[2].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM loginAttemptLog");
                rs.first();
                while (!rs.isAfterLast()) {
                    String log = "";
                    log = rs.getString(1);
                    log = log + " " + rs.getString(2);
                    log = log + " " + rs.getString(3);
                    rs.next();
                    logData[3].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM unblockAccountLog");
                rs.first();
                while (!rs.isAfterLast()) {
                    String log = "";
                    log = rs.getString(1);
                    log = log + " " + rs.getString(2);
                    log = log + " " + rs.getString(3);
                    rs.next();
                    logData[4].add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return logData;
        }
        return null;
    }
    public static void addReport(Report report) {

    }
    public static void loadAllReports() {

    }

}