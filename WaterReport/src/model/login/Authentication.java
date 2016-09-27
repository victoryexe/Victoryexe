package model.login;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Alexandra on 9/24/2016.
 */
public class Authentication {
    private static Map<String, CharSequence> map = new HashMap<>();
    private Authentication() {
    }

    private static void populateMap() { // TODO remove after M4
        map.put("GPBurdell", "password");
    }

    /**
     * Verifies that the user exists
     * @param userid The username
     * @return true iff the userid exists
     */
    public static boolean verifySubject(String userid) {
        populateMap();
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

    /**
     * Gets the set of users
     * @return a Set containing usernames as Strings
     */
    public Set<String> getUserList() {
        return map.keySet();
    }
}