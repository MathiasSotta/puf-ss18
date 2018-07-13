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
