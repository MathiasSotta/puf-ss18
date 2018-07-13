package application.controller;

import application.Main;
import application.game.Game;
import application.game.HighScore;
import application.game.HighScoreLoaderThread;
import application.manager.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HighscoresController implements Initializable {

    @FXML
    private Button startPlaying;

    @FXML
    private Button quit;

    @FXML
    private AnchorPane Highscores;

    private TableView table = new TableView();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HighScoreLoaderThread highScoreLoader = new HighScoreLoaderThread(Main.settings.getProperty("highscores_url"), Highscores);
        highScoreLoader.start();

        startPlaying.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewManager.getInstance().setView("/views/StartScreen.fxml");
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(1);
            }
        });
    }
}
