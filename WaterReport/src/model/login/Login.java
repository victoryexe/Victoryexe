package model.login;

/**
 * Created by grizz on 9/19/2016.
 */
public class Login {
    /**
     * Verifies credentials
     * @param subject the userid of the account trying to login
     * @param password the password entered
     * @return true if login is successful, else false
     */
    public static boolean login(String subject, String password) {
        return Authentication.verifySubject(subject)
                && Authentication.verifyPassword(subject, password);
    }
}