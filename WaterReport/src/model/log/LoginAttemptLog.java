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
     * @param responsibleAccount The Adming that banned the account
     * @param successStatus loginAttemptSuccess
     * @param timeStamp the String format of the timestamp
     */
    public LoginAttemptLog(Account responsibleAccount, boolean successStatus,
                            String timeStamp) {
        super(timeStamp, responsibleAccount);
        this.successStatus = successStatus;
    }
    @Override
    public String toString() {
        return "'" + getResponsibleAccount().getEmail() + "',"
                + successStatus + ",'"
                + getTimestamp().toString() + "'";
    }
}
