package tests.DBTests;
import db.DB;
import java.sql.Connection;
import model.Users.Account;
import model.Users.Address;
import model.Users.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by Ozzy on 11/15/16.
 * RUN DELETE USERS BEFORE RUNNING THIS TEST
 */
public class AddUserToDatabaseTest {
    Account acc1 = new User("User1","user@email.com");
    Account acc2 = new User("User2","user2@email.com");
    @Before
    public void setUp() {
        acc1.setHomeAddress(new Address("123 Street", 123,"Miami","FL",33144,"USA"));
        acc1.setTitle("Mr");
    }
    @Test(timeout = 2000)
    public void testAddUser() {
        try {
            //Fully Defined User
            assertTrue(DB.addAccount(acc1));
            //Partially Defined User
            assertTrue(DB.addAccount(acc2));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }
    @Test(timeout = 2000)
    public void testDeleteUser() {
        try {
            //Delete Both Accounts
            assertTrue(DB.deleteAccount(acc1));
            //Delete Both Accounts
            assertTrue(DB.deleteAccount(acc2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
