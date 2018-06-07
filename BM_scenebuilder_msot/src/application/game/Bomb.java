package application.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends GameObject {

    private List<Rectangle2D> explosion = new ArrayList<>();

    private int explosionTime = 2;
    private long explosionStart = 0;

    public Bomb(Image image) {
        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);
    }

    public void update() {
    }

    public boolean exploded() {
        return ((System.currentTimeMillis() / 1000L) - this.explosionStart) >= explosionTime;
    }

    public boolean withinExplosion(GameObject gameObject) {
        for (Rectangle2D rect : this.explosion) {
            if (rect.contains(gameObject.getCenterPosition())) {
                return true;
            }
        }

        return false;
    }

    public void explode() {
        // explosion right
        this.explosion.add(new Rectangle2D(getX()+getFitWidth(), getY(), 250, getFitHeight()));
        // explosion left
        this.explosion.add(new Rectangle2D(getX()-250, getY(), 250, getFitHeight()));
        // explosion up
        this.explosion.add(new Rectangle2D(getX(), getY()-250, getFitWidth(), 250));
        // explosion down
        this.explosion.add(new Rectangle2D(getX(), getY()+getFitHeight(), getFitWidth(), 250));

        this.explosionStart = System.currentTimeMillis() / 1000L;
    }

    public boolean isExploding() {
        return this.explosion.size() > 0;
    }

    public List<Rectangle2D> getExplosions() {
        return this.explosion;
    }
}
