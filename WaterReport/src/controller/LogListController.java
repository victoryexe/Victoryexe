package controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.log.Log;
import model.log.LogList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by grizz on 12/5/2016.
 */
class LogListController {
    private final ListView<Log> logList;
    private final ComboBox<String> reportType;

    public LogListController(ListView<Log> logList, ComboBox<String> reportType) {
        this.reportType = reportType;
        this.logList = logList;
        List<String> logTypes = new ArrayList<>();
        logTypes.add("All Logs");
        logTypes.add("Login Attempt Logs");
        logTypes.add("Deleted Report Logs");
        logTypes.add("Deleted Account Logs");
        logTypes.add("Banned Account Logs");
        logTypes.add("Unblocked Account Logs");
        MainController.populateComboBox(reportType, logTypes);
        reportType.valueProperty().addListener((ov, t, t1) -> updateLogList(t1));
        updateLogList(logTypes.get(0));
    }

    public void updateLogList(String s) {
        List<Log> logs;
        switch (s) {
            case "All Logs" :
                logs = LogList.getAllLogs();
                break;
            case "Login Attempt Logs" :
                logs = new LinkedList<>();
                logs.addAll(LogList.getLoginAttemptLog());
                break;
            case "Deleted Report Logs" :
                logs = new LinkedList<>();
                logs.addAll(LogList.getDeletedReportLog());
                break;
            case "Deleted Account Logs" :
                logs = new LinkedList<>();
                logs.addAll(LogList.getDeletedAccountLog());
                break;
            case "Banned Account Logs" :
                logs = new LinkedList<>();
                logs.addAll(LogList.getBannedAccountLog());
                break;
            case "Unblocked Account Logs" :
                logs = new LinkedList<>();
                logs.addAll(LogList.getUnblockAccountLog());
                break;
            default :
                logs = LogList.getAllLogs();
                break;

        }
        logList.setItems(javafx.collections.FXCollections.observableList(logs));
    }

    public String getLogType() {
        return reportType.getSelectionModel().getSelectedItem();
    }
}
