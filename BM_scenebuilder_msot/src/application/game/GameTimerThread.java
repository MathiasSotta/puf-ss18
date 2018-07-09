package application.game;

import java.util.concurrent.TimeUnit;

import application.manager.ViewManager;
import javafx.application.Platform;
import javafx.scene.text.Text;


public class GameTimerThread extends Thread {

    private Text gameTimer;

    private int time = 300;

    public GameTimerThread(Text gameTimer) {
        setDaemon(true);
        setName("GameTimer");
        this.gameTimer = gameTimer;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int minutes = time / 60;
                    int second = time % 60;
                    gameTimer.setText(String.valueOf(minutes) + ":" + String.valueOf(second));
                    if (minutes == 0 && second == 0) {
                        ViewManager.getInstance().setView("/views/StartScreen.fxml");
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
