package model.login;

import model.Users.Account;
import model.Users.Admin;
import model.Users.AuthLevel;
import model.log.LogList;
import model.registration.UserFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexandra on 9/28/2016.
 * A class that holds a map of all users in the system and their respective
 * Accounts. Oversees creation of new users and adds them to the list, permits
 * updates, and performs existence checks of Accounts.
 */
public class UserList {
    private static Map<String, Account> userMap = new HashMap<>();

    /**
     * Gets the set of users
     * @return a Set containing usernames as Strings
     */
    public Set<String> getUserList() {
        return userMap.keySet();
    }

    /**
     * Gets the Account object associated with the given userid
     * @param userid the userid of the desired account
     * @return the Account associated with the userid or null if none exists
     */
    public static Account getUserAccount(String userid) {
        return userMap.get(userid);
    }


    /**
     * If input is valid, makes a new Account and updates Authentication and
     * UserList's maps.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userid the user's email
     * @param pass1 the user's password
     * @param pass2 confirm password
     * @param auth the user's specified AuthLevel
     * @return the Account added to the userMap, or null if the Account
     * could not be added
     */
    public static Account makeNewUser(String firstName, String lastName,
                                      String userid, String pass1, String pass2, AuthLevel auth) {
        Account account = UserFactory.makeAccount(firstName, lastName, userid, auth);
        if (userMap.containsKey(account.getEmail())
                || Authentication.verifySubject(account.getEmail())) {
            return null;
        }
        userMap.put(userid, account);
        Authentication.addNewAccount(userid, pass1);
        return account;
    }

    /**
     * Given a new userid, updates the map, deleting the old entry
     * @param oldEmail the user's old email
     * @param newEmail the user's new email
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given oldEmail
     * @return true iff the map was updated, false otherwise
     */
    public static boolean updateMap(String oldEmail, String newEmail) {
        Account account = userMap.remove(oldEmail);
        if (account == null) {
            throw new java.util.NoSuchElementException("No account is"
                    + "associated with the userid " + oldEmail);
        }
        account.setEmail(newEmail);
        userMap.put(newEmail, account);
        return Authentication.updateEmail(oldEmail, newEmail);
    }

    /**
     * Deletes the userid-Account mapping associated with the given userid
     * Precondition: Admin is logged into the system
     * @param admin the Admin deleting an Account
     * @param userid the userid of the Account to be deleted
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given userid
     * @return true iff the Account was successfully deleted
     */
    public static boolean deleteAccount(Admin admin, String userid) {
        Account deleted = userMap.remove(userid);
        if (deleted == null || !Authentication.deleteAccount(userid)) {
            throw new java.util.NoSuchElementException("No account is"
                    + "associated with the userid " + userid);
        }
        LogList.makeDeletedAccountEntry(admin, userid);
        return true;
    }

    /**
     * Bans the Account associated with the given userid
     * Precondition: Admin is logged into the system
     * @param admin the Admin banning an Account
     * @param userid the userid of the Account to be banned
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given userid
     * @return true iff the Account was successfully deleted
     */
    public static boolean banAccount(Admin admin, String userid) {
        Account account = userMap.get(userid);
        if (account == null) {
            throw new java.util.NoSuchElementException("No account is"
                    + "associated with the userid " + userid);
        }
        account.setIsBanned();
        LogList.makeBannedAccountEntry(admin, userid);
        return true;
    }

    /**
     * Unblocks the Account associated with the given userid
     * Precondition: Admin is logged into the system
     * @param admin the Admin banning an Account
     * @param userid the userid of the Account to be unblocked
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given userid
     * @return true iff the Account was successfully unblocked
     */
    public static boolean unblockAccount(Admin admin, String userid) {
        Account account = userMap.get(userid);
        if (account == null) {
            throw new java.util.NoSuchElementException("No account is +"
                    + "associated with the userid " + userid);
        }
        account.setIsBlocked();
        LogList.makeUnblockAccountEntry(admin, userid);
        return true;
    }

    /**
     * Checks if the given userid exists in the userMap
     * @param userid the userid to check
     * @return true iff userMap contains the userid
     */
    public static boolean containsUserID(String userid) {
        return userMap.containsKey(userid);
    }
}
