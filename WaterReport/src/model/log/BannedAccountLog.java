package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 */
public class BannedAccountLog extends Log {
    private String bannedAccountID;

    /**
     * Makes a BannedAccount log with a timestamp ang given params
     * @param responsibleAccount the Admin that banned the account
     * @param bannedAccountID the userid of the Account being banned account
     */
    public BannedAccountLog(Admin responsibleAccount, String bannedAccountID) {
        super(responsibleAccount);
        this.bannedAccountID = bannedAccountID;
    }

    public String getBannedAccountID() {
        return bannedAccountID;
    }
}
