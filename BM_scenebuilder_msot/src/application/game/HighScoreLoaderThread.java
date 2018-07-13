package application.game;

import application.Main;
import application.manager.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

public class HighScoreLoaderThread extends Thread {

    private JSONObject json;

    private String highscoreUrl;

    private AnchorPane highscorePane;

    /**
     * Places the player names and scores into a table
     * @param highscoreUrl
     * @param highscorePane
     */
    public HighScoreLoaderThread(String highscoreUrl, AnchorPane highscorePane) {
        setDaemon(true);
        setName("HighScoreLoader");
        this.highscoreUrl = highscoreUrl;
        this.highscorePane = highscorePane;
    }

    /**
     * Player data is set to a JSON and put into a table
     */
    @Override
    public void run() {
        try {
            JSONObject jsonFull = readJsonFromUrl(highscoreUrl);
            json = (JSONObject)jsonFull.get("input");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TableView table = new TableView();
                    HighScoreList highscores = HighScoreList.fromJson(json);

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
                    highscoreList.addAll(highscores.getScores());

                    table.setItems(highscoreList);
                    table.setLayoutX(100);
                    table.setMinWidth(500);

                    highscorePane.getChildren().add(table);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reader
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Reading the data from the url
     * @param url
     * @return json object
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject jsonObject = new JSONObject(jsonText);
            return jsonObject;
        } finally {
            is.close();
        }
    }
}
