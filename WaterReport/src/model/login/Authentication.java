package model.login;

import lib.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by Alexandra on 9/24/2016.
 */
public class Authentication {
    private static Map<String, String> map = new HashMap<>();
    private Authentication() {
    }

    private void populateMap() throws PasswordStorage.CannotPerformOperationException {
        map.put("GPBurdell", PasswordStorage.createHash("password"));
        // TODO remove after M4
    }

    /**
     * Verifies that the user exists
     * @param userid The username
     * @return true iff the userid exists
     */
    public static boolean verifySubject(String userid) {
        return map.containsKey(userid);
    }

    /**
     * Verifies the entered password is correct
     * @param password The user-entered password
     * @return true iff the password is indeed the user's password
     */
    public static boolean verifyPassword(String userid, String password)
            throws PasswordStorage.CannotPerformOperationException,
                   PasswordStorage.InvalidHashException {
        return PasswordStorage.verifyPassword(password, map.get(userid));
    }
}
