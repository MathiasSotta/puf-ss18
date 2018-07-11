package application.game;

import application.Main;
import javafx.application.Platform;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HighScorePosterThread extends Thread {

    private JSONObject json;

    private String highscoreUrl;

    private HighScore highscore;

    public HighScorePosterThread(String highscoreUrl, HighScore highscore) {
        setDaemon(true);
        setName("HighScorePoster");
        this.highscoreUrl = highscoreUrl;
        this.highscore = highscore;
    }

    @Override
    public void run() {
        JSONObject highscoresJson = new JSONObject(highscore);
        String highscoresString = highscoresJson.toString();

        try {
            URL url = new URL(highscoreUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",  "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(highscoresString.length()));

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(highscoresString);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            writer.close();
            reader.close();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    HighScoreLoaderThread highScoreLoader = new HighScoreLoaderThread(Main.settings.getProperty("highscores_url"));
                    highScoreLoader.start();
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
