package tests;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Users.Account;
import model.Users.AuthLevel;
import model.login.UserList;
import model.login.Authentication;


/**
 * Tests UserList.makeNewUser
 * @author Alexandra Durso
 * @version Version 1.2
 */
public class MakeNewUserTests {
    private Map<String, Account> usersReflect;
    private Map<String, String> credentialsReflect;
    private Map<String, String> credentials;

    @Before
    public void setUp() {
        credentials = new HashMap<>();
        usersReflect = new HashMap<>();
        credentialsReflect = new HashMap<>();

        try { // clear maps before each test
            Field field;
            field = UserList.class.getDeclaredField("userMap");
            field.setAccessible(true);
            ((HashMap<String, Account>) field.get("")).clear();

            field = Authentication.class.getDeclaredField("userMap");
            field.setAccessible(true);
            ((HashMap<String, Account>) field.get("")).clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setMaps() {
        try {
            Field field;
            field = UserList.class.getDeclaredField("userMap");
            field.setAccessible(true);
            usersReflect = (HashMap<String, Account>) field.get("");

            field = Authentication.class.getDeclaredField("userMap");
            field.setAccessible(true);
            credentialsReflect = (HashMap<String, String>) field.get("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMaps(List<Account> expected) {
        for (Account a : expected) {
            String key = a.getEmail();
            assertEquals("UserList does not contain user " + key,
                    true, usersReflect.containsKey(key));
            assertEquals("Authentication does not contain user " + key,
                    true, credentialsReflect.containsKey(key));
            assertSame("UserList account mapping incorrect for user " + a.getEmail(),
                    a, usersReflect.get(key));
            assertEquals("Authentication password incorrect for user " + a.getEmail(),
                    credentials.get(key), credentialsReflect.get(key));
        }
    }

    // All tests with null data fail since makeNewUser() does not do input checking.
    // All input checking is done in the controller, so this scenario will
    // never occur in the actual running of the application.
    @Test(timeout = 200)
    public void testNullData() {
        assertNull(null, UserList.makeNewUser(null, null, null, null, null, null));

        setMaps();
        assertEquals(0, usersReflect.size());
        assertEquals(0, credentialsReflect.size());
    }


//    @Test(timeout = 200)
//    public void testNullLast() {
//        assertNull(null, UserList.makeNewUser("First", null, "user@gmail.com",
//                "pass", "pass", AuthLevel.USER));
//
//        setMaps();
//        assertEquals(0, usersReflect.size());
//        assertEquals(0, credentialsReflect.size());
//    }
//
//    @Test(timeout = 200)
//    public void testNullEmail() {
//        assertNull(null, UserList.makeNewUser("First", "Last", null,
//                "pass", "pass", AuthLevel.USER));
//
//        setMaps();
//        assertEquals(0, usersReflect.size());
//        assertEquals(0, credentialsReflect.size());
//    }
//
//    @Test(timeout = 200)
//    public void testNullPasswords() {
//        assertNull(null, UserList.makeNewUser("First", "Last",
//                "user@gmail.com", null, null, AuthLevel.USER));
//
//        setMaps();
//        assertEquals(0, usersReflect.size());
//        assertEquals(0, credentialsReflect.size());
//    }
//
//    @Test(timeout = 200)
//    public void testNullAuthLevel() {
//        assertNull(null, UserList.makeNewUser("First", "Last",
//                "user@gmail.com", "pass", "pass", null));
//
//        setMaps();
//        assertEquals(0, usersReflect.size());
//        assertEquals(0, credentialsReflect.size());
//    }

    // Similarly, these methods do not behave as expected because the
    // controller does all input checking.

    @Test(timeout = 200)
    public void testEmptyStrings() {
        Account user = UserList.makeNewUser("", "", "", "", "",
                AuthLevel.USER);
        assertNull(user);

        setMaps();
        assertEquals(0, usersReflect.size());
        assertEquals(0, credentialsReflect.size());
    }

    @Test(timeout = 200)
    public void testInvalidEmailFormat() {
        Account user = UserList.makeNewUser("First", "Last", "askjfpwehf",
                "pass", "pass", AuthLevel.USER);
        assertNull(user);

        setMaps();
        assertEquals(0, usersReflect.size());
        assertEquals(0, credentialsReflect.size());
    }

    // -------------------------------------------------------------------
    // Things that should actually pass section!

    @Test(timeout = 200)
    public void testInvalidPasswords() {
        Account user = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "LOL", "WAT", AuthLevel.USER);
        assertNull(user);

        setMaps();
        assertEquals(0, usersReflect.size());
        assertEquals(0, credentialsReflect.size());
    }

    @Test(timeout = 200)
    public void testAddDuplicates() {
        Account user = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "MEH", "MEH", AuthLevel.USER);
        Account user2 = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "MEH", "MEH", AuthLevel.USER);
        Account user3 = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "MEH", "MEH", AuthLevel.WORKER);
        Account user4 = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "MEH", "MEH", AuthLevel.MANAGER);
        Account user5 = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "MEH", "MEH", AuthLevel.ADMIN);
        assertNotNull(user);
        assertNull(user2);
        assertNull(user3);
        assertNull(user4);
        assertNull(user5);

        setMaps();
        assertEquals(1, usersReflect.size());
        assertEquals(1, credentialsReflect.size());

        List<Account> expected = new LinkedList<>();
        expected.add(user);
        credentials.put(user.getEmail(), "MEH");
        checkMaps(expected);
    }

    @Test(timeout = 200)
    public void testGeneralOnce() {
        Account user = UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "pass", "pass", AuthLevel.USER);
        assertNotNull(user);

        setMaps();
        assertEquals(1, usersReflect.size());
        assertEquals(1, credentialsReflect.size());

        List<Account> expected = new LinkedList<>();
        expected.add(user);
        credentials.put(user.getEmail(), "pass");
        checkMaps(expected);
}

    @Test(timeout = 200)
    public void testGeneralMultiple() {
        List<Account> expected = new ArrayList<>(5);
        expected.add(UserList.makeNewUser("David", "Arida", "darida@gatech.edu",
                "1234", "1234", AuthLevel.USER));
        expected.add(UserList.makeNewUser("Ozzy", "Armas", "oarmas@gatech.edu",
                "password", "password", AuthLevel.WORKER));
        expected.add(UserList.makeNewUser("Dacorvyn", "Young", "dyoung@gatech.edu",
                "pass", "pass", AuthLevel.MANAGER));
        expected.add(UserList.makeNewUser("Owen", "Brahms", "obrahms@gatech.edu",
                "123456", "123456", AuthLevel.ADMIN));
        expected.add(UserList.makeNewUser("Alex", "Durso", "adurso3@gatech.edu",
                "pass", "pass", AuthLevel.USER));
        for (int i = 0; i < expected.size(); i++) {
            assertNotNull("Expected non-null element at index " + i, expected.get(i));
        }

        setMaps();
        assertEquals(5, usersReflect.size());
        assertEquals(5, credentialsReflect.size());

        credentials.put(expected.get(0).getEmail(), "1234");
        credentials.put(expected.get(1).getEmail(), "password");
        credentials.put(expected.get(2).getEmail(), "pass");
        credentials.put(expected.get(3).getEmail(), "123456");
        credentials.put(expected.get(4).getEmail(), "pass");
        checkMaps(expected);
    }

    @Test(timeout = 200)
    public void testGeneralHeavy() {
        List<Account> expected = new ArrayList<>(40);

        // Makes 40 accounts in the pattern User, Worker, Manager, Admin
        for (int i = 0; i < 40; i++) {
            AuthLevel level;
            if (i % 4 == 0) {
                level = AuthLevel.USER;
            } else if (i % 4 == 1) {
                level = AuthLevel.WORKER;
            } else if (i % 4 == 2) {
                level = AuthLevel.MANAGER;
            } else {
                level = AuthLevel.ADMIN;
            }
            expected.add(UserList.makeNewUser("User", "User",
                    "user" + i + "@gatech.edu", "1234", "1234", level));
        }

        // Adds duplicates
        for (int i = 0; i < 40; i++) {
            Account duplicate = UserList.makeNewUser("Ditto", "",
                    "user" + i + "@gatech.edu", "1234", "1234", AuthLevel.USER);
            assertNull(duplicate);
        }

        for (int i = 0; i < expected.size(); i++) {
            assertNotNull("Expected non-null element at index " + i, expected.get(i));
        }

        setMaps();
        assertEquals(40, usersReflect.size());
        assertEquals(40, credentialsReflect.size());

        for (int i = 0; i < expected.size(); i++) {
            credentials.put(expected.get(i).getEmail(), "1234");
        }
        checkMaps(expected);
    }
}
