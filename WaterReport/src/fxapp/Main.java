package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public Stage getScreen() { return screen;}

    private void initRootLayout(Stage mainScreen) {
    /*  Used in a desperate attempt at a shitty persistence handler.
        PersistenceHandler.loadUsers(new File("D:\\GitHub\\Victoryexe\\WaterReport\\src" +
                        "\\fxapp\\persistance\\Users.txt"),
                new File("D:\\GitHub\\Victoryexe\\WaterReport\\src\\fxapp\\persistance\\Passwords.txt"));
          PersistenceHandler.loadQualityReports(new File("../persistance/QualityReports.txt"));
          PersistenceHandler.loadWaterReports(new File("../persistance/WaterReports.txt"));
    */
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

    public void showHistReport() {
        try {
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/historicalReport.fxml"));
            SplitPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Historical Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(screen);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            HistoricalReportController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
