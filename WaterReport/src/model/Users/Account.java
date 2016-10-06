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
    public String getName();
    /**
    * @param name of the Account Owner
    **/
    public void setName(String name);
    /**
    * @return the UserID of the Account Owner
    **/
    public int getUserID();
    /**
    * @return the Email of the Account Owner
    **/
    public String getEmail();
    /**
    * @param the Email of the Account Owner
    **/
    public void setEmail(String email);
    /**
    * @return the Home Address of the Account Owner
    **/
    public Address getHomeAddress();
    /**
     * @param address of the Account Owner
     */
    public void setHomeAddress(Address address);
    /**
    * @param the Title of the Account Owner
    **/
    public void setTitle(String title);
    /**
    * @return the title of the Account Owner
    **/
    public String getTitle();
    /**
    * @return the Authorization of the Account Owner
    **/
    public AuthLevel getAuthLevel();
}
