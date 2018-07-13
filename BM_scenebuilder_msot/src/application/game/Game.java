package application.game;

import application.Main;
import application.Manager.GameboardManager;
import application.manager.AssetManager;
import application.manager.ViewManager;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The game logic.
 */
public class Game {

    private static Game Instance;

    private static String PLAYER_ONE_NAME = "PLAYER_ONE";
    private static String PLAYER_TWO_NAME = "PLAYER_TWO";

    private GameAnimationTimer gameAnimationTimer;
    private AssetManager assetManager = AssetManager.getInstance();
    private GameboardManager gameboardManager = new GameboardManager();
    private Field field;

    private AnchorPane infoBoard;

    private HighScore highscore;

    private Text gameTimer;
    private Text playerOneName;
    private Text playerTwoName;
    private Text playerOneScore;
    private Text playerTwoScore;

    private boolean ended = false;

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

    /**
     *
     * @param pane the AnchorPane that is holding the field
     * @param infoBoard the AnchorPane that provides information on a players scores
     */
    public void Initialize(AnchorPane pane, AnchorPane infoBoard) {
        this.field = new Field(pane);
        this.infoBoard = infoBoard;

        // fill gameBoard with blocks ...
        gameboardManager.fillGameBoardWithBlocks(field, assetManager);

        // first player upper left
        field.addPlayer(createPlayerObject(new Point2D(0, 0), Game.PLAYER_ONE_NAME));
        // second player lower right
        field.addPlayer(createPlayerObject(new Point2D(field.getWidth() - Player.WIDTH, field.getHeight() - Player.HEIGHT), Game.PLAYER_TWO_NAME));

        // GameMatrix debugging
        //setGameMatrixVisible();
    }

    /**
     * Provides the information on player's name and score
     * @param playerOne
     * @param playerTwo
     */
    public void initializeInfoboard(String playerOne, String playerTwo) {
        Player player1 = getField().getPlayers().get(0);
        Player player2 = getField().getPlayers().get(1);

        player1.setName(playerOne);
        player2.setName(playerTwo);

        playerOneName = (Text)this.infoBoard.lookup("#PlayerOneName");
        playerOneScore = (Text)this.infoBoard.lookup("#PlayerOneScore");
        playerTwoName = (Text)this.infoBoard.lookup("#PlayerTwoName");
        playerTwoScore = (Text)this.infoBoard.lookup("#PlayerTwoScore");

        gameTimer = (Text)this.infoBoard.lookup("#GameTimer");

        playerOneName.setText(player1.getName());
        playerTwoName.setText(player2.getName());

        // highscore
        highscore = new HighScore();
        highscore.setPlayerOne(player1.getName());
        highscore.setPlayerTwo(player2.getName());

        GameTimerThread thread = new GameTimerThread(gameTimer);
        thread.start();
    }

    /**
     * Updates a player's score.
     * @param bomb
     */
    public void updatePlayerScore(Bomb bomb) {
        if (bomb.getOwner().getId() == Game.PLAYER_ONE_NAME) {
            highscore.setPlayerOneScore(bomb.getOwner().getScore());
            playerOneScore.setText(String.valueOf(bomb.getOwner().getScore()));
        }
        if (bomb.getOwner().getId() == Game.PLAYER_TWO_NAME) {
            highscore.setPlayerTwoScore(bomb.getOwner().getScore());
            playerTwoScore.setText(String.valueOf(bomb.getOwner().getScore()));
        }
    }

    /**
     * Fills the rectangles in the game matrix with color and set an Id.
     */
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

    /**
     * Starts the timer for an animation.
     */
    public void Start() {
        gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();
    }

    /**
     * Starts the thread for getting the highscores.
     */
    public void End() {
        ended = true;
        HighScorePosterThread highscorePoster = new HighScorePosterThread(Main.settings.getProperty("highscores_url"), highscore);
        highscorePoster.start();
    }

    /**
     *
     * @return AnchorPane
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Created a player object.
     * @param initialPos Position where the player appears on the AnchorPane.
     * @param name Player's name
     * @return player
     */
    private Player createPlayerObject(Point2D initialPos, String name) {
        Player player = PlayerFactory.getPlayer(field, initialPos, name);
        return player;
    }


    public boolean hasEnded() {
        return ended;
    }
}
