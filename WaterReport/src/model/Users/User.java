package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public class User implements Account {
    private String name;
    private String email;
    private final int userID;
    private Address homeAddress;
    private String title;
    private static int userCount;
    private boolean isBanned;
    private boolean isBlocked;

    /**
     * Constructor used to load data from the DB
     * @param name the User's name
     * @param email the User's email
     * @param userID the User's userID
     */
    public User(String name, String email, int userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
    }

    /**
     * Creates a new User with the given name and email
     * @param name the User's name
     * @param email the User's email
     */
    public User(String name, String email) {
        this(name, email, ++userCount);
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
    public void setBanned(boolean ban) {isBanned = ban;}
    @Override
    public boolean getIsBlocked() {
        return isBlocked;
    }
    @Override
    public void setBlocked(boolean block) {isBlocked = block;}


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
        return ((User) obj).hashCode() == this.hashCode();
    }
    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
    @Override
    public String toString() {
        String msg = name + ", " + email + ", " + getAuthLevel();
        if (isBanned) {
            msg += ", BANNED";
        }
        if (isBlocked) {
            msg += ", BLOCKED";
        }
        return msg;
    }
}