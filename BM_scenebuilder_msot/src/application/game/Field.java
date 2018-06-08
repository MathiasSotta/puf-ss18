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
        return fieldPane.getMaxWidth();
    }

    public double getHeight() {
        return fieldPane.getMaxHeight();
    }

    public boolean isCollidingWithStaticElement(Node node) {
        for (Node staticElement : staticElements) {
            if (staticElement.getBoundsInParent().intersects(node.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public void update(long now) {
        Bomb removeBomb = null;
        for (Bomb b : this.bombs) {
            b.update(now);
            if (b.isExploding()) {
                for (Player p : this.players) {
                    // if player is not alive we can skip the check
                    if (!p.isAlive()) {
                        break;
                    }
                    if (b.withinExplosion(p)) {
                        p.damage(100);

                        // is player still alive after damage ?
                        if (!p.isAlive()) {
                            // player is within explosion
                            System.out.println("Player died");
                            p.triggerRespawn(now);
                        }
                    }
                }

                // remove exploded bomb
                // todo: remove multiple in one frame
                if (b.exploded(now)) {
                    fieldPane.getChildren().remove(b);
                    removeBomb = b;
                }
            }
        }

        // cleanup Objects
        this.bombs.remove(removeBomb);

        for (Player p : this.players) {
            p.update(now);
        }
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

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
