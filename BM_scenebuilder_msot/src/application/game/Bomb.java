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

    /**
     * Defines the bomb's properties.
     * @param image  sets the bomb's image
     * @param bombAudio sets the bomb's sound
     * @param owner sets the player who dropped the bomb
     */
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

    /**
     * Sets the time it takes til the bomb explodes.
     * @param now
     * @param delta
     */
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

    /**
     * Sets the time til the next bomb can be dropped.
     * @param now
     * @return timestamp passed time from now must be longer than explosion duration
     */
    public boolean exploded(long now) {
        return timestampToSeconds(now - explosionStart) >= explosionDuration;
    }

    /**
     * Defines which game object is inside the explosion's center by comparing rectangles inside the explosion Array List.
     * @param gameObject any object like a player, a block or the bomb itself
     * @return true- object is inside the explosion's center, false - object isn't inside the explosion's center
     */
    public boolean withinExplosionCenter(GameObject gameObject) {
        for (Rectangle2D rect : this.explosion) {
            if (rect.contains(gameObject.getCenterPosition())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Bomb hitting the opponent player increments the score.
     * @param player is declarated as victim, hit by the bomb of the opponent player or is the owner of the dropped bomb comitting suicide
     * @return true- anybody got hit, false- no player got hit
     */
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

    /**
     * Checks which rectangle is inside the explosion radius.
     * @param targetRect the rectangle which contains the dropped bomb
     * @return true- contains the dropped bomb, false- isn't hit by explosion
     */
    public boolean withinExplosionRect(Rectangle2D targetRect) {
        for (Rectangle2D rect : this.explosion) {
            if (rect.intersects(targetRect)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Handles everything changed by an explosion like destroying blocks or powering up a player and playing bomb sound.
     * @param now
     */
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

    /**
     * Checks if the Array List explosion is empty.
     * @return true- exploding, false- not exploding
     */
    public boolean isExploding() {
        return this.explosion.size() > 0;
    }

    /**
     *
     * @return the Array List with explosions
     */
    public List<Explosion> getExplosions() {
        return this.explosions;
    }

    /**
     * @return who dropped this bomb
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *
     * @param powerFactor getting scores from hitting the opponent player
     */
    public void setPowerFactor(int powerFactor) {
        this.powerFactor = powerFactor;
    }
}
