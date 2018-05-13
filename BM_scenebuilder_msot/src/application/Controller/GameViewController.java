package application.Controller;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javax.sound.midi.SysexMessage;

/**
 * GameViewController-Klasse
 */
public class GameViewController implements Initializable {

    @FXML
    private AnchorPane GameBoard;

    /**
     * Inizialisiert die Controller-Klasse.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameBoard.setFocusTraversable(true);
        GameBoard.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    System.out.println("LINKS");
                    break;
                case RIGHT:
                    System.out.println("RECHTS");
                    break;
                case UP:
                    System.out.println("OBEN");
                    break;
                case DOWN:
                    System.out.println("UNTEN");
                    break;

            }
        });

        GameBoard.setOnKeyReleased(event -> {

        });
    }

}
