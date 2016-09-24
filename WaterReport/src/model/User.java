package model;

public interface User {
    public String getName();
    public void setName(String name);
    public void setUserID(String uID);
    public void setPassword(String pw);
    public boolean verifyPassword(String pass); //Using getPassword would be a security problem.
    public boolean isAdmin();
}