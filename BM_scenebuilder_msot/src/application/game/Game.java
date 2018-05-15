package application.game;

import application.manager.AssetManager;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Game {

    private static Game Instance;

    private GameAnimationTimer gameAnimationTimer;
    private AssetManager assetManager = new AssetManager();
    private AnchorPane fieldPane;
    private Player localPlayer;

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
        this.fieldPane = pane;
        assetManager.loadAssets();

        localPlayer = createPlayerObject();
        fieldPane.getChildren().add(localPlayer);
    }

    public void Start() {
        gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public AnchorPane getFieldPane() {
        return fieldPane;
    }

    // todo: move to factory pattern
    private Player createPlayerObject() {
        Image playerImage = assetManager.getImageAsset("player");
        Player player = new Player(playerImage, assetManager.getImageAsset("bomb"));
        return player;
    }
}
