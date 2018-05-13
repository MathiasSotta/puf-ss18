package application;

import application.Manager.ViewManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Properties;

public class Main extends Application {

    private static final Properties settings = new Properties();
    private static Stage mainStage;
    private static ViewManager viewManager = new ViewManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        viewManager.setView("/views/GameView.fxml");
        settings.load(getClass().getResourceAsStream("/config/application.config"));

        Group root = new Group();
        root.getChildren().addAll(viewManager);

        mainStage = primaryStage;
        mainStage.setTitle(settings.getProperty("application.title"));
        mainStage.setScene(new Scene(root, 770, 870));
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
