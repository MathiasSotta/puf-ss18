package application.manager;

import javafx.scene.image.Image;

import java.util.HashMap;

public class AssetManager {

    private HashMap assets = new HashMap<>();

    public void loadAssets() {
        assets.put("player", new Image("images/test.png"));
        assets.put("bomb", new Image("images/bomb.png"));
    }

    public Image getImageAsset(String key) {
        return (Image)assets.get(key);
    }
}
