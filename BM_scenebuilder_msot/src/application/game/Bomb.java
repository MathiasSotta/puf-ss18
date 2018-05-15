package application.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bomb extends ImageView {


    public Bomb(Image image) {

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);
    }

    public void update() {
    }
}
