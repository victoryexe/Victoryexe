package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import fxapp.Main;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import model.Users.Account;
import model.report.OverallCondition;
import model.report.WaterCondition;
import model.report.WaterType;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;

/**
 * The controller for the root/main window
 *
 */
public class MainController {

    @FXML
    private Button Logout;
    @FXML
    private TextField lastnametextbox;
    @FXML
    private  TextField firstnametextbox;
    @FXML
    private TextField streetaddresstextbox;
    @FXML
    private TextField citytextbox;
    @FXML
    private TextField statetextbox;
    @FXML
    private TextField countrytextbox;
    @FXML
    private TextField aptnumtextbox;
    @FXML
    private TextField zipcodetextbox;
    @FXML
    private TextField emailtextbox;
    @FXML
    private ComboBox salutationcombobox;
    @FXML
    private Button salutationedit;
    @FXML
    private Button submit;
    @FXML
    private Label currsalutation;
    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;
    @FXML
    private ComboBox<WaterType> sourceBox;
    @FXML
    private Button submitRepBox;
    @FXML
    private ComboBox<WaterCondition> conditionBox;
    @FXML
    private ListView reportlist;
    @FXML
    private Button viewreport;
    @FXML
    private TextField othertype;
    @FXML
    private GoogleMapView GmapsViewPane;
    @FXML
    private TabPane applicationTabs;
    @FXML
    private Tab submitTab;
    @FXML
    private Tab Purity;
    @FXML
    private TextField purityLat;
    @FXML
    private TextField purityLon;
    @FXML
    private TextField VirusPPM;
    @FXML
    private TextField ContamPPM;
    @FXML
    private ComboBox<OverallCondition> purityCond;
    @FXML
    private Button SubmitPurity;
    @FXML
    private Button avetoqual;
    @FXML
    private Button qualtoave;
    @FXML
    private Button hisreportTransition;
    @FXML
    private TextField searchLat;
    @FXML
    private TextField searchLon;
    @FXML
    private Button searchBut;

    /** reference back to mainApplication if needed */
    private Main mainApp;

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     * */
    public void setMainApp(Main main) {
        mainApp = main;
    }

    @FXML
    private void initialize() {
        Account currUser = LoginScreenController.getCurrUser();
        Logout.setOnAction(event -> mainApp.showLogin());

        // Delegates control of the profile view to ProfileController
        ProfileController profile = new ProfileController(lastnametextbox, firstnametextbox, streetaddresstextbox,
                statetextbox, countrytextbox, citytextbox, aptnumtextbox, zipcodetextbox,
                emailtextbox, salutationcombobox, salutationedit, submit, currsalutation);

        // Delegates control of report submission to AddReportController
        AddReportController addReport = new AddReportController(latitude,
                longitude, sourceBox, conditionBox, submitRepBox, othertype, avetoqual,
                applicationTabs, Purity);

        // Delegates control of QualityReport submission to AddQualityController
        AddQualityController qualReport = new AddQualityController(purityLat,
                purityLon, VirusPPM, ContamPPM, purityCond, SubmitPurity, qualtoave,
                applicationTabs, submitTab);

        // Delegates control of the View Report screen to ReportListController
        ReportListController reportList = new ReportListController(reportlist, viewreport, hisreportTransition);
        reportList.setMainApp(mainApp);

        // Delegates control of the Google Map to MapController
        MapComponentInitializedListener map = new MapController(GmapsViewPane, searchLat, searchLon, searchBut);
        GmapsViewPane.addMapInializedListener(map);
        ObservableList<Tab> tabs = applicationTabs.getTabs();
        tabs.remove(4);
        switch(currUser.getAuthLevel()) {
            case USER:
                tabs.remove(3);
                break;
            case WORKER:
                tabs.remove(3);
                break;
            case MANAGER:
                break;
            case ADMIN:
                for(int i = 0; i < 3; i++) {
                    tabs.remove(1);
                }
                break;
        }
    //    applicationTabs.set = tabs;
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
    /**
     * Forces only decimal numbers to be entered as values in corresponding
     * TextField
     */

    protected static void restrictToNums(TextField field) {
        DecimalFormat format = new DecimalFormat( "#" );
        field.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( (object == null) || ((parsePosition.getIndex()) < (c.getControlNewText().length())))
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

    }

    /**
     * Takes a ComboBox and an ArrayList containing its elements then populates the ComboBox and sets
     * the default value to the first entry in the list
     * @param box the ComboBox being populated
     * @param list the ArrayList being used to populate box
     */
    protected static void populateComboBox(ComboBox box, List list) {
        box.setItems(javafx.collections.FXCollections.observableList(list));
        box.setValue(list.get(0));
    }
}
