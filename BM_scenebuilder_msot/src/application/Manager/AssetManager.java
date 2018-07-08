package application.manager;

import javafx.scene.image.Image;

import java.util.HashMap;

public class AssetManager {

    private HashMap assets = new HashMap<>();

    public void loadAssets() {
        assets.put("player", new Image("images/test.png"));
        assets.put("bomb", new Image("images/bomb.png"));
        assets.put("looksRight", new Image("images/looksRight.png"));
        assets.put("looksLeft", new Image("images/looksLeft.png"));
        assets.put("looksUp", new Image("images/looksUp.png"));
        assets.put("looksDown", new Image("images/looksDown.png"));
        assets.put("destructible", new Image("images/destructible.png"));
    }

    public Image getImageAsset(String key) {
        return (Image)assets.get(key);
    }
}
