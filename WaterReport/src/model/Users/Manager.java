package model.Users;
/**
* Author: Osvaldo Armas
* Version: 1.0
* Date:  9/30/2016
*/
public class Manage extends Worker implements Account {

    public Manager(String name, String userName) {
        this.name = name;
        this.userName = userName;
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