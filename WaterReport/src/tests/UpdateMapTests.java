package tests;

import model.Users.AuthLevel;
import model.login.Authentication;
import org.junit.Before;
import org.junit.Test;

import model.login.Login;
import model.login.UserList;
import model.Users.Account;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;


/**
 * Created by Dacorvyn on 11/9/2016.
 *
 */
public class UpdateMapTests {
    private Map<String, Account> uReflect;

    @Before
    public void setUp() {

        try { // clear maps before each test
            Field field;
            field = UserList.class.getDeclaredField("userMap");
            field.setAccessible(true);
            ((HashMap<String, Account>) field.get("")).clear();
            uReflect = (HashMap<String, Account>) field.get("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*
        So the way the controller is designed, it handles all the input controll so that
        there will never be a truly invalid input such as a null allowed for the updateMap. Therefore
        While I could write a test Null, Test Empty string or test old and new being the same, but the controllers
         and UI are designed to deal with those issues.
     */
    @Test(timeout = 200)
    public void testUpdateWithEmail()
    {
        UserList.makeNewUser("Dac", "You", "chirp@herp.slurp",
                "Fut", "Fut", AuthLevel.USER);
        assertTrue(uReflect.containsKey("chirp@herp.slurp"));
        assertTrue("Successfully Updated UserList",UserList.updateMap("chirp@herp.slurp","a@c"));
    }

    @Test
    public void testUpdatedLogin() {
        UserList.makeNewUser("Dac", "You", "chirp@herp.slurp",
                "Fut", "Fut", AuthLevel.USER);
        assertTrue(uReflect.containsKey("chirp@herp.slurp"));
        assertTrue("Successfully Updated UserList",UserList.updateMap("chirp@herp.slurp","a@c"));
        assertTrue("New email can login",Login.login("a@c", "Fut"));
        assertFalse("Old email cannot login", Login.login("chirp@herp.slurp","Fut"));

    }

}
