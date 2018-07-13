package application.game;

import application.manager.AssetManager;

/**
 * Making blocks for the game board.
 */
public class BlockFactory {

    public static final String DESTRUCTIBLE = "destructible";
    public static final String INDESTRUCTIBLE = "indestructible";

    /**
     * Creating the two types of blocks and sets the images.
     * @param type destructable or indestructuable block
     * @return d for returning a destructable block and i for returning an indestructable block
     */
    public static Block getBlock(String type)
    {
        AssetManager assetManager = AssetManager.getInstance();

        if (type == BlockFactory.DESTRUCTIBLE) {
            DestructibleBlock d = new DestructibleBlock();
            d.setId("DestructibleBlock");
            d.setImage(assetManager.getImage(BlockFactory.DESTRUCTIBLE));

            return d;
        } else {
            IndestructibleBlock i = new IndestructibleBlock();
            i.setId("IndestructibleBlock");
            i.setImage(assetManager.getImage(BlockFactory.INDESTRUCTIBLE));

            return i;
        }
    }
}
