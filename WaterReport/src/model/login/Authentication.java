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
        userMap.put("GPBurdell", "password");
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
        userMap.put(subject, password); // TODO sanitize inputs
    }
}