package application.GameLogic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {

    private Movement movement = Movement.IDLE;

    static final int STEP_WIDTH = 4;
    static final int STEP_HEIGHT = 4;

    public Player(Image image) {

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(64);

        this.setX(0);
        this.setY(0);
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

        if (posX + xFactor + getFitWidth() < Game.getInstance().getFieldPane().getWidth() && posX + xFactor > 0) {
            this.setX(posX + xFactor);
        }

        if (posY + yFactor + getFitHeight() < Game.getInstance().getFieldPane().getHeight() && posY + yFactor > 0) {
            this.setY(posY + yFactor);
        }
    }


    public void setMovement(Movement move) {
        this.movement = move;
    }

    Movement getMovement() {
        return this.movement;
    }
}
