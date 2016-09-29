package model.Users;

public class ClientUser implements User {
    private String name;
    private String userName;
    private String userID;
    private String pw;
    private static String cUserCount; // TODO added String, not sure

    public ClientUser(String name, String userName, String pw) {
        this.name = name;
        this.userName = userName;
        this.pw = pw;
        this.userID = "C" + cUserCount; // TODO changed adminCount to cUserCount

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUserName(String uName) {
        this.userName = uName;
    }
    public void setUserID(String userid) {
        this.userID = userid;
    }
    public void setPassword(String pw) {
        this.pw = pw;
    }
    public boolean verifyPassword(String pass) {
        return pw == pass; 
    }
    public boolean isAdmin() {
        return false;
    }
}