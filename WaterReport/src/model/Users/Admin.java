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
    private int userID;
    private static int adminCount;
    private boolean isBanned;
    private boolean isBlocked;
    public Adming(String name, String email, int id) {
        this(name, email);
        this.userID = id;
    }
    public Admin(String name, String email) {
        this.name = name;
        this.email = email;
        adminCount++;
        this.userID = adminCount;

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
    public void setHomeAddress(Address homeAddress) { this.homeAddress = homeAddress; }
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
        if (!(obj instanceof Admin)) {
            return false;
        }
        return ((Admin) obj).getUserID() == this.getUserID();
    }
    @Override
    public int hashCode() {
        return getUserID();
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