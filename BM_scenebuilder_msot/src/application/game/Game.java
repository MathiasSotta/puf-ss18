package application.game;

import application.Main;
import application.manager.AssetManager;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Game {

    private static Game Instance;

    private static String PLAYER_ONE_NAME = "PLAYER_ONE";
    private static String PLAYER_TWO_NAME = "PLAYER_TWO";

    private GameAnimationTimer gameAnimationTimer;
    private AssetManager assetManager = new AssetManager();
    private Field field;

    private AnchorPane infoBoard;

    private HighScoreList highscores;

    private Text gameTimer;
    private Text playerOneName;
    private Text playerTwoName;
    private Text playerOneScore;
    private Text playerTwoScore;

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

    public void Initialize(AnchorPane pane, AnchorPane infoBoard) {
        this.field = new Field(pane);
        this.infoBoard = infoBoard;
        assetManager.loadAssets();

        // fill gameBoard with blocks ...
        fillGameBoardWithBlocks();

        // first player upper left
        field.addPlayer(createPlayerObject(new Point2D(0, 0), Game.PLAYER_ONE_NAME));
        // second player lower right
        field.addPlayer(createPlayerObject(new Point2D(field.getWidth() - Player.WIDTH, field.getHeight() - Player.HEIGHT), Game.PLAYER_TWO_NAME));

        initializeInfoboard();

        // GameMatrix debugging
        //setGameMatrixVisible();
    }

    public void initializeInfoboard() {
        playerOneName = (Text)this.infoBoard.lookup("#PlayerOneName");
        playerOneScore = (Text)this.infoBoard.lookup("#PlayerOneScore");
        playerTwoName = (Text)this.infoBoard.lookup("#PlayerTwoName");
        playerTwoScore = (Text)this.infoBoard.lookup("#PlayerTwoScore");

        gameTimer = (Text)this.infoBoard.lookup("#GameTimer");

        GameTimerThread thread = new GameTimerThread(gameTimer);
        thread.start();

        HighScoreLoaderThread highScoreLoader = new HighScoreLoaderThread(Main.settings.getProperty("highscores_url"));
        highScoreLoader.start();
    }

    public void updatePlayerScore(Bomb bomb) {
        if (bomb.getOwner().getId() == Game.PLAYER_ONE_NAME) {
            playerOneScore.setText(String.valueOf(bomb.getOwner().getScore()));
        }
        if (bomb.getOwner().getId() == Game.PLAYER_TWO_NAME) {
            playerTwoScore.setText(String.valueOf(bomb.getOwner().getScore()));
        }
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
    private Player createPlayerObject(Point2D initialPos, String name) {
        Player player = new Player(field, assetManager, initialPos, ViewDirection.DOWN, name);
        return player;
    }

    private void fillGameBoardWithBlocks() {

        int counter = 0;

        // ToDo: set NonDestructibleBlocks programatically
        // ToDo: create Object for Blocks
        // 0 = empty field
        // 1 = IndestructibleBlock Block
        // 2 = Destructible Block
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
                    IndestructibleBlock i = new IndestructibleBlock();
                    i.setId("IndestructibleBlock");
                    i.setImage(assetManager.getImage("indestructible"));
                    i.setFitWidth(r.getWidth() - 10);
                    i.setFitHeight(r.getHeight() - 10);
                    i.setX(r.getMinX() + 5);
                    i.setY(r.getMinY() + 5);
                    field.add(i);
                }
                //setting up the destructible blocks on the game field
                if (x == 2) {
                    Rectangle2D r = field.getGameMatrix().get(counter);
                    DestructibleBlock d = new DestructibleBlock();
                    d.setId("DestructibleBlock");
                    d.setImage(assetManager.getImage("destructible"));
                    d.setFitWidth(r.getWidth() - 10);
                    d.setFitHeight(r.getHeight() - 10);
                    d.setX(r.getMinX() + 5);
                    d.setY(r.getMinY() + 5);
                    field.add(d);
                }
                counter++;
                //System.out.println(counter);
            }
        }
    }

    public void setHighscores(HighScoreList highscores) {
        this.highscores = highscores;
    }
}
