package application.game;

import javafx.animation.AnimationTimer;

public class GameAnimationTimer extends AnimationTimer {

    private static GameAnimationTimer Instance;

    private Game game = Game.getInstance();

    long prevTime = 0;

    public static GameAnimationTimer getInstance() {
        if (Instance == null) {
            Instance = new GameAnimationTimer();
        }
        return Instance;
    }

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
