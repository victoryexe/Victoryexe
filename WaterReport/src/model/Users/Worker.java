package model.Users;
/**
* Author Osvaldo Armas
* Version 1.0
* Date:  9/30/2016
*/
public class Worker extends User implements Account {

    public Worker(String name, String userName) {
        this.name = name;
        this.userName = userName;
        super.userCount++;
        this.userID = userCount;
    }
    public AuthLevel getAuthLevel() {
        return AuthLevel.WORKER;
    }
    public boolean reportWaterPurity() {
        
    }
}