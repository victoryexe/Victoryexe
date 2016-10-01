package model.Users;
/**
* Author Osvaldo Armas
* Version 2.0
* Date:  9/30/2016
*/
public interface Account {
    public String getName();
    public void setName(String name);
    public int getUserID();
    public String getEmail();
    public void setEmail(String email);
    public AuthLevel getAuthLevel();
}