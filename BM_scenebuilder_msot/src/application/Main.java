package application;

import application.controller.StartScreenController;
import application.manager.ViewManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

        Group root = new Group();
        root.getChildren().addAll(viewManager);

        mainStage = primaryStage;
        mainStage.setTitle(settings.getProperty("application.title"));
        mainStage.setScene(new Scene(root, 770, 900));
        mainStage.show();
    }

    public void backgroundSound(){
        String musicFile = "resources/sounds/background.mid";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
