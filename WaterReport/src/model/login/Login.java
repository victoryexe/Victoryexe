package model.login;

import model.login.*;

/**
 * Created by grizz on 9/19/2016.
 */
public class Login {
    /**
     * Verifies credentials
     * @return true if login is successful, else false
     */
    public static boolean login(String subject, String password) {
        return (Authentication.verifySubject(subject) && Authentication.verifyPassword(subject, password));
    }
}
