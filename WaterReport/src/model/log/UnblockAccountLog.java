package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 */
public class UnblockAccountLog extends Log {
    private String unbannedAccountID;

    /**
     * Makes an UnblockAccount Log with a timestamp anf given params
     * @param responsibleAccount the Admin that unblocked the account
     * @param unblockedAccountID the Account that was unblocked
     */
    public UnblockAccountLog(Admin responsibleAccount, String unblockedAccountID) {
        super(responsibleAccount);
        this.unbannedAccountID = unblockedAccountID;
    }

    /**
     * Gets the unblocked account's userid
     * @return String representing the unbanned account's userid
     */
    public String getUnbannedAccountID() {
        return unbannedAccountID;
    }
}
