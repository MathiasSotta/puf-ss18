package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.game.Game;
import application.game.Movement;
import application.game.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


public class GameViewController implements Initializable {

    @FXML
    private AnchorPane GameBoard;

    @FXML
    private AnchorPane InfoBoard;

    /**
     * Abstract method of the interface Initializable.
     *
     * Gets instances of players from Game with their positions from field.
     * Sets the movement on key events for the players.
     *
     * @param url location - The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb resources - The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game game = Game.getInstance();
        game.Initialize(GameBoard, InfoBoard);
        Player player1 = game.getField().getPlayers().get(0);
        Player player2 = game.getField().getPlayers().get(1);

        GameBoard.setFocusTraversable(true);

        GameBoard.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player2.setMovement(Movement.LEFT);
                    break;
                case RIGHT:
                    player2.setMovement(Movement.RIGHT);
                    break;
                case UP:
                    player2.setMovement(Movement.UP);
                    break;
                case DOWN:
                    player2.setMovement(Movement.DOWN);
                    break;
                case ENTER:
                    player2.dropBomb();
                    break;

                case A:
                    player1.setMovement(Movement.LEFT);
                    break;
                case D:
                    player1.setMovement(Movement.RIGHT);
                    break;
                case W:
                    player1.setMovement(Movement.UP);
                    break;
                case S:
                    player1.setMovement(Movement.DOWN);
                    break;
                case SHIFT:
                    player1.dropBomb();
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
                player2.setMovement(Movement.IDLE);
            }

            if (
                event.getCode() == KeyCode.A ||
                event.getCode() == KeyCode.D ||
                event.getCode() == KeyCode.W ||
                event.getCode() == KeyCode.S
            ) {
                player1.setMovement(Movement.IDLE);
            }
        });

        game.Start();
    }
}
