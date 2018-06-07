package application.game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Bomb extends GameObject {


    public Bomb(Image image) {

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(60);
        this.setFitHeight(60);


    }

    public void update() {
        // do explosion timer here and trigger explosion

    }

    public void explode() {
        Point2D pos = new Point2D(getX(), getY());
        Point2D dir = new Point2D(1,0);
        Point2D rightPoint = getRayPosition(pos, dir, 150);

//        setX(rightPoint.getX());
//        setY(rightPoint.getY());
    }

    public static Point2D getRayPosition(Point2D position, Point2D direction, float distance){
        Point2D ray = new Point2D(position.getX(), position.getY());
        ray = ray.add(direction.multiply(distance));

        return ray;
    }
}
