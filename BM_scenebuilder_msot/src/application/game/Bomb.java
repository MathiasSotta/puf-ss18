package application.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.*;
import java.io.File;

public class Bomb extends GameObject {

    private List<Rectangle2D> explosion = new ArrayList<>();

    private List<Explosion> explosions = new ArrayList<>();

    private int explosionCountdown = 2;
    private int explosionDuration = 1;

    private long droppedAt = 0;
    private long explosionStart = 0;

    private MediaPlayer bombAudio = null;


    public Bomb(Image image, MediaPlayer bombAudio) {
        this.setId("Bomb");
        this.setImage(image);

        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);

        this.bombAudio = bombAudio;

    }

    public void update(long now, double delta) {
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

    public boolean withinExplosionCenter(GameObject gameObject) {
        for (Rectangle2D rect : this.explosion) {
            if (rect.contains(gameObject.getCenterPosition())) {
                return true;
            }
        }

        return false;
    }

    public boolean withinExplosionRect(Rectangle2D targetRect) {
        for (Rectangle2D rect : this.explosion) {
            if (rect.intersects(targetRect)) {
                return true;
            }
        }

        return false;
    }

    public void explode(long now) {
        // explosion right
        this.explosion.add(new Rectangle2D(getX()+getFitWidth(), getY(), 60, getFitHeight()));
        // explosion left
        this.explosion.add(new Rectangle2D(getX()-60, getY(), 60, getFitHeight()));
        // explosion up
        this.explosion.add(new Rectangle2D(getX(), getY()-60, getFitWidth(), 60));
        // explosion down
        this.explosion.add(new Rectangle2D(getX(), getY()+getFitHeight(), getFitWidth(), 60));

        this.setVisible(false);
        this.explosionStart = now;

        Explosion explosion = new Explosion(now);
        explosion.setX(this.getX());
        explosion.setY(this.getY());
        Game.getInstance().getField().add(explosion);
        this.explosions.add(explosion);

        // top
        Explosion explosionTop = new Explosion(now);
        explosionTop.setX(this.getX());
        explosionTop.setY(this.getY()-60);
        Game.getInstance().getField().add(explosionTop);
        this.explosions.add(explosionTop);

        // bottom
        Explosion explosionBottom = new Explosion(now);
        explosionBottom.setX(this.getX());
        explosionBottom.setY(this.getY()+60);
        Game.getInstance().getField().add(explosionBottom);
        this.explosions.add(explosionBottom);

        // right
        Explosion explosionRight = new Explosion(now);
        explosionRight.setX(this.getX()+60);
        explosionRight.setY(this.getY());
        Game.getInstance().getField().add(explosionRight);
        this.explosions.add(explosionRight);

        // left
        Explosion explosionLeft = new Explosion(now);
        explosionLeft.setX(this.getX()-60);
        explosionLeft.setY(this.getY());
        Game.getInstance().getField().add(explosionLeft);
        this.explosions.add(explosionLeft);

        bombAudio.play();
    }

    public boolean isExploding() {
        return this.explosion.size() > 0;
    }

    public List<Explosion> getExplosions() {
        return this.explosions;
    }
}
