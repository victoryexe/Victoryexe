package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public interface Account {
    /**
    * @return the name of the Account Owner
    **/
    String getName();
    /**
    * @param name of the Account Owner
    **/
    void setName(String name);
    /**
    * @return the UserID of the Account Owner
    **/
    int getUserID();
    /**
    * @return the Email of the Account Owner
    **/
    String getEmail();
    /**
    * @param email of the Account Owner
    **/
    void setEmail(String email);
    /**
    * @return the Home Address of the Account Owner
    **/
    Address getHomeAddress();
    /**
     * @param address of the Account Owner
     */
    void setHomeAddress(Address address);
    /**
    * @param title of the Account Owner
    **/
    void setTitle(String title);
    /**
    * @return the title of the Account Owner
    **/
    String getTitle();
    /**
    * @return the Authorization of the Account Owner
    **/
    AuthLevel getAuthLevel();

    /**
     * Gets this Account's banned status
     * @return true if this Account is banned, false otherwise
     */
    boolean getIsBanned();

    /**
     * Flips this Account's banned status: if the Account is currently
     * banned this will set isBanned to false, and vice-versa
     */
    void setIsBanned();

    /**
     * method used in Account Loading setting the Account as banned or not banned
     * @param ban whether or not the Account is banned
     */
    void setBanned(boolean ban);

    /**
     * Gets this Account's blocked status
     * @return true if this Account is blocked, otherwise false
     */
    boolean getIsBlocked();

    /**
     * Method used in Account Loading setting the Account as blocked or not blocked
     * @param block whether or not the Account is blocked
     */
    void setBlocked(boolean block);

    /**
     * Flips this Account's blocked status
     */
    void setIsBlocked();
}
