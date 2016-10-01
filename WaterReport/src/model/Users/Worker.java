package model.Users;
/**
* Author Osvaldo Armas
* Version 1.0
* Date:  9/30/2016
*/
public class Worker extends User {

    public Worker(String name, String email) {
        this.name = name;
        this.email = email;
        super.userCount++;
        this.userID = userCount;
    }
    public Worker(){}
    public AuthLevel getAuthLevel() {
        return AuthLevel.WORKER;
    }
    public boolean reportWaterPurity() {
        return true;
    }
}