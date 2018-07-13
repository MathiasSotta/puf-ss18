package application.game;

import javafx.application.Platform;
import java.util.concurrent.TimeUnit;

/**
 * Sets the time that the player has to use the power up.
 */

public class PowerupTimerThread extends Thread {

    private Player player;
    private int time = 0;
    private boolean ended = false;

    public PowerupTimerThread(Player player) {
        setDaemon(true);
        setName("PowerupTimer");
        this.player = player;
        this.time = player.getActivePowerup().getDurationSeconds();
    }

    @Override
    public void run() {
        while (!ended) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (time == 0) {
                        player.setActivePowerup(null);
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
