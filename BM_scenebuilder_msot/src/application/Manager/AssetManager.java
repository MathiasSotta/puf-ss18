package application.manager;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;

public class AssetManager {

    private HashMap images = new HashMap<>();
    private HashMap audio = new HashMap<>();

    public void loadAssets() {
        images.put("player", new Image("images/test.png"));
        images.put("bomb", new Image("images/bomb.png"));
        images.put("looksRight", new Image("images/looksRight.png"));
        images.put("looksLeft", new Image("images/looksLeft.png"));
        images.put("looksUp", new Image("images/looksUp.png"));
        images.put("looksDown", new Image("images/looksDown.png"));
        images.put("destructible", new Image("images/destructible.png"));
        images.put("indestructible", new Image("images/indestructible.png"));

        audio.put("explosion", new MediaPlayer(new Media(
                new File("resources/sounds/bomb_explosion_medium.aif").toURI().toString()))
        );
    }

    public Image getImage(String key) {
        return (Image) images.get(key);
    }

    public MediaPlayer getAudio(String key) {
        return (MediaPlayer) audio.get(key);
    }
}
