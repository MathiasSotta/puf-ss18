package application.game;

import application.manager.AssetManager;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

public class Player extends GameObject {

    private Movement movement = Movement.IDLE;

    private Image bombImage;

    private MediaPlayer bombAudio;

    private Field field;

    private int health = 100;
    
    private Point2D initialPos;

    private boolean isAlive = true;
    private long diedAt = 0;

    static final int STEP_WIDTH = 150;
    static final int STEP_HEIGHT = 150;

    static final int WIDTH = 60;
    static final int HEIGHT = 60;

    static final int respawnCountdown = 2;

    private ViewDirection playerLooks;

    private Image playerRight = null;
    private Image playerUp = null;
    private Image playerDown = null;
    private Image playerLeft = null;

    private int score = 0;

    private String name = "";

    public Player(Field field, AssetManager assetManager, Point2D pos, ViewDirection playerLooks, String name) {
        this.field = field;
        this.playerLooks = playerLooks;
        this.setId(name);

        setImage(assetManager.getImage("player"));
        playerRight = assetManager.getImage("looksRight");
        playerLeft = assetManager.getImage("looksLeft");
        playerUp = assetManager.getImage("looksUp");
        playerDown = assetManager.getImage("looksDown");
        bombImage = assetManager.getImage("bomb");
        bombAudio = assetManager.getAudio("explosion");

        setPreserveRatio(true);
        setCache(true);

        setFitWidth(Player.WIDTH);
        setFitHeight(Player.HEIGHT);
        
        initialPos = pos;

        setX(initialPos.getX());
        setY(initialPos.getY());
    }

    public void update(long now, double delta) {

        // check health each frame
        if (isAlive() && health <= 0) {
            isAlive = false;
            handleDeath(now);
        }

        // handle dead state
        if (!isAlive()) {
            if (timestampToSeconds(now - diedAt) >= respawnCountdown) {
                handleRespawn();
            } else {
                return;
            }
        }

        double posX = getX();
        double posY = getY();
        double xFactor = 0;
        double yFactor = 0;

        if (movement == Movement.RIGHT) {
            xFactor = STEP_WIDTH * delta;
            this.playerLooks = ViewDirection.RIGHT;
            this.setImage(playerRight);


        } else if (movement == Movement.LEFT) {
            xFactor = STEP_WIDTH * delta * -1;
            this.playerLooks = ViewDirection.LEFT;
            this.setImage(playerLeft);

        } else if (movement == Movement.UP) {
            yFactor = STEP_HEIGHT * delta * -1;
            this.playerLooks = ViewDirection.UP;
            this.setImage(playerUp);

        } else if (movement == Movement.DOWN) {
            yFactor = STEP_HEIGHT * delta * 1;
            this.playerLooks = ViewDirection.DOWN;
            this.setImage(playerDown);
        }

        if (posX + xFactor + getFitWidth() < field.getWidth() && posX + xFactor > 0) {
            setX(posX + xFactor);
        }

        if (posY + yFactor + getFitHeight() < field.getHeight() && posY + yFactor > 0) {
            setY(posY + yFactor);
        }

        // check if new player position collides with static objects in scene
        // and reset position if needed
        if (field.isCollidingWithStaticElement(this)) {
            setX(posX);
            setY(posY);
        }
    }

    public void dropBomb() {
        // todo: add time until next bomb can be dropped
        // start timer and update with delta in update()
        // only allow dropping another bomb after 2 seconds and reset timer
        if (isAlive()) {
            bombAudio.stop();
            Bomb bomb = new Bomb(bombImage, bombAudio, this);

            // set bomb position to center of the players current tile
            Point2D bombPosition = field.getBombTileCenterPosition(this);
            if (bombPosition != null) {

                bomb.setX(bombPosition.getX()-(bomb.getFitWidth()/2));
                bomb.setY(bombPosition.getY()-(bomb.getFitHeight()/2));
            }
            field.addBomb(bomb);
        }
    }

    public void setMovement(Movement move) {
        movement = move;
    }

    Movement getMovement() {
        return movement;
    }

    public boolean damage(int amount) {
        health -= amount;
        if (health <= 0) {
            return true;
        }
        return false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void handleDeath(long now) {
        health = 0;
        isAlive = false; // :(
        setVisible(false);
        diedAt = now;
    }

    public void handleRespawn() {
        health = 100;
        diedAt = 0;
        isAlive = true;
        setX(initialPos.getX());
        setY(initialPos.getY());
        setVisible(true);
    }

    public void incrementScore() {
        score++;
    }

    public void decrementScore() {
        score--;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
