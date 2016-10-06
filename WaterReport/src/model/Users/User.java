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
    protected Address homeAddress;
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
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Address getHomeAddress() {
        return this.homeAddress;
    }
    @Override
    public void setHomeAddress(Address homeAddress) { this.homeAddress = homeAddress; }
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String getTitle() {
        return this.title;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String getEmail() {
        return this.email;
    }
    @Override
    public int getUserID() {
        return this.userID;
    }
    @Override
    public AuthLevel getAuthLevel() {
        return AuthLevel.USER;
    }
    /**
    *@return returns if reporting was succesful;
    */
    public boolean reportWaterAvailibility() {
        return true;
    }
    public void viewWaterSources() {

    }
}