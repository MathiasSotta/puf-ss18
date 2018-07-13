package application.controller;

import application.Main;
import application.game.Game;
import application.manager.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.*;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {
    @FXML
    private Button startPlaying;

    @FXML
    private Button highScores;

    @FXML
    private AnchorPane StartScreen;

    /**
     * Abstract method of the interface Initializable.
     *
     * Sets the event for pressing the "Start Playing" button for changing the view from start screen to start playing the game in game view.
     * Sets the event for pressing the "Highscores" button for showing the highscore.
     * Gets the player names.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startPlaying.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextField playerOne = (TextField)StartScreen.lookup("#PlayerOneName");
                TextField playerTwo = (TextField)StartScreen.lookup("#PlayerTwoName");

                ViewManager.getInstance().setView("/views/GameView.fxml");
                Game.getInstance().initializeInfoboard(playerOne.getText(), playerTwo.getText());
            }
        });

        highScores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewManager.getInstance().setView("/views/HighscoreScreen.fxml");
            }
        });
    }
}
