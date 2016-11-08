package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds relevant data for the specific action of banning an account
 */
class BannedAccountLog extends Log {
    private final String bannedAccountID;

    /**
     * Makes a BannedAccount log with a timestamp ang given params
     * @param responsibleAccount the Admin that banned the account
     * @param bannedAccountID the userid of the Account being banned account
     */
    BannedAccountLog(Admin responsibleAccount, String bannedAccountID) {
        super(responsibleAccount);
        this.bannedAccountID = bannedAccountID;
    }

    /**
     * Gets the the bannedAccountID of this BannedAccountLog
     * @return the userid of the account that was banned
     */
    public String getBannedAccountID() {
        return bannedAccountID;
    }
}
