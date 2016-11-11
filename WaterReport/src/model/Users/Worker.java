package model.Users;
/**
* Author Osvaldo Armas
* Version 1.0
* Date:  9/30/2016
*/
public class Worker extends User {

    public Worker(String name, String email, int uID) {
        super(name, email, uID);
    }

    /**
     * Creates a Worker with the given name and email
     * @param name the Worker's name
     * @param email the Worker's email
     */
    public Worker(String name, String email) {
        super(name, email);
    }

    @Override
    public AuthLevel getAuthLevel() {
        return AuthLevel.WORKER;
    }
}