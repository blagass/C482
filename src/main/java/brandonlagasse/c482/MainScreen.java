package brandonlagasse.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreen extends Application {
    /**
     * The main class that sets the stage
     * @param stage for starting the app
     * @throws IOException for any IOExceptions
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Calls the start method
     * @param args loads main args
     * Javadoc folder location: C482-FINAL/javadoc.zip
     * FUTURE ENHANCEMENT: I would combine the functionality of both adding parts and products to the same screen. During testing, I found that having to make a prt first, then back out and into the Add Product screen was a lot of steps.
     */
    public static void main(String[] args) {
        launch();
    }
}