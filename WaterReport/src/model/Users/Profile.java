package model.Users;
/**
* Author Osvaldo Armas
* Version 1.0
* Date:  9/30/2016
*/
public class Profile {
    private User user;

    public Profile(User user) {
        this.user = user;
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getName() {
        return user.getName();
    }
    public String getAddress() {
        return user.getAddress();
    }
    public String getTitle() {
    	return user.getTitle();
    }
    public void changeTitle(String title) {
    	user.setTitle(title);
    }
    public void changeAddress(String address) {
        user.setHomeAddress(address);
    }
    public void changeEmail(String email) {
        user.setEmail(email);
    }
    public void changePassword(String pw) {
        //TODO: PasswordRemapping
    }
}