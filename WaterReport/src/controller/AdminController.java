package controller;

import db.DB;
import fxapp.Main;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.Users.Account;
import model.log.Log;
import model.log.LogList;
import model.registration.Authentication;
import model.registration.UserList;

/**
 * Created by grizz on 11/11/2016.
 * Handles delegation of tasks for the main Admin screen to the
 * appropriate Controllers;
 */
public class AdminController {

    @FXML
    private TextField adminlastnametextbox;
    @FXML
    private TextField adminemailtextbox;
    @FXML
    private TextField adminstreetaddresstextbox;
    @FXML
    private TextField admincitytextbox;
    @FXML
    private TextField adminstatetextbox;
    @FXML
    private TextField adminaptnumtextbox;
    @FXML
    private TextField adminfirstnametextbox;
    @FXML
    private TextField adminzipcodetextbox;
    @FXML
    private TextField admincountrytextbox;
    @FXML
    private Button salutationedit;
    @FXML
    private Button adminsubmit;
    @FXML
    private Button Logout;
    @FXML
    private Button banuser;
    @FXML
    private Button deleteuser;
    @FXML
    private Button unblockuser;
    @FXML
    private Button refreshAdmin;
    @FXML
    private ComboBox<String> adminsalutationcombobox;
    @FXML
    private ComboBox<String> reportType;
    @FXML
    private Label currsalutation;
    @FXML
    private ListView<Account> userlist;
    @FXML
    private ListView<Log> logList;

    private Main mainApp;

    /**
     * Provides a reference back to Main  mostly for switching screens
     * @param mainApp the reference back to Main
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {

        ProfileController profile = new ProfileController(adminlastnametextbox, adminfirstnametextbox,
                adminstreetaddresstextbox, adminstatetextbox, admincountrytextbox, admincitytextbox, adminaptnumtextbox,
                adminzipcodetextbox, adminemailtextbox, adminsalutationcombobox, salutationedit, adminsubmit,
                currsalutation);
        UserListController userList = new UserListController(userlist, deleteuser, banuser, unblockuser);

        LogListController loglist = new LogListController(logList, reportType);

        Logout.setOnAction(event -> mainApp.showLogin());
        refreshAdmin.setOnAction((ActionEvent) -> {
            UserList.mapAllAccounts(DB.loadAllAccounts());
            Authentication.loadMap(DB.loadMap());
            LogList.addNewLogs(DB.loadLogData());
            DB.loadAllReports();
            userList.updateUserList();
            loglist.updateLogList(loglist.getLogType());
        });
    }
}
