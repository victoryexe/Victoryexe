package model.registration;

import model.Users.*;

/**
 * Created by Alexandra on 10/3/2016.
 */
public class UserFactory {
    /**
     * Creates an account of the appropriate auth type
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userid the user's email
     * @param auth the designated authentication level
     * @return the created Account
     */
    public static Account makeAccount(String firstName, String lastName, String userid, AuthLevel auth) {
        Account account = null;
        if (auth.equals(AuthLevel.USER)) {
            account = new User(firstName + " " + lastName, userid);
        } else if (auth.equals(AuthLevel.WORKER)) {
            account = new Worker(firstName + " " + lastName, userid);
        } else if (auth.equals(AuthLevel.MANAGER)) {
            account = new Manager(firstName + " " + lastName, userid);
        } else if (auth.equals(AuthLevel.ADMIN)) {
            account = new Admin(firstName + " " + lastName, userid);
        }
        return account;
    }
}
