package model.Users;
/**
* Author: Osvaldo Armas
* Version: 1.0
* Date:  9/30/2016
*/
public class Manager extends Worker {

    public Manager(String name, String email) {
        super(name, email);
    }
    @Override
    public AuthLevel getAuthLevel() {
        return AuthLevel.MANAGER;
    }
}