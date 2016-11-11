package model.Users;
/**
* Author: Osvaldo Armas
* Version: 1.0
* Date:  9/30/2016
*/
public class Manager extends Worker {

    /**
     * Creates a Manager with the given name and email
     * @param name the Manager's name
     * @param email the Manager's email
     */
    public Manager(String name, String email) {
        super(name, email);
    }
    @Override
    public AuthLevel getAuthLevel() {
        return AuthLevel.MANAGER;
    }
}