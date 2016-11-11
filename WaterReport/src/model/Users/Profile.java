package model.Users;
/**
 * Author Osvaldo Armas
 * Version 1.0
 * Date:  9/30/2016
 */
public class Profile {
    private final Account acc;

    /**
     * Creates a Profile for an Account
     * @param acc the Account to create a Profile for
     */
    public Profile(Account acc) {
        this.acc = acc;
    }

    /**
     * Gets the email associated with this Profile
     * @return the email as a String
     */
    public String getEmail() {
        return acc.getEmail();
    }

    /**
     * Gets the name associated with this Profile
     * @return the user's name on this Profile
     */
    public String getName() {
        return acc.getName();
    }

    /**
     * Gets the Address associated with this Profile
     * @return the user's home address
     */
    public Address getAddress() {
        return acc.getHomeAddress();
    }

    /**
     * Gets the Title associated with this Profile
     * @return the user's title
     */
    public String getTitle() {
        return acc.getTitle();
    }

    /**
     * Changes this User's Profile title to the provided one
     * @param title the user's updated title
     */
    public void changeTitle(String title) {
        acc.setTitle(title);
    }

    /**
     * Changes the User's Profile address to the provided one
     * @param address the user's updated Address
     */
    public void changeAddress(Address address) { acc.setHomeAddress(address); }

    /**
     * Changes the User's Profile email to the one provided
     * @param email the user's updated email address
     */
    public void changeEmail(String email) {
        acc.setEmail(email);
    }

    /**
     * Changes the User's Profile name to the one provided
     * @param name the user's updated name
     */
    public void changeName(String name) { acc.setName(name); }
}