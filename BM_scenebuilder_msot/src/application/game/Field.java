package application.game;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private AnchorPane fieldPane;

    private List<Node> staticElements = new ArrayList<>();

    private List<Bomb> bombs = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

    private List<Rectangle2D> gameMatrix = new ArrayList<>();

    private List<DestructableBlock> destructableBlocks = new ArrayList<>();

    public Field(AnchorPane fieldPane) {
        this.fieldPane = fieldPane;

        // parse and set static elements
        initStaticElements();

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
        Bomb removeBomb = null;
        for (Bomb b : this.bombs) {
            b.update(now, delta);
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
                        }
                    }
                }
                //destructable blocks
                for(DestructableBlock d : this.destructableBlocks){
                    if(b.withinExplosion(d)){
                        gameMatrix.remove(d);
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
            p.update(now, delta);
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

    public void addDestrBlock(DestructableBlock destrBlock){
        this.add(destrBlock);
        this.destructableBlocks.add(destrBlock);
    }

    public  List<DestructableBlock> getDestructableBlocks(){
        return destructableBlocks;
    }



    public void initStaticElements() {
        for (Node node : this.fieldPane.getChildren()) {
            if (node.getId().equals("UndestructableBlock")) {
                staticElements.add(node);
            }
            if (node.getId().equals("DestructableBlock")) {
                staticElements.add(node);
            }
        }
    }

    private void initGameMatrix() {
        double matrixTileHeight = this.fieldPane.getMaxHeight() / 11;
        double matrixTileWidth = this.fieldPane.getMaxWidth() / 11;

        for (int i=0; i<this.fieldPane.getMaxWidth(); i+=matrixTileWidth) {
            for (int j = 0; j<this.fieldPane.getMaxHeight(); j+=matrixTileHeight) {
                Rectangle2D r = new Rectangle2D(j, i, matrixTileWidth, matrixTileHeight);
                gameMatrix.add(r);
            }
        }
    }

    public List<Rectangle2D> getGameMatrix() {
        return gameMatrix;
    }

    public Point2D getBombTileCenterPosition(Node node) {
        double x;
        double y;

        List<Rectangle> intersectingTiles = new ArrayList<>();

        for (Rectangle2D r : gameMatrix) {
            Rectangle currentMatrixTile = new Rectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());

            if (currentMatrixTile.getBoundsInParent().intersects(node.getBoundsInParent())) {
                intersectingTiles.add(currentMatrixTile);
            }
        }
        if(intersectingTiles.size() == 1) {
            Rectangle r = intersectingTiles.get(0);
            x = r.getX() + (r.getWidth()/2);
            y = r.getY() + (r.getHeight()/2);
            return new Point2D(x, y);
        }
        else if(intersectingTiles.size() > 1) {

            // Algorithm to calculate player position in relation to tiles of the game matrix
            // Simplified: if player is in fields A and B and most of the players bounding-box is in field A, the bomb is being placed in field A and vice versa
            for (int i=0; i<intersectingTiles.size(); i++) {
                double nodeMinX = node.getBoundsInParent().getMinX();
                double nodeMinY = node.getBoundsInParent().getMinY();
                double nodeMaxX = node.getBoundsInParent().getMaxX();
                double nodeMaxY = node.getBoundsInParent().getMaxY();
                double aMatrixMaxX = intersectingTiles.get(i).getBoundsInParent().getMaxX();
                double aMatrixMaxY = intersectingTiles.get(i).getBoundsInParent().getMaxY();
                double bMatrixMinX = intersectingTiles.get(i+1).getBoundsInParent().getMinX();
                double bMatrixMinY = intersectingTiles.get(i+1).getBoundsInParent().getMinY();

                int dX = ((Math.abs(aMatrixMaxX - nodeMinX) > Math.abs(bMatrixMinX - nodeMaxX)) && (aMatrixMaxY >= nodeMaxY) && (bMatrixMinY <= nodeMinY)) ? i : i+1;
                int dY = ((Math.abs(aMatrixMaxY - nodeMinY) > Math.abs(bMatrixMinY - nodeMaxY)) && (aMatrixMaxX >= nodeMaxX) && (bMatrixMinX <= nodeMinX)) ? i : i+1;

                Rectangle rX = intersectingTiles.get(dX);
                Rectangle rY = intersectingTiles.get(dY);
                x = rX.getX() + (rX.getWidth()/2);
                y = rY.getY() + (rY.getHeight()/2);

                return new Point2D(x, y);
            }
        }
        return null;
    }

}
