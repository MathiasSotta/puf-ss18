package application.game;

import javafx.scene.image.Image;

public class PowerBomb extends Powerup {
    /**
     * Bomb for powering up.
     * @param image
     */
    public PowerBomb(Image image) {
        this.setId("Powerbomb");
        this.setImage(image);

        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);
    }

    public int getBombPower() {
        return 2;
    }

    public int getDurationSeconds() {
        return 10;
    }

    public Image getBombImage() {
        return getImage();
    }
}
