package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public class User implements Account {
    private String name;
    private String email;
    private int userID;
    private String homeAddress;
    private String title;
    private static int userCount; 
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.userCount++;
        this.userID = userCount;

    }
    public User() {
        name = null;
        email = null;
        userID = null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHomeAddress(String address) {
        this.homeAddress = address;
    }
    public String getHomeAddress() {
        return this.getHomeAddress;
    }
    public String setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
    public String getUserID() {
        return this.userID;
    }
    public AuthLevel getAuthLevel() {
        return AuthLevel.USER;
    }
    public boolean reportWaterAvailibility() {
        return true;
    }
    public void viewWaterSources() {

    }
}