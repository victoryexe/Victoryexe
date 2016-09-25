package model.login;

import lib.PasswordStorage;

/**
 * Created by grizz on 9/19/2016.
 */
public class Login {
    /**
     * Verifies credentials
     * @return true if login is successful, else false
     */
    public static boolean login(String subject, String password)
            throws PasswordStorage.CannotPerformOperationException,
                   PasswordStorage.InvalidHashException{
        return (Authentication.verifySubject(subject)
                && Authentication.verifyPassword(subject, password));
    }
}
