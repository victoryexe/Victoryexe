package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import model.Users.User;
import model.report.*;

import java.util.ArrayList;

/**
 * Created by grizz on 10/16/2016.
 */
public class AddReportController {

    private TextField latitude;
    private TextField longitude;
    private ComboBox sourceBox ;
    private ComboBox conditionBox;
    private Button submitRepBox;

    public AddReportController (TextField latitude, TextField longitude,
                                ComboBox sourceBox, ComboBox conditionBox, Button submitRepBox) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sourceBox = sourceBox;
        this.conditionBox = conditionBox;
        this.submitRepBox = submitRepBox;
        ArrayList<WaterType> source = new ArrayList<WaterType>();
        for (WaterType type : WaterType.values()) {
            source.add(type);
        }
        ArrayList<WaterCondition> condition = new ArrayList<WaterCondition>();
        for (WaterCondition con : WaterCondition.values()) {
            condition.add(con);
        }
        MainController.populateComboBox(sourceBox, source);
        MainController.populateComboBox(conditionBox, condition);
        MainController.restrictToNums(latitude);
        MainController.restrictToNums(longitude);

        submitRepBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (latitude.getText().equals("") || longitude.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "All fields must be filled in.", ButtonType.CLOSE);
                    alert.show();
                } else {
                    Report report = new WaterReport((User) LoginScreenController.currUser,
                            new Location(latitude.getText(), longitude.getText()),
                            (WaterType) sourceBox.getValue(), (WaterCondition) conditionBox.getValue());
                    ReportsList.makeReport(report.getReportID(), report);
                    latitude.setText("");
                    longitude.setText("");
                    sourceBox.setValue(source.get(0));
                    conditionBox.setValue(condition.get(0));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Report #W" + report.getReportID() + " has been submitted.\n" +
                                    "Thank you for your contribution.", ButtonType.CLOSE);
                    alert.show();
                    ReportListController.updateList();
                }
            }
        });
    }
}
