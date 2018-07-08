package application.game;

import application.manager.AssetManager;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.Animation;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game Instance;

    private GameAnimationTimer gameAnimationTimer;
    private AssetManager assetManager = new AssetManager();
    private Field field;

    /**
     * Singleton Pattern for Game Instance
     *
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

        // fill gameBoard with blocks ...
        fillGameBoardWithBlocks();

        // first player upper left
        field.addPlayer(createPlayerObject(new Point2D(0, 0)));
        // second player lower right
        field.addPlayer(createPlayerObject(new Point2D(field.getWidth() - Player.WIDTH, field.getHeight() - Player.HEIGHT)));

        // ======== Display GameMatrix for debugging (comment out next line to hide it) ========
        //setGameMatrixVisible();

    }

    private void setGameMatrixVisible() {
        for (Rectangle2D r : field.getGameMatrix()) {
            Rectangle myrect = new Rectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
            //System.out.println("minX: " + r.getMinX() + "  minY: " + r.getMinY() + "  width: " + r.getWidth() + "  height: " + r.getHeight());
            myrect.setFill(Color.RED);
            myrect.setStroke(Color.DARKRED);
            myrect.setOpacity(.5);
            myrect.setId("gameMatrixBlock");
            field.add(myrect);
        }
        //System.out.println("gameMatrix-tiles: " + (field.getGameMatrix()).size());
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
        Player player = new Player(field, assetManager, initialPos, ViewDirection.DOWN);
        return player;
    }

    private void fillGameBoardWithBlocks() {

        int counter = 0;

        // ToDo: set NonDestructibleBlocks programatically
        // ToDo: create Object for Blocks
        // 0 = empty field
        // 1 = NonDestructableBlock
        // 2 = Destructable Block
        int[][] gameBoardAsIntArri = new int[][]{
                {0, 0, 2, 0, 2, 2, 0, 0, 2, 2, 0},//10
                {0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2},//21
                {2, 2, 0, 2, 2, 2, 2, 0, 0, 2, 2},//32
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 2},//43
                {2, 0, 2, 2, 0, 2, 0, 2, 0, 0, 0},//54
                {2, 1, 0, 1, 0, 1, 2, 1, 2, 1, 0},//65
                {0, 0, 2, 2, 2, 0, 0, 2, 0, 0, 2},//76
                {2, 1, 0, 1, 0, 1, 2, 1, 2, 1, 0},//87
                {0, 0, 2, 2, 0, 2, 0, 0, 2, 2, 2},//98
                {2, 1, 0, 1, 2, 1, 2, 1, 0, 1, 0},//109
                {2, 0, 0, 2, 0, 2, 2, 0, 2, 0, 0},//120

        };
        for (int[] y : gameBoardAsIntArri) {

            for (int x : y) {

                if (x == 1) {
                    Rectangle2D r = field.getGameMatrix().get(counter);
                    Rectangle currRect = new Rectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
                    currRect.setFill(Color.valueOf("d7d7d7"));
                    currRect.setStroke(Color.valueOf("#cccccc"));
                    currRect.setId("UndestructableBlock");
                    field.add(currRect);
                }
                //setting up the destructable blocks on the game field
                if (x == 2) {
                    Rectangle2D r = field.getGameMatrix().get(counter);
                    Rectangle currRect = new Rectangle(r.getMinX() + 5, r.getMinY() + 5, r.getWidth() - 10, r.getHeight() - 10);
                    currRect.setFill(Color.DARKGREY);
                    currRect.setStroke(Color.DARKSLATEGREY);
                    currRect.setId("DestructableBlock");
                    field.add(currRect);
                }
                counter++;
                //System.out.println(counter);
            }

        }

        // update static elements
        field.initStaticElements();
    }
}
