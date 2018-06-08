package application.game;

import application.manager.AssetManager;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Game {

    private static Game Instance;

    private GameAnimationTimer gameAnimationTimer;
    private AssetManager assetManager = new AssetManager();
    private Field field;

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

        // first player upper left
        field.addPlayer(createPlayerObject(new Point2D(0,0)));
        // second player lower right
        field.addPlayer(createPlayerObject(new Point2D(field.getWidth()-Player.WIDTH, field.getHeight()-Player.HEIGHT)));
    }

    public void Start() {
        gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();
    }

    public Field getField() {
        return this.field;
    }

    // todo: move to factory pattern
    private Player createPlayerObject(Point2D initialPos) {
        Image playerImage = assetManager.getImageAsset("player");
        Player player = new Player(field, playerImage, assetManager.getImageAsset("bomb"), initialPos);
        return player;
    }
}
