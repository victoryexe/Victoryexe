package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Users.Account;
import model.login.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grizz on 11/11/2016.
 */
public class UserListController {

    public UserListController(ListView<Account> userlist, Button deleteuser, Button banuser, Button unblockuser) {
        List<Account> users = new ArrayList<>();
        for(String user: UserList.getUserList()) {
            users.add(UserList.getUserAccount(user));
        }
        userlist.setItems(javafx.collections.FXCollections.observableList(users));
        deleteuser.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Feature not yet implemented.", ButtonType.CLOSE);
            alert.show();
        });
        banuser.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Feature not yet implemented.", ButtonType.CLOSE);
            alert.show();
        });
        unblockuser.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Feature not yet implemented.", ButtonType.CLOSE);
            alert.show();
        });
    }
}
