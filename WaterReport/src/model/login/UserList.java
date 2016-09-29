package model.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexandra on 9/28/2016.
 */
public class UserList {
    private static Map<String, CharSequence> userMap = new HashMap<>();
    private UserList() {
    }

    private static void populateMap() { // TODO remove after M4
        userMap.put("GPBurdell", "password");
    }

    /**
     * Verifies that the user exists
     * @param userid The username
     * @return true iff the userid exists
     */
    public static boolean verifySubject(String userid) {
        populateMap();
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
     * Gets the set of users
     * @return a Set containing usernames as Strings
     */
    public Set<String> getUserList() {
        return userMap.keySet();
    }

    public static void makeNewUser(String subject, String password) {
        userMap.put(subject, password); // TODO santitize inputs
    }
}
