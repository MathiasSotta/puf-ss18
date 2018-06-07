package application.game;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

abstract class GameObject extends ImageView {
    public abstract void update();

    public Point2D getCenterPosition() {
        return new Point2D(getX()+(getFitWidth()/2), getY()+(getFitHeight()/2));
    }
}