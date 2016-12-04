package controller;

import db.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Users.Account;
import model.Users.Admin;
import model.Users.AuthLevel;
import model.registration.UserList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by grizz on 11/11/2016.
 * Handles the functions of the User list
 */
class UserListController {
    private static ListView<Account> userlist;

    public UserListController(ListView<Account> userlist, Button deleteuser, Button banuser, Button unblockuser) {
        this.userlist = userlist;
        List<Account> users =
                UserList.getUserList().stream().map(UserList::getUserAccount).collect(Collectors.toList());
        userlist.setItems(javafx.collections.FXCollections.observableList(users));
        deleteuser.setOnAction(event -> {
            Account account = userlist.getSelectionModel().getSelectedItem();
            if(account == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "No Account Selected.");
                alert.show();
            } else if(account.equals(LoginScreenController.getCurrUser())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Cannot delete current user.");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete Account: " + account.getEmail() + "?");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> deleteAccount(account));
            }
        });
        banuser.setOnAction(event -> {
            Account account = userlist.getSelectionModel().getSelectedItem();
            if(account == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "No Account Selected.");
                alert.show();
            } else if (account.getIsBanned()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Account is already banned.");
                alert.show();
            } else if(AuthLevel.ADMIN.equals(account.getAuthLevel())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Cannot ban Admins.");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to ban Account: " + account.getEmail() + "?");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> banAccount(account));
            }
        });
        unblockuser.setOnAction(event -> {
            Account account = userlist.getSelectionModel().getSelectedItem();
            if(account == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "No Account Selected.");
                alert.show();
            } else if (!account.getIsBlocked()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Account is not Blocked.");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to unblock Account: " + account.getEmail() + "?");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> unblockAccount(account));
            }
        });
    }

    private void deleteAccount(Account acc) {
        UserList.deleteAccount((Admin) LoginScreenController.getCurrUser(),
                acc.getEmail());
        DB.deleteAccount(acc);
        updateUserList();
    }

    private void banAccount(Account acc) {
        UserList.banAccount((Admin) LoginScreenController.getCurrUser(),
                acc.getEmail());
        updateUserList();
    }

    private void unblockAccount(Account acc) {
        UserList.unblockAccount((Admin) LoginScreenController.getCurrUser(),
                acc.getEmail());
        updateUserList();
    }

    public static void updateUserList() {
        List<Account> users =
                UserList.getUserList().stream().map(UserList::getUserAccount).collect(Collectors.toList());
        userlist.setItems(javafx.collections.FXCollections.observableList(users));
    }
}
