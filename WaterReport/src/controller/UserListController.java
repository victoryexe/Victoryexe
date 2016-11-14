package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Users.Account;
import model.login.UserList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by grizz on 11/11/2016.
 * Handles the functions of the User list
 */
class UserListController {

    public UserListController(ListView<Account> userlist, Button deleteuser, Button banuser, Button unblockuser) {
        List<Account> users =
                UserList.getUserList().stream().map(UserList::getUserAccount).collect(Collectors.toList());
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
