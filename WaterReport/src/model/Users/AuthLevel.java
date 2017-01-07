package model.Users;

/**
 * Created by Alexandra on 9/29/2016.
 * An enum class representing the types of users the system allows
 */
public enum AuthLevel {
    USER, WORKER, MANAGER, ADMIN

    /*public static AuthLevel fromString(String str) {
        switch (str) {
            case "USER" :
                return USER;
            case "WORKER" :
                return WORKER;
            case "MANAGER" :
                return MANAGER;
            case "ADMIN" :
                return ADMIN;
            default:
                return null;
        }
    }
    */
}
