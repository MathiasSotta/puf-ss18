package application.game;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class HighScoreList {

    private ArrayList<HighScore> scores = new ArrayList<>();

    public static HighScoreList fromJson(JSONObject json) {
        HighScoreList highscores = new HighScoreList();
        Iterator<?> keys = json.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            if (json.get(key) instanceof JSONObject) {
                JSONObject match = (JSONObject)json.get(key);
                HighScore score = new HighScore();
                score.setPlayerOne((String)match.get("PlayerOne"));
                score.setPlayerTwo((String)match.get("PlayerTwo"));
                score.setPlayerOneScore(Integer.parseInt((String)match.get("PlayerOneScore")));
                score.setPlayerTwoScore(Integer.parseInt((String)match.get("PlayerTwoScore")));
                score.setDate((String)match.get("date"));

                highscores.scores.add(score);
            }
        }
        return highscores;
    }

    public ArrayList<HighScore> getScores() {
        return scores;
    }
}
