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

    public static boolean verifyCredentials(String subject, String password) {
        return UserList.verifySubject(subject) && UserList.verifyPassword(subject, password);
    }
}