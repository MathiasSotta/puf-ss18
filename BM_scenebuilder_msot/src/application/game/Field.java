package application.game;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private AnchorPane fieldPane;

    private List<Node> staticElements = new ArrayList<>();
    private List<DestructibleBlock> destructibleBlocks = new ArrayList<>();

    private List<Node> cleanup = new ArrayList<>();

    private List<Bomb> bombs = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

    private List<Rectangle2D> gameMatrix = new ArrayList<>();


    public Field(AnchorPane fieldPane) {
        this.fieldPane = fieldPane;

        // enable gameMatrix
        initGameMatrix();
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

    public void update(long now, double delta) {
        for (Bomb b : this.bombs) {
            b.update(now, delta);
            if (b.isExploding()) {
                for (Player p : this.players) {

                    // if player is not alive we can skip the check
                    if (!p.isAlive()) {
                        break;
                    }

                    // player within bomb explosion
                    if (b.doesHitOtherPlayer(p)) {
                        if (p.damage(100)) {
                            Game.getInstance().updatePlayerScore(b);
                        }
                    }

                    // destructible block within bomb explosion
                    for (DestructibleBlock d : destructibleBlocks) {
                        if (b.withinExplosionCenter(d)) {
                            fieldPane.getChildren().remove(d);
                            cleanup.add(d);
                        }
                    }
                }

                // remove exploded bomb
                if (b.exploded(now)) {
                    fieldPane.getChildren().remove(b);
                    cleanup.add(b);

                    for (Explosion e : b.getExplosions()) {
                        fieldPane.getChildren().remove(e);
                    }
                }
            }
        }

        // cleanup Objects
        for (Node n : cleanup) {
            if (n.getId().equals("DestructibleBlock")) {
                staticElements.remove(n);
                destructibleBlocks.remove(n);
            }
            if (n.getId().equals("Bomb")) {
                bombs.remove(n);
            }
        }

        // update player state
        if (!Game.getInstance().hasEnded()) {
            for (Player p : this.players) {
                p.update(now, delta);
            }
        }
    }

    public void add(Node node) {
        if (node.getId() != null) {
            if (node.getId().equals("DestructibleBlock")) {
                staticElements.add(node);
                destructibleBlocks.add((DestructibleBlock) node);
            }
            if (node.getId().equals("IndestructibleBlock")) {
                staticElements.add(node);
            }
        }
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

    private void initGameMatrix() {
        double matrixTileHeight = this.fieldPane.getMaxHeight() / 11;
        double matrixTileWidth = this.fieldPane.getMaxWidth() / 11;

        for (int i = 0; i < this.fieldPane.getMaxWidth(); i += matrixTileWidth) {
            for (int j = 0; j < this.fieldPane.getMaxHeight(); j += matrixTileHeight) {
                Rectangle2D r = new Rectangle2D(j, i, matrixTileWidth, matrixTileHeight);
                gameMatrix.add(r);
            }
        }
    }

    public List<Rectangle2D> getGameMatrix() {
        return gameMatrix;
    }

    public Point2D getBombTileCenterPosition(GameObject node) {
        double x;
        double y;

        List<Rectangle> intersectingTiles = new ArrayList<>();
        for (Rectangle2D r : gameMatrix) {
            Rectangle currentMatrixTile = new Rectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());

            // check if rectangle of game matrix intersects with player bounds and harvest these in new array
            if (currentMatrixTile.getBoundsInParent().intersects(node.getBoundsInParent())) {
                intersectingTiles.add(currentMatrixTile);
            }
        }
        // if player bounds are completely in one tile
        if (intersectingTiles.size() == 1) {
            Rectangle r = intersectingTiles.get(0);
            x = r.getX() + (r.getWidth() / 2);
            y = r.getY() + (r.getHeight() / 2);
            return new Point2D(x, y);
        }
        // if player bounds are in more than one tile
        else {
            for (int i = 0; i < intersectingTiles.size(); i++) {
                Point2D playerCenterPosition = new Point2D(node.getX() + (node.getFitWidth() / 2), node.getY() + (node.getFitHeight() / 2));
                Rectangle currentTile = intersectingTiles.get(i);

                // the center position of the player should only exist in one tile at a time ...
                if (currentTile.contains(playerCenterPosition)) {
                    return new Point2D(currentTile.getX() + (currentTile.getWidth() / 2), currentTile.getY() + (currentTile.getHeight() / 2));
                }
            }
            // a mystery
            return null;
        }
    }

}
