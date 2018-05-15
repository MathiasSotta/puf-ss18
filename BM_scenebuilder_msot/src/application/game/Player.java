package application.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {

    private Movement movement = Movement.IDLE;

    private Image bombImage;

    static final int STEP_WIDTH = 2;
    static final int STEP_HEIGHT = 2;

    public Player(Image playerImage, Image bombImage) {

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


        //System.out.println(posY + yFactor + getFitHeight());

        if (posX + xFactor + getFitWidth() < Game.getInstance().getFieldPane().getWidth() && posX + xFactor > 0) {
            this.setX(posX + xFactor);
        }

        if (posY + yFactor + getFitHeight() < Game.getInstance().getFieldPane().getHeight() && posY + yFactor > 0) {
            this.setY(posY + yFactor);
        }
    }

    public void dropBomb() {
        Bomb bomb = new Bomb(this.bombImage);
        bomb.setX(this.getX());
        bomb.setY(this.getY());
        Game.getInstance().getFieldPane().getChildren().add(bomb);
    }


    public void setMovement(Movement move) {
        this.movement = move;
    }

    Movement getMovement() {
        return this.movement;
    }
}
