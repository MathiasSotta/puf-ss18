package application.game;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/**
 * Everything on the game board is a game object.
 */
abstract class GameObject extends ImageView {
    public abstract void update(long now, double delta);

    public Point2D getCenterPosition() {
        return new Point2D(getX()+(getFitWidth()/2), getY()+(getFitHeight()/2));
    }

    public int timestampToSeconds(long now) {
         return (int)(now / 1000 / 1000 / 1000);
    }
}
