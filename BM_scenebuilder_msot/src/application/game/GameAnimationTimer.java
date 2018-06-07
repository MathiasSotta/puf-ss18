package application.game;

import javafx.animation.AnimationTimer;

public class GameAnimationTimer extends AnimationTimer {

    private Game game = Game.getInstance();

    /**
     * Update GameWorld
     * called once per frame
     * @param now
     */
    @Override
    public void handle(long now) {
        for (GameObject go : game.gameObjectList) {
            go.update();
        }

        game.getField().update();
    }
}
