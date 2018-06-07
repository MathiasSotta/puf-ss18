package application.game;

import application.manager.AssetManager;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game Instance;

    private GameAnimationTimer gameAnimationTimer;
    private AssetManager assetManager = new AssetManager();
    private Player localPlayer;
    private Field field;

    public List<GameObject> gameObjectList = new ArrayList<>();

    /**
     * Singleton Pattern for Game Instance
     * @return Game Instance
     */
    public static Game getInstance() {
        if (Instance == null) {
            Instance = new Game();
        }
        return Instance;
    }

    public void Initialize(AnchorPane pane) {
        this.field = new Field(pane);
        assetManager.loadAssets();


        localPlayer = createPlayerObject();
        field.add(localPlayer);
        gameObjectList.add(localPlayer);
    }

    public void Start() {
        gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    // todo: move to factory pattern
    private Player createPlayerObject() {
        Image playerImage = assetManager.getImageAsset("player");
        Player player = new Player(field, playerImage, assetManager.getImageAsset("bomb"));
        return player;
    }
}
