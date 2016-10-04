package model.Users;
/**
* Author Osvaldo Armas
* Version 1.0
* Date:  9/30/2016
*/
public class Profile {
    private Account acc;

    public Profile(Account acc) {
        this.acc = acc;
    }
    public String getEmail() {
        return acc.getEmail();
    }
    public String getName() {
        return acc.getName();
    }
    public String getAddress() {
        return acc.getHomeAddress();
    }
    public String getTitle() {
    	return acc.getTitle();
    }
    public void changeTitle(String title) {
    	acc.setTitle(title);
    }
    public String changeAddress(String address) {
        acc.setHomeAddress(address);
    }
    public void changeEmail(String email) {
        acc.setEmail(email);
    }
    public void changePassword(String pw) {
        //TODO: PasswordRemapping
    }
}
