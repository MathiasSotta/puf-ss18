package application.game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Player extends GameObject {

    private Movement movement = Movement.IDLE;

    private Image bombImage;

    private Field field;

    private int health = 100;
    
    private Point2D initialPos;

    private boolean isAlive = true;
    private long diedAt = 0;

    static final int STEP_WIDTH = 2;
    static final int STEP_HEIGHT = 2;

    static final int WIDTH = 60;
    static final int HEIGHT = 60;

    static final int respawnCountdown = 2;

    public Player(Field field, Image playerImage, Image bomb, Point2D pos) {
        this.field = field;
        setImage(playerImage);
        setPreserveRatio(true);
        setCache(true);

        setFitWidth(Player.WIDTH);
        setFitHeight(Player.HEIGHT);
        
        initialPos = pos;

        setX(initialPos.getX());
        setY(initialPos.getY());

        bombImage = bomb;
    }

    public void update(long now) {
        // check health each frame
        if (isAlive() && health <= 0) {
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

        int posX = (int) getX();
        int posY = (int) getY();
        int xFactor = 0;
        int yFactor = 0;

        if (movement == Movement.RIGHT) {
            xFactor = STEP_WIDTH;
        } else if (movement == Movement.LEFT) {
            xFactor = STEP_WIDTH * -1;
        } else if (movement == Movement.UP) {
            yFactor = STEP_HEIGHT * -1;
        } else if (movement == Movement.DOWN) {
            yFactor = STEP_HEIGHT * 1;
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
            Bomb bomb = new Bomb(bombImage);
            bomb.setX(getX());
            bomb.setY(getY());
            field.addBomb(bomb);
        }
    }

    public void setMovement(Movement move) {
        movement = move;
    }

    Movement getMovement() {
        return movement;
    }

    public void damage(int amount) {
        health -= amount;
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
}
