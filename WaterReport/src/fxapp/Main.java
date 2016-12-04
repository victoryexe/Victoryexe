package fxapp;


import controller.AdminController;
import controller.LoginScreenController;
import controller.MainController;
import controller.RegistrationController;
import db.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.log.LogList;
import model.registration.Authentication;
import model.registration.UserList;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class.
 */

public class Main extends Application {
    /** the java logger for this class */
    private static final Logger LOGGER =Logger.getLogger("Main");

    /** main container for application */
    private Stage screen;

    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        screen = primaryStage;
        initRootLayout(screen);
    }

    public Stage getScreen() {
        return screen;
    }

    private void initRootLayout(Stage mainScreen) {
        UserList.mapAllAccounts(DB.loadAllAccounts());
        Authentication.loadMap(DB.loadMap());
        LogList.addNewLogs(DB.loadLogData());
        DB.loadAllReports();
        try {
            // Load root layout from fxml file.
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/LoginScreenView.fxml"));
            AnchorPane rootLayout = loader.load();

            //Setting controller
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);

            //Setting title
            mainScreen.setTitle("Clean Slate");

            //shows the scene with rootLayout
            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();
        } catch (IOException e) {
            //error on load, must be logged
            LOGGER.log(Level.SEVERE, "Failed to find the FXML file!");
            e.printStackTrace();
        }
    }

    /**
     * Switches from whatever screen the program is currently on to the Main screen
     */

    public void showMain() {
        try {
            // Load main screen.
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/MainScreenView.fxml"));
            AnchorPane root = loader.load();

            // Give the controller access to the main app.
            MainController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root);
            screen.setScene(scene);
            screen.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Main!!");
            e.printStackTrace();
        }

    }

    /**
     * Switches from whatever screen the program is currently on to the Registration screen
     */
    public void showRegistration() {
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Registration.fxml"));
            root = loader.load();
            RegistrationController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(root);
            screen.setScene(scene);
            screen.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches from whatever screen the program is on to the Admin version of the Main screen
     */
    public void showAdmin() {
        try {
            // Load Admin screen.
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/AdminView.fxml"));
            AnchorPane root = loader.load();

            // Give the controller access to the main app.
            AdminController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root);
            screen.setScene(scene);
            screen.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Admin!!");
            e.printStackTrace();
        }
    }

    /**
     * Switches from whatever screen the program is currently on to the Login screen
     */
    public void showLogin() {
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/LoginScreenView.fxml"));
            root = loader.load();
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(root);
            screen.setScene(scene);
            screen.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the program
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }

/* Used in a desperate attempt at a shitty persistence handler.
   @Override
    public void stop() {
        PersistenceHandler.saveUsers(UserList.getUserList());
        Authentication.savePass();
        Platform.exit();
   }
*/
}
