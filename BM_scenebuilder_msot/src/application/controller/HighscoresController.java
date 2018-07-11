package application.controller;

import application.game.Game;
import application.game.HighScore;
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
        TableColumn playerOneCol = new TableColumn("Name");
        TableColumn playerOneScoreCol = new TableColumn("Score");
        TableColumn playerTwoCol = new TableColumn("Name");
        TableColumn playerTwoScoreCol = new TableColumn("Score");
        TableColumn dateColumn = new TableColumn("Date");

        playerOneCol.setCellValueFactory(
                new PropertyValueFactory<HighScore,String>("playerOne")
        );
        playerOneScoreCol.setCellValueFactory(
                new PropertyValueFactory<HighScore,String>("playerOneScore")
        );
        playerTwoCol.setCellValueFactory(
                new PropertyValueFactory<HighScore,String>("playerTwo")
        );
        playerTwoScoreCol.setCellValueFactory(
                new PropertyValueFactory<HighScore,String>("playerTwoScore")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<HighScore,String>("date")
        );

        table.getColumns().addAll(playerOneCol, playerOneScoreCol, playerTwoCol, playerTwoScoreCol, dateColumn);
        ObservableList<HighScore> highscoreList = FXCollections.observableArrayList();
        highscoreList.addAll(Game.getInstance().getHighscores().getScores());

        table.setItems(highscoreList);
        table.setLayoutX(100);
        table.setMinWidth(500);

        Highscores.getChildren().add(table);

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
