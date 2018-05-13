package application.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.GameLogic.Game;
import application.GameLogic.Movement;
import application.GameLogic.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


public class GameViewController implements Initializable {

    @FXML
    private AnchorPane GameBoard;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game game = Game.getInstance();
        game.Initialize(GameBoard);
        Player localPlayer = game.getLocalPlayer();

        GameBoard.setFocusTraversable(true);
        GameBoard.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    localPlayer.setMovement(Movement.LEFT);
                    break;
                case RIGHT:
                    localPlayer.setMovement(Movement.RIGHT);
                    break;
                case UP:
                    localPlayer.setMovement(Movement.UP);
                    break;
                case DOWN:
                    localPlayer.setMovement(Movement.DOWN);
                    break;
            }
        });

        GameBoard.setOnKeyReleased(event -> {
            if (
                event.getCode() == KeyCode.LEFT ||
                event.getCode() == KeyCode.RIGHT ||
                event.getCode() == KeyCode.UP ||
                event.getCode() == KeyCode.DOWN
            ) {
                localPlayer.setMovement(Movement.IDLE);
            }
        });

        game.Start();
    }

}
