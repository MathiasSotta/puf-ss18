package application.game;

import javafx.animation.AnimationTimer;

public class GameAnimationTimer extends AnimationTimer {

    private Game game = Game.getInstance();

    long prevTime = 0;

    /**
     * Update GameWorld
     * called once per frame
     * @param now
     */
    @Override
    public void handle(long now) {
        double delta = (double)(now - prevTime) / 1000 / 1000 / 1000;
        game.getField().update(now, delta);
        prevTime = now;
    }
}
