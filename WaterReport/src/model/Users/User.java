package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public class User implements Account {
    protected String name;
    protected String email;
    protected int userID;
    protected String homeAddress;
    protected String title;
    protected static int userCount; 
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.userCount++;
        this.userID = userCount;

    }
    public User() {
        name = null;
        email = null;
        userID = 0;
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
        return this.homeAddress;
    }
    public String setTitle(String title) {
        return this.title = title;
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
    public int getUserID() {
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