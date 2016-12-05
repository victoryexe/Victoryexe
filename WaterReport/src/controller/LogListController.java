package controller;

import javafx.scene.control.ListView;
import model.log.Log;
import model.log.LogList;

import java.util.List;

/**
 * Created by grizz on 12/5/2016.
 */
class LogListController {
    private ListView<Log> logList;

    public LogListController(ListView<Log> logList) {
        this.logList = logList;
        updateLogList();
    }

    public void updateLogList() {
        List<Log> logs = LogList.getAllLogs();
        logList.setItems(javafx.collections.FXCollections.observableList(logs));
    }
}
