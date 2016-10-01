package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public class Admin implements Account {
    private String name;
    private String email;
    private int userID;
    private static int adminCount;

    public Admin(String name, String email) {
        this.name = name;
        this.email = email;
        adminCount++;
        this.userID = adminCount;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public AuthLevel getAuthLevel() {
        return AuthLevel.ADMIN;
    }
    public String getEmail() {
        return this.email;
    }
    public int getUserID() {
        return this.userID;
    }
    public boolean deleteAccount() {
        return true;
    }
    public boolean banUserFromSubmittingReports() {
        return true;
    }
    public boolean unblockAccount() {
        return true;
    }
    public void viewSecurityLog() {
        
    }
}