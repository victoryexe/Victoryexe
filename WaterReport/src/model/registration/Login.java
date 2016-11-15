package model.registration;

import model.Users.Account;
import model.log.LogList;
import model.log.UnblockAccountLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by grizz on 9/19/2016.
 * A class that controls the action of logging in
 */
public class Login {
    private static final int BLOCK_ACCOUNT_WINDOW = 15;
    private static final int MAX_UNSUCCESSFUL_LOGIN_ATTEMPTS = 3;

    /**
     * Verifies credentials and logs the attempt
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
            if (!success) { // block account if necessary
                List<UnblockAccountLog> log = LogList.getUnblockAccountLog();
                int attempts = 0;
                boolean windowActive = true;
                for (int i = log.size() - 1; (i >= 0) && windowActive; i--) {
                    // checks for recent attempts
                    if (LocalDateTime.now().minusMinutes(BLOCK_ACCOUNT_WINDOW)
                            .isBefore(log.get(i).getTimestamp())) {
                        if (log.get(i).getResponsibleAccount().equals(account))
                        {
                            attempts++;
                        }
                    } else { // if Log is outside BLOCK_ACCOUNT_WINDOW, exit
                        windowActive = false;
                    }
                    if (attempts >= MAX_UNSUCCESSFUL_LOGIN_ATTEMPTS) {
                        account.setIsBlocked();
                    }
                }
            }
        }
        return success;
    }
}