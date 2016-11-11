package model.Users;
/**
* Author Osvaldo Armas
* Version 1.0
* Date:  9/30/2016
*/
public class Worker extends User {

    public Worker(String name, String email) {
        super(name, email);
    }
    public Worker() {
    }

    @Override
    public AuthLevel getAuthLevel() {
        return AuthLevel.WORKER;
    }
}