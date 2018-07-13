package application.game;

import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.text.Text;

/**
 * Sets the gaming duration to 5 minutes.
 */
public class GameTimerThread extends Thread {

    private Text gameTimer;

    private int time = 300;

    private boolean ended = false;


    /**
     * @param gameTimer
     */
    public GameTimerThread(Text gameTimer) {
        setDaemon(true);
        setName("GameTimer");
        this.gameTimer = gameTimer;
    }

    /**
     * Sets the gaming time.
     */
    @Override
    public void run() {
        while (!ended) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int minutes = time / 60;
                    int second = time % 60;
                    gameTimer.setText(String.valueOf(minutes) + ":" + String.valueOf(second));
                    if (time == 0) {
                        Game.getInstance().End();
                        ended = true;
                    }

                }
            });
            try {
                sleep(TimeUnit.SECONDS.toMillis(1));
                time--;
            } catch (InterruptedException ex) {
                //
            }
        }
    }
}
