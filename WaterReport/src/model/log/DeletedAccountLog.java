package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds data specific to account deletion
 */
public class DeletedAccountLog extends Log {
    private final String deletedAccountID;

    // For all logs, we kept the responsibleAccount specific as possible
    // because not all user types can initiate actions. For example, a
    // regular User should not be able to ban an account; only an admin can.

    /**
     * Makes a DeletedAccountLog with a timestamp and given params
     * @param responsibleAccount the Admin that deleted the account
     * @param deletedAccountID the Account that was deleted
     */
    public DeletedAccountLog(Admin responsibleAccount, String deletedAccountID)
    {
        super(responsibleAccount);
        this.deletedAccountID = deletedAccountID;
    }

    /**
     * @param responsibleAccount The Adming that banned the account
     * @param deletedAccountID the userID of the banned account
     * @param timeStamp the String format of the timestamp
     */
    public DeletedAccountLog(Admin responsibleAccount, String deletedAccountID,
                            String timeStamp) {
        super(timeStamp, responsibleAccount);
        this.deletedAccountID = deletedAccountID;
    }
    @Override
    public String toString() {
        if (deletedAccountID != null) {
            return "'"
                    + getResponsibleAccount().getEmail() + "','"
                    + deletedAccountID + "','"
                    + getTimestamp().toString() + "'";
        } else {
            return "EMPTY LOG";
        }
    }
}
