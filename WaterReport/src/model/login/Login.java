package model.login;

import model.Users.Account;
import model.Users.User;
import model.log.LogList;

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
        boolean success = Authentication.verifySubject(subject)
                && Authentication.verifyPassword(subject, password);
        Account account = UserList.getUserAccount(subject);
        if (account != null) {
            LogList.makeLoginAttemptEntry(account, success);
        }
        return success;
    }
}