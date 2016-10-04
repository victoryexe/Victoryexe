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
     * @return the Account associated with the userid
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
     */
    public static void makeNewUser(String firstName, String lastName, String userid,
                                   String pass1, String pass2, AuthLevel auth) {
        if (isInputValid(firstName, lastName, userid, pass1, pass2)) {
            Account account = UserFactory.makeAccount(firstName, lastName, userid, auth);
            userMap.put(userid, account);
            Authentication.addNewAccount(userid, pass1);
        }
    }

    /**
     * Checks whether the user-provided input is valid
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userid the user's email
     * @param password1 the user's password
     * @param password2 confirm password
     * @return true iff all fields are valid
     */
    public static boolean isInputValid(String firstName, String lastName, String userid,
                                       String password1, String password2) {
        if (firstName == null || lastName == null || userid == null
                || password1 == null || password2 == null) { // null checks
            return false;
        }

        if (userMap.containsKey(userid) || !userid.contains("@")) {
            return false;
        }

        if (!password1.equals(password2)) { // check that passwords match
            return false;
        }

        return true;
    }
}
