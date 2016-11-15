package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds data specific to unblocking accounts
 */
public class UnblockAccountLog extends Log {
    private final String unbannedAccountID;

    // For all logs, we kept the responsibleAccount specific as possible
    // because not all user types can initiate actions. For example, a
    // regular User should not be able to ban an account; only an admin can.

    /**
     * Makes an UnblockAccount Log with a timestamp anf given params
     * @param responsibleAccount the Admin that unblocked the account
     * @param unblockedAccountID the Account that was unblocked
     */
    public UnblockAccountLog(Admin responsibleAccount,
                             String unblockedAccountID) {
        super(responsibleAccount);
        this.unbannedAccountID = unblockedAccountID;
    }

    @Override
    public String toString() {
        if (unbannedAccountID != null) {
            return "'" + getTimestamp().toString() + "','"
                    + getResponsibleAccount().getEmail() + "','"
                    + unbannedAccountID + "'";
        } else {
            return "EMPTY LOG";
        }
    }
}
