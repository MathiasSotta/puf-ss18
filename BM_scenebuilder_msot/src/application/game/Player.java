package application.game;

import application.Main;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

/**
 * Player with all properties.
 */
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

    private Image player;
    private Image playerRight;
    private Image playerUp;
    private Image playerDown;
    private Image playerLeft;

    private int score = 0;

    private Powerup activePowerup;

    private String name = "";

    /**
     * Gives the player it's position, name and looking direction.
     * @param field
     * @param pos
     * @param playerLooks Defines the direction in which the player is looking
     * @param name
     */
    public Player(Field field, Point2D pos, ViewDirection playerLooks, String name) {
        this.field = field;
        this.playerLooks = playerLooks;
        this.setId(name);
        initialPos = pos;
    }

    /**
     * Checks if the player is alive in each frame.
     * @param now
     * @param delta
     */
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

        field.isPickingUpPowerup(this);
        if (activePowerup != null) {
            if (activePowerup.getId().equals("Powerbomb")) {
                PowerupTimerThread powerupTimer = new PowerupTimerThread(this);
                powerupTimer.start();
            }
        }

        // check if new player position collides with static objects in scene
        // and reset position if needed
        if (field.isCollidingWithStaticElement(this)) {
            setX(posX);
            setY(posY);
        }
    }

    /**
     * Handles the dropped bomb.
     * Deciding in which field the bomb is dropped.
     */
    public void dropBomb() {
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

    /**
     *
     * @param move
     */
    public void setMovement(Movement move) {
        movement = move;
    }

    /**
     *
     * @return
     */
    Movement getMovement() {
        return movement;
    }

    /**
     *
     * @param amount
     * @return
     */
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

    public void setPlayer(Image player) {
        this.player = player;
        setImage(player);

        setPreserveRatio(true);
        setCache(true);

        setFitWidth(Player.WIDTH);
        setFitHeight(Player.HEIGHT);

        setX(initialPos.getX());
        setY(initialPos.getY());
    }

    public void setPlayerRight(Image playerRight) {
        this.playerRight = playerRight;
    }

    public void setPlayerUp(Image playerUp) {
        this.playerUp = playerUp;
    }

    public void setPlayerDown(Image playerDown) {
        this.playerDown = playerDown;
    }

    public void setPlayerLeft(Image playerLeft) {
        this.playerLeft = playerLeft;
    }

    public void setBombImage(Image bombImage) {
        this.bombImage = bombImage;
    }

    public void setBombAudio(MediaPlayer bombAudio) {
        this.bombAudio = bombAudio;
    }

    public Powerup getActivePowerup() {
        return activePowerup;
    }

    public void setActivePowerup(Powerup activePowerup) {
        this.activePowerup = activePowerup;
    }
}
