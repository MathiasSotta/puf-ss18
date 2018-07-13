package application.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.*;

public class Bomb extends GameObject {

    private List<Rectangle2D> explosion = new ArrayList<>();

    private List<Explosion> explosions = new ArrayList<>();

    private Player owner;
    private Player victim;

    private int explosionCountdown = 2;
    private int explosionDuration = 1;

    private long droppedAt = 0;
    private long explosionStart = 0;

    private MediaPlayer bombAudio = null;

    private int powerFactor = 1;

    public Bomb(Image image, MediaPlayer bombAudio, Player owner) {
        this.owner = owner;
        this.setId("Bomb");
        this.setImage(image);

        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);

        this.bombAudio = bombAudio;

        if (owner.getActivePowerup() != null) {
            powerFactor = owner.getActivePowerup().getBombPower();
            setImage(owner.getActivePowerup().getBombImage());
        }
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

    public boolean doesHitOtherPlayer(Player player) {
        if (withinExplosionCenter(player)) {
            victim = player;
            if (victim.getId() == owner.getId()) {
                owner.decrementScore();
            } else {
                owner.incrementScore();
            }
            return true;
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
        this.explosion.add(new Rectangle2D(getX()+getFitWidth(), getY(), 60*powerFactor, getFitHeight()));
        // explosion left
        this.explosion.add(new Rectangle2D(getX()-60*powerFactor, getY(), 60*powerFactor, getFitHeight()));
        // explosion up
        this.explosion.add(new Rectangle2D(getX(), getY()-60*powerFactor, getFitWidth(), 60*powerFactor));
        // explosion down
        this.explosion.add(new Rectangle2D(getX(), getY()+getFitHeight(), getFitWidth(), 60*powerFactor));
        // explosion center
        this.explosion.add(new Rectangle2D(getX(), getY(), getFitWidth(), getFitHeight()));


        this.setVisible(false);
        this.explosionStart = now;

        Explosion explosion = new Explosion(now);
        explosion.setX(this.getX());
        explosion.setY(this.getY());
        Game.getInstance().getField().add(explosion);
        this.explosions.add(explosion);

        // top
        for(int i=1; i <= powerFactor; i++) {
            Explosion explosionTop = new Explosion(now);
            explosionTop.setX(this.getX());
            explosionTop.setY(this.getY() - 60*i);
            Game.getInstance().getField().add(explosionTop);
            this.explosions.add(explosionTop);
        }

        // bottom
        for(int i=1; i <= powerFactor; i++) {
            Explosion explosionBottom = new Explosion(now);
            explosionBottom.setX(this.getX());
            explosionBottom.setY(this.getY() + 60*i);
            Game.getInstance().getField().add(explosionBottom);
            this.explosions.add(explosionBottom);
        }

        // right
        for(int i=1; i <= powerFactor; i++) {
            Explosion explosionRight = new Explosion(now);
            explosionRight.setX(this.getX() + 60*i);
            explosionRight.setY(this.getY());
            Game.getInstance().getField().add(explosionRight);
            this.explosions.add(explosionRight);
        }

        // left
        for(int i=1; i <= powerFactor; i++) {
            Explosion explosionLeft = new Explosion(now);
            explosionLeft.setX(this.getX()-60*i);
            explosionLeft.setY(this.getY());
            Game.getInstance().getField().add(explosionLeft);
            this.explosions.add(explosionLeft);
        }

        bombAudio.play();
    }

    public boolean isExploding() {
        return this.explosion.size() > 0;
    }

    public List<Explosion> getExplosions() {
        return this.explosions;
    }

    public Player getOwner() {
        return owner;
    }

    public void setPowerFactor(int powerFactor) {
        this.powerFactor = powerFactor;
    }
}
