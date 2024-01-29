package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class Test is responsible for running the application.
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Test extends Application {

    /**
     * The method start charge the main window.
     *
     * @param stage stage to show.
     */
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/PrincipalWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("ENTRADA");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
