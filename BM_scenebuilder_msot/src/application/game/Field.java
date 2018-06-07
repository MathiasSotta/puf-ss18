package application.game;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private AnchorPane fieldPane;

    private List<Node> staticElements = new ArrayList<>();

    private List<Bomb> bombs = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

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

    public void update() {
        Bomb removeBomb = null;
        for (Bomb b : this.bombs) {
            if (b.isExploding()) {
                for (Player p : this.players) {
                    if (b.withinExplosion(p)) {
                        // player is within explosion
                        System.out.println("Player is within explosion");
                    }
                }
                if (b.exploded()) {
                    fieldPane.getChildren().remove(b);
                    removeBomb = b;
                }
            }
        }
        this.bombs.remove(removeBomb);
    }

    public void add(Node node) {
        fieldPane.getChildren().add(node);
    }

    public void addPlayer(Player player) {
        this.add(player);
        this.players.add(player);
    }

    public void addBomb(Bomb bomb) {
        this.add(bomb);
        this.bombs.add(bomb);
    }
}
