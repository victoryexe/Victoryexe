package model.log;

import model.Users.Admin;

/**
 * Created by Alexandra on 10/27/2016.
 * A Log that holds data specific to account deletion
 */
public class DeletedAccountLog extends Log {
    private final String deletedAccountID;

    /**
     * Makes a DeletedAccountLog with a timestamp and given params
     * @param responsibleAccount the Admin that deleted the account
     * @param deletedAccountID the Account that was deleted
     */
    public DeletedAccountLog(Admin responsibleAccount, String deletedAccountID) {
        super(responsibleAccount);
        this.deletedAccountID = deletedAccountID;
    }

    /**
     * Gets the deleted account's userid
     * @return the deleted account's userid
     */
    public String getDeletedAccountID() {
        return deletedAccountID;
    }
}
