package model.registration;

import model.login.UserList;
import model.Users.AuthLevel;

/**
 * Created by Alexandra on 9/29/2016.
 */
public class Registration {
    private Registration() {
    }

    /**
     * Creates a new account with the given data
     * @param first the user's first name
     * @param last the user's last name
     * @param userid the user's email
     * @param pass1 the user's password
     * @param pass2 confirm password
     * @param auth the AuthLevel of the account
     */
    public static void createAccount(String first, String last, String userid,
                                     String pass1, String pass2, AuthLevel auth) {
        UserList.makeNewUser(first, last, userid, pass1, pass2, auth);
    }
}
