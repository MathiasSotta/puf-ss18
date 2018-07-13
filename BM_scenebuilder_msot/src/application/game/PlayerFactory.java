package application.game;

import application.manager.AssetManager;
import javafx.geometry.Point2D;

public class PlayerFactory {
    /**
     * Sets the images for the players looking directions.
     * @param field
     * @param initialPos
     * @param name
     * @return
     */
    public static Player getPlayer(Field field, Point2D initialPos, String name)
    {
        AssetManager assetManager = AssetManager.getInstance();
        Player player = new Player(field, initialPos, ViewDirection.DOWN, name);

        player.setPlayer(assetManager.getImage("player"));
        player.setPlayerRight(assetManager.getImage("looksRight"));
        player.setPlayerLeft(assetManager.getImage("looksLeft"));
        player.setPlayerUp(assetManager.getImage("looksUp"));
        player.setPlayerDown(assetManager.getImage("looksDown"));
        player.setBombImage(assetManager.getImage("bomb"));
        player.setBombAudio(assetManager.getAudio("explosion"));

        return player;
    }
}
