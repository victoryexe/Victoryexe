public class Admin implements User{
    private String name;
    private String userName;
    private String userID;
    private String pw;
    private static adminCount; 
    //Will Probably Create A Password Class Solely to add another layer of security
    //Plus it will allow me to do some simple encryption for the Lol's

    public Admin(String name, String userName, String pw) {
        this.name = name;
        this.userName = userName;
        this.pw = pw;
        this.userID = "A" + adminCount;

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
        return pw == pass; //Strings Allow this stuff, it will be more complex later.
    } //Using getPassword would be a security problem.
    public boolean isAdmin() {
        return true;
    }
}