package application.game;

import application.manager.AssetManager;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

        // ======== Display GameMatrix for debugging (comment out next line to hide gameMatrix) ========
        setGameMatrixVisible();

        // first player upper left
        field.addPlayer(createPlayerObject(new Point2D(0,0)));
        // second player lower right
        field.addPlayer(createPlayerObject(new Point2D(field.getWidth()-Player.WIDTH, field.getHeight()-Player.HEIGHT)));
    }

    private void setGameMatrixVisible() {
        for (Rectangle2D r : field.getGameMatrix()) {
            Rectangle myrect = new Rectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
            System.out.println("minX: " + r.getMinX() + "  minY: " + r.getMinY() + "  width: " + r.getWidth() + "  height: " + r.getHeight());
            myrect.setFill(Color.RED);
            myrect.setStroke(Color.DARKRED);
            myrect.setOpacity(.5);
            field.add(myrect);
        }
        System.out.println("gameMatrix-tiles: " + (field.getGameMatrix()).size());
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
        Player player = new Player(field, playerImage, assetManager.getImageAsset("bomb"), initialPos, ViewDirection.DOWN);
        return player;
    }
}
