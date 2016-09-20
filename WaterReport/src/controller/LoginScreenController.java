package controller;


import fxapp.Main;
import javafx.fxml.FXML;

public class LoginScreenController {

    /** a link back to Main */
    private Main mainApp;

    /**
     * allows for calling back to the main code if necessary
     * @param main  the reference to the FX Application instance
     */
    public void setMainApp(Main main) { mainApp = main; }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
}
