package application;

import application.controller.StartScreenController;
import application.manager.ViewManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.media.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.Properties;

public class Main extends Application {

    public static final Properties settings = new Properties();
    private static Stage mainStage;
    private ViewManager viewManager = ViewManager.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        viewManager.setView("/views/StartScreen.fxml");
        settings.load(getClass().getResourceAsStream("/config/application.config"));

        backgroundMusic();

        Group root = new Group();
        root.getChildren().addAll(viewManager);

        mainStage = primaryStage;
        mainStage.setTitle(settings.getProperty("application.title"));
        mainStage.setScene(new Scene(root, 770, 900));
        mainStage.show();
    }

    /** Playing background music in a loop all game long.
     * Background music taken as free download from:
     * http://freemusicarchive.org/genre/Chiptune/?sort=track_date_published&d=1&page=7
     */
    public void backgroundMusic(){
        String music = "resources/sounds/sawsquarenoise_-_01_-_Interstellar.mp3";
        Media sound = new Media(new File(music).toURI().toString());
        MediaPlayer mediaPlayer= new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
