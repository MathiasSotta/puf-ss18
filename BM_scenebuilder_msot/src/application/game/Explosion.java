package application.game;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Explosion extends GameObject {

    private static final Image IMAGE = new Image("images/explosion.png");
    private static final int COLUMNS  =   9;
    private static final int COUNT    =  74;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 100;
    private static final int HEIGHT   = 100;

    private Animation animation = null;

    /**
     * Sets the animation of the bomb explosion.
     * @param now
     */
    public Explosion(long now) {
        this.setImage(IMAGE);

        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);

        this.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        this.animation = new SpriteAnimation(
                this,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.play();
    }

    public void update(long now, double delta) {
    }
}
