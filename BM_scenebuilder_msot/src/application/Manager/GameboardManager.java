package application.Manager;

import application.game.DestructibleBlock;
import application.game.Field;
import application.game.IndestructibleBlock;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.Random;

public class GameboardManager {

    private ArrayList<int[][]> randomPick = new ArrayList<>();
    private Random rand = new Random();

    public void fillGameBoardWithBlocks(Field field, application.manager.AssetManager assetManager) {

        int counter = 0;

        // ToDo: set NonDestructibleBlocks programatically
        // ToDo: create Object for Blocks
        // 0 = empty field
        // 1 = IndestructibleBlock Block
        // 2 = Destructible Block
        int[][] gameBoardAsIntArri = getGameRandomGameboard();
        for (int[] y : gameBoardAsIntArri) {

            for (int x : y) {

                if (x == 1) {
                    Rectangle2D r = field.getGameMatrix().get(counter);
                    IndestructibleBlock i = new IndestructibleBlock();
                    i.setId("IndestructibleBlock");
                    i.setImage(assetManager.getImage("indestructible"));
                    i.setFitWidth(r.getWidth() - 10);
                    i.setFitHeight(r.getHeight() - 10);
                    i.setX(r.getMinX() + 5);
                    i.setY(r.getMinY() + 5);
                    field.add(i);
                }
                //setting up the destructible blocks on the game field
                if (x == 2) {
                    Rectangle2D r = field.getGameMatrix().get(counter);
                    DestructibleBlock d = new DestructibleBlock();
                    d.setId("DestructibleBlock");
                    d.setImage(assetManager.getImage("destructible"));
                    d.setFitWidth(r.getWidth() - 10);
                    d.setFitHeight(r.getHeight() - 10);
                    d.setX(r.getMinX() + 5);
                    d.setY(r.getMinY() + 5);
                    field.add(d);
                }
                counter++;
                //System.out.println(counter);
            }
        }
    }

    private int[][] getGameRandomGameboard() {

        // add your favorite gameboard style here

        int[][] gb1 = new int[][]{
                {0, 0, 2, 0, 2, 2, 0, 0, 2, 2, 0},//10
                {0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2},//21
                {2, 2, 0, 2, 2, 2, 2, 0, 0, 2, 2},//32
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 2},//43
                {2, 0, 2, 2, 0, 2, 0, 2, 0, 0, 0},//54
                {2, 1, 0, 1, 0, 1, 2, 1, 2, 1, 0},//65
                {0, 0, 2, 2, 2, 0, 0, 2, 0, 0, 2},//76
                {2, 1, 0, 1, 0, 1, 2, 1, 2, 1, 0},//87
                {0, 0, 2, 2, 0, 2, 0, 0, 2, 2, 2},//98
                {2, 1, 0, 1, 2, 1, 2, 1, 0, 1, 0},//109
                {2, 0, 0, 2, 0, 2, 2, 0, 2, 0, 0},//120
        };

        int[][] gb2 = new int[][]{
                {0, 2, 2, 0, 0, 2, 0, 0, 2, 2, 0},//10
                {0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2},//21
                {0, 2, 0, 2, 2, 0, 2, 0, 0, 2, 2},//32
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2},//43
                {2, 0, 2, 0, 0, 2, 2, 2, 0, 0, 0},//54
                {2, 1, 0, 1, 0, 1, 2, 1, 2, 1, 0},//65
                {0, 0, 2, 2, 2, 0, 0, 2, 0, 0, 2},//76
                {2, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},//87
                {0, 0, 2, 0, 0, 2, 2, 0, 2, 2, 2},//98
                {2, 1, 0, 1, 2, 1, 2, 1, 0, 1, 2},//109
                {2, 0, 2, 0, 0, 2, 2, 0, 0, 0, 0},//120
        };

        int[][] gb3 = new int[][]{
                {0, 0, 0, 0, 0, 2, 0, 0, 2, 2, 0},//10
                {0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2},//21
                {2, 0, 0, 2, 2, 0, 2, 0, 0, 2, 2},//32
                {2, 1, 0, 1, 2, 1, 0, 1, 2, 1, 2},//43
                {2, 0, 2, 2, 0, 2, 0, 2, 0, 0, 0},//54
                {2, 1, 2, 1, 0, 1, 2, 1, 2, 1, 0},//65
                {2, 0, 2, 2, 2, 0, 0, 2, 0, 0, 2},//76
                {2, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0},//87
                {2, 0, 2, 2, 0, 2, 2, 2, 2, 2, 2},//98
                {2, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0},//109
                {2, 2, 2, 2, 0, 2, 0, 0, 0, 0, 0},//120
        };

        int[][] gb4 = new int[][]{
                {0, 0, 0, 0, 0, 2, 0, 0, 2, 2, 0},//10
                {0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2},//21
                {0, 0, 0, 2, 2, 0, 2, 0, 0, 2, 2},//32
                {0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 2},//43
                {2, 2, 2, 2, 0, 2, 0, 2, 0, 0, 0},//54
                {2, 1, 2, 1, 0, 1, 2, 1, 2, 1, 0},//65
                {2, 2, 2, 2, 0, 2, 2, 0, 0, 0, 2},//76
                {2, 1, 0, 1, 2, 1, 2, 1, 2, 1, 0},//87
                {0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},//98
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0},//109
                {0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0},//120
        };
        randomPick.add(gb1);
        randomPick.add(gb2);
        randomPick.add(gb3);
        randomPick.add(gb4);

        return randomPick.get(rand.nextInt(randomPick.size()));
    }

}
