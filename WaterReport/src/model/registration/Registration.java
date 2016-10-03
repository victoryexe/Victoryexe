package model.registration;

import model.login.UserList;
import model.login.Authentication;
import model.Users.Account;

/**
 * Created by Alexandra on 9/29/2016.
 */
public class Registration {
    private Registration() {
    }

    /**
     * Creates an account with the given username and password.
     * @param userid The userid of the new account
     * @param password The password associated with the new userid
     * @param account The account associated with the new userid
     */
    public static void createAccount(String userid, String password, Account account) {
        UserList.makeNewUser(userid, account);
        Authentication.addNewAccount(userid, password);
    }

    // TODO
    // Make an instance of user
}
