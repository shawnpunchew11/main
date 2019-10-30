package dream.fcard;

import java.io.IOException;

import dream.fcard.core.Main;
import dream.fcard.logic.stats.Stats;
import dream.fcard.logic.storage.StatsStorageManager;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.State;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            StorageManager.provideRoot("./");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Windows/MainWindow.fxml"));
            VBox vbox = fxmlLoader.load();
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setTitle("FlashCard Pro");
            stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/address_book_32.png")));
            stage.show();
            // when the 'X' button is clicked.
            stage.setOnCloseRequest(e -> {
                Stats.endCurrentSession();
                StorageManager.saveAll(State.getState().getDecks());
                StatsStorageManager.saveStats();
            });
            // start a session
            Stats.startCurrentSession();
        } catch (IOException e) {
            // TODO: Our yet-to-be-reinstated-logger will replace this rudimentary error printing
            System.err.println("Failed to load app");
            e.printStackTrace();
        }
    }
}
