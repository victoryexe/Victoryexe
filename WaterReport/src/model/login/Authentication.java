package model.login;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by Alexandra on 9/24/2016.
 * A helper class that contains a map of userids to passwords, methods
 * to update these entries, and methods to check whether users exist
 * in the map.
 */
public class Authentication {
    private static final Map<String, CharSequence> userMap = new HashMap<>();

    /**
     * Verifies that the user exists
     * @param userid The username
     * @return true iff the userid exists
     */
    static boolean verifySubject(String userid) {
        return userMap.containsKey(userid);
    }

    /**
     * Verifies the entered password is correct
     * @param userid the userid the user entered
     * @param password The user-entered password
     * @return true iff the password is indeed the user's password
     */
    static boolean verifyPassword(String userid, String password) {
        // TODO currently plain text string matching
        return userMap.get(userid).equals(password);
    }

    /**
     * Adds a new password, userid entry to userMap
     * @param subject the userid of the new account
     * @param password the password associated with the account
     * @param password2 the confirmed password
     * @return true if the user was successfully added to the map
     */
    static boolean addNewAccount(String subject, String password,
                                 String password2) {
        if (userMap.containsKey(subject)) {
            return false;
        }
        if (password.equals(password2)) { // TODO plain string matching
            userMap.put(subject, password);
            return true;
        }
        return false;
    }

    /**
     * Deletes previous user-id: pass mapping and makes a new one
     * @param oldEmail the user's old email
     * @param newEmail the user's new email
     * @param pass1 the user's password
     * @param pass2 confirm password
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given oldEmail
     * @return true iff the map was updated, false otherwise
     */
    private static boolean updateAccount(String oldEmail, String newEmail,
                                           String pass1, String pass2) {
        CharSequence oldPass = userMap.remove(oldEmail);
        if (oldPass == null) {
            throw new java.util.NoSuchElementException("No existing user"
                    + "with the email " + oldEmail);
        }
        if (pass1.equals(pass2)) {
            userMap.put(newEmail, pass1);
            return true;
        }
        return false;
    }

    /**
     * Deletes previous user-id: pass mapping and makes a new one
     * @param oldEmail the user's old email
     * @param newEmail the user's new email
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given oldEmail
     * @return true iff the map was updated, false otherwise
     */
    static boolean updateEmail(String oldEmail, String newEmail) {
        String pass = (String) userMap.get(oldEmail);
        if (pass == null) {
            throw new java.util.NoSuchElementException("No existing user"
                    + "with the email " + oldEmail);
        }
        return updateAccount(oldEmail, newEmail, pass, pass);
    }

    // For methods such as deleteAccount, banAccount, and unblockAccount,
    // we chose to adhere to previous design choice in that a successful
    // action returns true, otherwise false. For these methods, these actions
    // should always be true.

    /**
     * Deletes the MapEntry associated with the given userid
     * @param userid the userid to delete
     * @throws java.util.NoSuchElementException if no user is associated
     * with the given userid
     * @return true if the Account was successfully deleted
     */
    static boolean deleteAccount(String userid) {
        CharSequence removed = userMap.remove(userid);
        if (removed == null) {
            throw new java.util.NoSuchElementException("No existing user"
                + "with the email " + userid);
        }
        return true;
    }
}