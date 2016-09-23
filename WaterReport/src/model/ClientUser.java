public class ClientUser implements User {
    private String name;
    private String userName;
    private String userID;
    private String pw;
    private static cUserCount;

    public Admin(String name, String userName, String pw) {
        this.name = name;
        this.userName = userName;
        this.pw = pw;
        this.userID = "C" + adminCount;

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