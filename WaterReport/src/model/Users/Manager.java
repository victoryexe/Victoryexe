package model.Users;
/**
* Author: Osvaldo Armas
* Version: 1.0
* Date:  9/30/2016
*/
public class Manager extends Worker {

    public Manager(String name, String email) {
        this.name = name;
        this.email = email;
        super.userCount++;
        this.userID = userCount;
    }
    public AuthLevel getAuthLevel() {
        return AuthLevel.MANAGER;
    }
    public void viewHistoricalReports() {

    }
    public void viewPurityTrends() {

    }
    public void deleteReports() {
        
    }
}