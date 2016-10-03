package model.login;

import model.Users.Account;
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
    public Account getUserAccount(String userid) {
        return userMap.get(userid);
    }

    /**
     * Adds a userid, account entry to the userMap
     * @param userid The subject's userid
     * @param user The account object associated with the user account
     */
    public static void makeNewUser(String userid, Account user) {
        userMap.put(userid, user); // TODO make user object, check fields
    }
}
