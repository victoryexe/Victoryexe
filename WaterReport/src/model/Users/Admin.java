package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public class Admin implements Account {
    private String name;
    private String email;
    private String homeAddress;
    private String title;
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
    public String getEmail() {
        return this.email;
    }
    public int getUserID() {
        return this.userID;
    }
    public void setHomeAddress(String address) {
        this.homeAddress = address;
    }
    public String getHomeAddress() {
        return this.homeAddress;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }
    public AuthLevel getAuthLevel() {
        return AuthLevel.ADMIN;
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