package model.login;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by Alexandra on 9/24/2016.
 */
public class Authentication {
    private static Map<String, CharSequence> map = new HashMap<>();
    private Authentication(String subject, CharSequence pass) { // TODO probably want to redo this for privacy
        map.put(subject, pass);
    }

    private void populateMap() { // TODO remove after M4

    }

    /**
     * Verifies that the user exists
     * @param userid The username
     * @return true iff the userid exists
     */
    public static boolean verifySubject(String userid) {
        map.put("GPBurdell", "password");
        return map.containsKey(userid);
    }

    /**
     * Verifies the entered password is correct
     * @param password The user-entered password
     * @return true iff the password is indeed the user's password
     */
    public static boolean verifyPassword(String userid, String password) {
        // TODO currently plain text string matching
        return map.get(userid).equals(password);
    }
}