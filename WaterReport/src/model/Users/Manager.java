package model.Users;
/**
* Author: Osvaldo Armas
* Version: 1.0
* Date:  9/30/2016
*/
public class Manager extends Worker {

    /**
     * Constructor used to load data from the DB
     * @param name the Manager's name
     * @param email the Manager's email
     * @param uID the Manager's userID
     */
    public Manager(String name, String email, int uID) {
        super(name, email, uID);
    }

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