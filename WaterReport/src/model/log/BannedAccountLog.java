package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds relevant data for the specific action of banning an account
 */
public class BannedAccountLog extends Log {
    private final String bannedAccountID;

    // For all logs, we kept the responsibleAccount specific as possible
    // because not all user types can initiate actions. For example, a
    // regular User should not be able to ban an account; only an admin can.

    /**
     * Makes a BannedAccount log with a timestamp ang given params
     * @param responsibleAccount the Admin that banned the account
     * @param bannedAccountID the userid of the Account being banned account
     */
    public BannedAccountLog(Admin responsibleAccount, String bannedAccountID) {
        super(responsibleAccount);
        this.bannedAccountID = bannedAccountID;
    }

    /**
     * @param responsibleAccount The Adming that banned the account
     * @param bannedAccountID the userID of the banned account
     * @param timeStamp the String format of the timestamp
     */
    public BannedAccountLog(Admin responsibleAccount, String bannedAccountID,
                            String timeStamp) {
        super(timeStamp, responsibleAccount);
        this.bannedAccountID = bannedAccountID;
    }
    @Override
    public String toString() {
        if (bannedAccountID != null) {
            return "'" + getTimestamp().toString() + "','"
                    + getResponsibleAccount().getEmail() + "','"
                    + bannedAccountID + "'";
        } else {
            return "EMPTY LOG";
        }
    }
}
