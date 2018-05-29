package application.game;

import javafx.scene.shape.Rectangle;
import java.util.Random;
import application.controller.rr_controller;


public class rennen extends Thread {

    private Rectangle r;
    private int nr;
    private Random random = new Random();
    private long sleepTime;

    public rennen(Rectangle r) {
        this.r = r;
        this.nr = nr;
    }

    @Override
    public void run() {
        int _nr = nr++;

        sleepTime = Math.abs(random.nextLong() % 1000);

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("Schade, ich (" + _nr + ") habe verloren.");
            return;
        }

    }

}
