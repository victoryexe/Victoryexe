package model.login;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Alexandra on 9/24/2016.
 */
public class Authentication {
    private static Map<String, CharSequence> userMap = new HashMap<>();
    private Authentication() {
    }

    /**
     * Verifies that the user exists
     * @param userid The username
     * @return true iff the userid exists
     */
    public static boolean verifySubject(String userid) {
        return userMap.containsKey(userid);
    }

    /**
     * Verifies the entered password is correct
     * @param password The user-entered password
     * @return true iff the password is indeed the user's password
     */
    public static boolean verifyPassword(String userid, String password) {
        // TODO currently plain text string matching
        return userMap.get(userid).equals(password);
    }

    /**
     * Adds a new password, userid entry to userMap
     * @param subject the userid of the new account
     * @param password the password associated with the account
     */
    public static void addNewAccount(String subject, String password) {
        if (sanitizePassword(password)) {
            userMap.put(subject, password);
        } else {
            // TODO throw custom exception?
        }
    }

    /**
     * Deletes previous user-id: pass mapping and makes a new one
     * @param oldEmail the user's old email
     * @param newEmail the user's new email
     * @param pass1 the user's password
     * @param pass2 confirm password
     */
    public static void updateAccount(String oldEmail, String newEmail, String pass1, String pass2) {
        CharSequence oldPass = userMap.remove(oldEmail);
        if(oldPass != null && pass1.equals(pass2) && sanitizePassword((pass1))) {
            userMap.put(newEmail, pass1);
        }
    }

    /**
     * Deletes previous user-id: pass mapping and makes a new one
     * @param oldEmail the user's old email
     * @param newEmail the user's new email
     */
    public static void updateEmail(String oldEmail, String newEmail) {
        CharSequence oldPass = userMap.remove(oldEmail);
        userMap.put(newEmail, oldPass);
    }


    /**
     * Checks whether password is safe to store
     * @param password the password to check
     * @return true if the password is safe, false otherwise
     */
    private static boolean sanitizePassword(String password) {
        return true;
        // TODO
    }
}