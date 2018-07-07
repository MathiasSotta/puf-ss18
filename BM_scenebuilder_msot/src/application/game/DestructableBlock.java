package application.game;

import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.awt.*;

public class DestructableBlock extends GameObject {

    private Field field;
    private Rectangle r;

    public DestructableBlock(Field field, Rectangle r){
        field = this.field;
        r = this.r;
    }

    @Override
    public void update(long now, double delta) {

    }


}
