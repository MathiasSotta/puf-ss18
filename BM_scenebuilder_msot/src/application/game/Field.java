package application.game;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private AnchorPane fieldPane;

    private List<Node> staticElements = new ArrayList<>();

    public Field(AnchorPane fieldPane) {
        this.fieldPane = fieldPane;

        for (Node node : this.fieldPane.getChildren()) {
            if (node.getId().equals("UndestructableBlock")) {
                staticElements.add(node);
            }
        }
    }

    public double getWidth() {
        return fieldPane.getWidth();
    }

    public double getHeight() {
        return fieldPane.getHeight();
    }

    public boolean isCollidingWithStaticElement(Node node) {
        for (Node staticElement : staticElements) {
            if (staticElement.getBoundsInParent().intersects(node.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public void add(Node node) {
        fieldPane.getChildren().add(node);
    }
}
