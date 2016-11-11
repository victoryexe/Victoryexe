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
    private Address homeAddress;
    private String title;
    static int userCount;
    private boolean isBanned;
    private boolean isBlocked;

    public User(String name, String email, int id) {
        this(name, email);
        this.userID = id;
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.userCount++;
        this.userID = userCount;

    }
    public User() {

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
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
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
    @Override
    public boolean getIsBanned() {
        return isBanned;
    }
    @Override
    public void setIsBanned() {
        isBanned = !isBanned;
    }
    @Override
    public boolean getIsBlocked() {
        return isBlocked;
    }
    @Override
    public void setIsBlocked() {
        isBlocked = !isBlocked;
    }


    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        return ((User) obj).getUserID() == this.getUserID();
    }
    @Override
    public int hashCode() {
        return getUserID();
    }
}