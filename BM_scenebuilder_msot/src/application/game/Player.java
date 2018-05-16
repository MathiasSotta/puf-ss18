package application.game;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Player extends ImageView {

    private Movement movement = Movement.IDLE;

    private Image bombImage;

    private Field field;

    static final int STEP_WIDTH = 2;
    static final int STEP_HEIGHT = 2;

    public Player(Field field, Image playerImage, Image bombImage) {
        this.field = field;
        this.setImage(playerImage);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);

        this.setX(0);
        this.setY(0);

        this.bombImage = bombImage;
    }

    public void update() {

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
            this.setX(posX + xFactor);
        }

        if (posY + yFactor + getFitHeight() < field.getHeight() && posY + yFactor > 0) {
            this.setY(posY + yFactor);
        }

        // check if new player position collides with static objects in scene
        // and reset position if needed
        if (field.isCollidingWithStaticElement(this)) {
            this.setX(posX);
            this.setY(posY);
        }
    }

    public void dropBomb() {
        // todo: add time until next bomb can be dropped
        // start timer and update with delta in update()
        // only allow dropping another bomb after 2 seconds and reset timer
        Bomb bomb = new Bomb(this.bombImage);
        bomb.setX(this.getX());
        bomb.setY(this.getY());
        field.add(bomb);
    }


    public void setMovement(Movement move) {
        this.movement = move;
    }

    Movement getMovement() {
        return this.movement;
    }
}
