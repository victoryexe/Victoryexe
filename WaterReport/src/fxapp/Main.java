package fxapp;

import controller.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

    private AnchorPane rootLayout;
    @Override
    public void start(Stage primaryStage) throws Exception{
        screen = primaryStage;
        initRootLayout(screen);
    }

    public Stage getScreen() { return screen;}

    private void initRootLayout(Stage mainScreen) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/LoginScreenView.fxml"));
            rootLayout = loader.load();

            //Setting controller
            LoginScreenController controller = loader.getController();

            //Setting title
            mainScreen.setTitle("Aqua Pura");

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

    public static void main(String[] args) {
        launch(args);
    }


}
