package model.log;

import model.Users.Account;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds information specific to login attempts
 */
public class LoginAttemptLog extends Log {
    private final boolean successStatus;

    /**
     * Makes a new LoginAttemptLog with a timestamp and the given params
     * @param responsibleAccount the account logging in
     * @param successStatus whether the user's login succeeded
     */
    public LoginAttemptLog(Account responsibleAccount, boolean successStatus) {
        super(responsibleAccount);
        this.successStatus = successStatus;
    }

    /**
     * Gets whether the login attempt was successful or not
     * @return true iff the login attempt succeeded
     */
    public boolean getSuccessStatus() {
        return successStatus;
    }
}
