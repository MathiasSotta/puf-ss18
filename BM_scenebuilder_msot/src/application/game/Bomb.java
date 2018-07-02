package application.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends GameObject {

    private List<Rectangle2D> explosion = new ArrayList<>();

    private int explosionCountdown = 2;
    private int explosionDuration = 2;

    private long droppedAt = 0;
    private long explosionStart = 0;

    public Bomb(Image image) {
        this.setImage(image);

        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);
    }

    public void update(long now) {
        if (!isExploding()) {
            if (droppedAt == 0) {
                droppedAt = now;
            }
            if (timestampToSeconds(now - droppedAt) >= explosionCountdown) {
                explode(now);
            }
        }
    }

    public boolean exploded(long now) {
        return timestampToSeconds(now - explosionStart) >= explosionDuration;
    }

    public boolean withinExplosion(GameObject gameObject) {
        for (Rectangle2D rect : this.explosion) {
            if (rect.contains(gameObject.getCenterPosition())) {
                return true;
            }
        }

        return false;
    }

    public void explode(long now) {
        // explosion right
        this.explosion.add(new Rectangle2D(getX()+getFitWidth(), getY(), 250, getFitHeight()));
        // explosion left
        this.explosion.add(new Rectangle2D(getX()-250, getY(), 250, getFitHeight()));
        // explosion up
        this.explosion.add(new Rectangle2D(getX(), getY()-250, getFitWidth(), 250));
        // explosion down
        this.explosion.add(new Rectangle2D(getX(), getY()+getFitHeight(), getFitWidth(), 250));

        this.explosionStart = now;
    }

    public boolean isExploding() {
        return this.explosion.size() > 0;
    }

    public List<Rectangle2D> getExplosions() {

        return this.explosion;
    }
}
