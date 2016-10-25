package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.report.WaterReport;

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

    private AnchorPane rootLayout;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        screen = primaryStage;
        initRootLayout(screen);
    }

    public Stage getScreen() { return screen;}

    private void initRootLayout(Stage mainScreen) {
        try {
            // Load root layout from fxml file.
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/LoginScreenView.fxml"));
            rootLayout = loader.load();

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

    public void showMain(Stage stage) {
        Parent root;
        try {
            // Load main screen.
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/MainScreenView.fxml"));
            root = loader.load();

            // Give the controller access to the main app.
            MainController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Main!!");
            e.printStackTrace();
        }

    }
    public void showRegistration(Stage stage) {
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/Registration.fxml"));
            root = loader.load();
            RegistrationController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showLogin(Stage stage) {
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/LoginScreenView.fxml"));
            root = loader.load();
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
