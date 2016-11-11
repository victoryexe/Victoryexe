package model.Users;
/**
 * Author Osvaldo Armas
 * Version 2.0
 * Date:  9/30/2016
 */
public class Admin implements Account {
    private String name;
    private String email;
    private Address homeAddress;
    private String title;
    private final int userID;
    private static int adminCount;
    private boolean isBanned;
    private boolean isBlocked;

    public Admin(String name, String email, int userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
    }

    /**
     * Creates an Admin with the given name and email
     * @param name the Admin's name
     * @param email the Admin's email
     */
    public Admin(String name, String email) {
        this(name, email, ++adminCount);
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
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
    @Override
    public Address getHomeAddress() {
        return this.homeAddress;
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
    public AuthLevel getAuthLevel() {
        return AuthLevel.ADMIN;
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
    public void setIsBlocked() {
        isBlocked = !isBlocked;
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
        if (!(obj instanceof Admin)) {
            return false;
        }
        return ((Admin) obj).getUserID() == this.getUserID();
    }
    @Override
    public int hashCode() {
        return getUserID();
    }
    @Override
    public String toString() {
        return name + ", " + email + ", " + getAuthLevel().name();
    }
}