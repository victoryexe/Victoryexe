package db.DB;
import model.User.*;
/*STRUCTURE  OF DATABASE
DB Info: 
mysql server @ ozzyarmas.net
port 3306
database name: water_report

CLASSES THAT USE THIS DATABASE
Authentication
UserList
LogList
UserFactory
ReportsList

TABLES
Accounts
Logs
Reports
Maps (Auth and UseList)

TABLE STRUCTURES
ACCOUNT:
Name varchar
Id int
Email varchar
Address varchar
Title varchar
AuthLevel varchar
IsBanned boolean
IsBlocked boolean

MAPS (Select Tables whose email address Match and their resulting Password and Users)
Email Address
Password
User Associated

LOGS

REPORTS

----------------------------------------
Actual Tables:
+-------------------------+
| Tables_in_water_report  |
+-------------------------+
| account                 |
| bannedAccountLog        |
| deletedAccountLog       |
| deletedReportAccountLog |
| loginAttmptLog          |
| maps                    |
| qualityReports          |
| unblockAccountLog       |
| waterReports            |
+-------------------------+

ACCOUNT TABLE



    
*/
public class DB {
    private static boolean connected;
    public static boolean connect() {
        Connection conn = null;
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
    public static boolean addUser(String name, String email,
        int id, String address, String title, String authLevel,
        boolean isBlocked, boolean isBanned, String domain) {
        String userDef = "(" + name + "," + id  + "," + address  + ","
             + title  + "," +  authLevel  + "," + isBlocked  + "," + isBanned + ","+ email + ","
             + domain + ")"; 
        if(connect()) {
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
    public static ArrayList<Account> loadAllUsers() {
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
                    String address = rs.getString(3);
                    String title = rs.getString(5);
                    String authLevel = rs.getString(6);
                    boolean isBlocked = rs.getBoolean(7);
                    boolean isBanned = rs.getBoolean(8);
                    String email = rs.getEmail(9);
                    String domain = rs.getDomain(10);
                    if (authLevel == "USER") {
                        Account acc = new User(name, email + "@" + domain, id);
                        acc.setHomeAddress(address);
                        acc.setTitle(title);
                        if (isBlocked) {
                            acc.setIsBlocked();
                        }
                        if (isBanned) {
                            acc.setIsBanned();
                        }

                    } else if (authLevel == "ADMIN") {
                        Account acc = new Admin(name, email + "@" + domain, id);
                        acc.setHomeAddress(address);
                        acc.setTitle(title);
                        if (isBlocked) {
                            acc.setIsBlocked();
                        }
                        if (isBanned) {
                            acc.setIsBanned();
                        }
                    } else if (authLevel == "WORKER") {
                        Account acc = new Worker(name, email + "@" + domain, id);
                        acc.setHomeAddress(address);
                        acc.setTitle(title);
                        if (isBlocked) {
                            acc.setIsBlocked();
                        }
                        if (isBanned) {
                            acc.setIsBanned();
                        }
                    } else if (authLevel == "MANAGER") {
                        Account acc = new Manager(name, email + "@" + domain, id);
                        acc.setHomeAddress(address);
                        acc.setTitle(title);
                        if (isBlocked) {
                            acc.setIsBlocked();
                        }
                        if (isBanned) {
                            acc.setIsBanned();
                        }
                    }
                    accountList.add(acc);
                    rs.next();
                }
                return accountList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void changeEmail() {

    }

}