package application.game;

import application.manager.AssetManager;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Links the view elements to the logic.
 */
public class Field {

    private AnchorPane fieldPane;

    private List<Node> staticElements = new ArrayList<>();
    private List<DestructibleBlock> destructibleBlocks = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();

    private List<Node> cleanup = new ArrayList<>();

    private List<Bomb> bombs = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

    private List<Rectangle2D> gameMatrix = new ArrayList<>();

    /**
     * @param fieldPane defines the AnchorPane that carries the game.
     */
    public Field(AnchorPane fieldPane) {
        this.fieldPane = fieldPane;

        // enable gameMatrix
        initGameMatrix();
    }

    /**
     *
     * @return width of the AnchorPane
     */
    public double getWidth() {
        return fieldPane.getMaxWidth();
    }
    /**
     *
     * @return height of the AnchorPane
     */
    public double getHeight() {
        return fieldPane.getMaxHeight();
    }

    /**
     * Avoids the crossing of the player onto this node element.
     * @param node elements like blocks
     * @return
     */
    public boolean isCollidingWithStaticElement(Node node) {
        for (Node staticElement : staticElements) {
            if (staticElement.getBoundsInParent().intersects(node.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Player gets skills from collectiong a power up.
     * @param player The player who picks up.
     */
    public void isPickingUpPowerup(Player player) {
        if (player.getActivePowerup() == null) {
            for (Powerup powerup : powerups) {
                if (powerup.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    player.setActivePowerup(powerup);
                    fieldPane.getChildren().remove(powerup);
                    cleanup.add(powerup);
                }
            }
        }
    }

    /**
     * Handling the visual changes after a bomb explosion, like removing exploded bombs, dead players or destroyed blocks
     * @param now
     * @param delta
     */
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
                            Random r = new Random();
                            float chance = r.nextFloat();

                            if (chance <= 0.10f) {
                                PowerBomb powerup = new PowerBomb(AssetManager.getInstance().getImage("bomb_powerup"));
                                powerup.setX(d.getX());
                                powerup.setY(d.getY());
                                fieldPane.getChildren().add(powerup);
                                powerups.add(powerup);
                            }
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

            if (n.getId().equals("Powerbomb")) {
                powerups.remove(n);
            }
        }

        // update player state
        if (!Game.getInstance().hasEnded()) {
            for (Player p : this.players) {
                p.update(now, delta);
            }
        }
    }

    /**
     * Adds the blocks as nodes onto the field.
     * @param node
     */
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

    /**
     * Adds a player onto the field.
     * @param player
     */
    public void addPlayer(Player player) {
        this.add(player);
        this.players.add(player);
    }

    /**
     * Adds a bomb onto the field.
     * @param bomb
     */
    public void addBomb(Bomb bomb) {
        this.add(bomb);
        this.bombs.add(bomb);
    }

    /**
     *
     * @return Array List of bombs.
     */
    public List<Bomb> getBombs() {
        return bombs;
    }

    /**
     *
     * @return Array List of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Initialises the game matrix with height and width.
     * Makes tiles from the rectangles on the field.
     */
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

    /**
     *
     * @return Array List of rectangles which represents the game matrix.
     */
    public List<Rectangle2D> getGameMatrix() {
        return gameMatrix;
    }

    /**
     * Defines in which rectangle where the bomb is dropped by calculating the point.
     * @param node represents the game object
     * @return the point's coordinates or null
     */
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
