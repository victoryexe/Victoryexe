package model.login;

import model.Users.*;
import model.registration.UserFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexandra on 9/28/2016.
 */
public class UserList {
    private static Map<String, Account> userMap = new HashMap<>();
    private UserList() {
    }

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
    public static Account makeNewUser(String firstName, String lastName, String userid,
                                   String pass1, String pass2, AuthLevel auth) {
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
            throw new java.util.NoSuchElementException("No account is" +
                    "associated with the userid " + oldEmail);
        }
        userMap.put(newEmail, account);
        return Authentication.updateEmail(oldEmail, newEmail);
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
