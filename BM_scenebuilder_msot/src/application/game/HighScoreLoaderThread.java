package application.game;

import application.Main;
import application.manager.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
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

    public HighScoreLoaderThread(String highscoreUrl) {
        setDaemon(true);
        setName("HighScoreLoader");
        this.highscoreUrl = highscoreUrl;
    }

    @Override
    public void run() {
        try {
            JSONObject jsonFull = readJsonFromUrl(highscoreUrl);
            json = (JSONObject)jsonFull.get("input");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    HighScoreList highscores = HighScoreList.fromJson(json);
                    Game.getInstance().setHighscores(highscores);
                    ViewManager.getInstance().setView("/views/HighscoreScreen.fxml");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

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
