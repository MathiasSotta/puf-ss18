package application.game;

import javafx.scene.image.Image;

abstract class Powerup extends GameObject {
    @Override
    public void update(long now, double delta) {
    }

    public abstract int getBombPower();

    public abstract Image getBombImage();

    public abstract int getDurationSeconds();
}
