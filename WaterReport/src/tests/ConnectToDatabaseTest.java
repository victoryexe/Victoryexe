//package tests;
//import db.DB;
////import java.sql.Connection;
//import model.Users.Account;
//import model.Users.Address;
//import model.Users.User;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertTrue;
//
//
///**
// * Created by Ozzy on 11/15/16.
// */
//public class connectToDatabaseTest {
//    boolean connected;
//    Account acc1 = new User("User1","user@email.com");
//    Account acc2 = new User("User2","user@email.com");
//
//    @Before
//    public void setUp() {
//        acc1.setHomeAddress(new Address("123 Stree", 123,"Miami","FL",33144,"USA"));
//        acc1.setTitle("Mr");
//    }
//    @Test(timeout = 200)
//    public void testAddUser() {
//        try {
//            assertTrue(DB.addAccount(acc1.getName(),acc1.getEmail(),acc1.getUserID(),acc1.getHomeAddress().toString(),
//                    acc1.getTitle(),"USER",true,true));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
