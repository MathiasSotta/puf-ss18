/**
 *
 * @author clecon
 * @date June 2009
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Random;

public class ThreadStarship extends Applet implements Runnable {

    private String imageFilenames[] = {"raumschiff.gif"};
    private final int COUNT_STARSHIPS = 3;
    private int posX[] = new int[COUNT_STARSHIPS];
    private int posY[] = new int[COUNT_STARSHIPS];
    private StarShip threads[] = new StarShip[COUNT_STARSHIPS];
    private StatusThread statusThread = new StatusThread();
    private Image img, img2;
    private boolean done = false;
    private int end = 200; // Sieger-x-Wert
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private Random random = new Random();
    private int won = -1; // Thread-Nr. des Gewinners

    // F�r Statusanzeige:
    private String statusMsg = new String("");

    // =====================================================
    class StarShip extends Thread {
        private int nr = 0;
        private int x = 0;
        private long sleepTime;

        public StarShip(int nr) {
            this.nr = nr;
        } // Konstruktor

        public void run() {
            int _nr = nr+1;
            while (x < end) {

                sleepTime = Math.abs(random.nextLong() % 1000);

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    // Bei Interrupt: Aufh�ren
                    System.out.println("Schade, ich (Nr. " + _nr +") habe verloren :-(");
                    return;
                } // catch
                x += 10;
                posX[nr] = x;
                repaint();
            } // while
            // Gewonnen!
            System.out.println("Hurra! Ich (Nr. " + _nr + " habe gewonnen!");
            for (int i=0; i<COUNT_STARSHIPS; i++) {
                if (i!=nr) {
                    threads[i].interrupt();
                } // if
            } // for
            won = nr;
            done = true;
            repaint();
        } // run

        public int getX() {
            return x;
        } // getX
    } // inner class StarShip


    // =====================================================
    private class StatusThread extends Thread {

        public void run() {
            while (true) {
                String nr = "1";
                statusMsg = "Raumschiff Nr. ";
                if (threads[0].getX() < threads[1].getX()) {
                    nr = "2";
                } // if
                if (threads[1].getX() < threads[2].getX()) {
                    nr = "3";
                }
                statusMsg += nr;
                statusMsg += " f�hrt!";
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                } // catch
            } // while
        } // run
    } // inner class StatusTread

    // =====================================================

    public void init() {
        img = getImage(getCodeBase(), imageFilenames[0]);
        setBackground(Color.BLUE);
        for (int i = 0; i < COUNT_STARSHIPS; i++) {
            posX[i] = 10;
            posY[i] = 10 + (10 + 100*i);
            threads[i] = new StarShip(i);
            threads[i].start();
        } // for
        statusThread.start();
    } // Konstruktor

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        // g.drawString("Hallo", posX[0], 10);
        // Erst einmal Zeichenfl�che l�schen:
        Color original = g.getColor();
        Dimension size = this.getSize();
        g.clearRect(0, 0, size.width, size.height);
        // g.setColor(original);
        g.setColor(Color.BLUE);
        setBackground(Color.BLUE);


        for (int i = 0; i < posX.length; i++) {
            // g.drawImage(img, posX[i], posY[i], 100, 75, this);
            g.drawImage(img, posX[i], posY[i], 100, 75, this);
        } // for

        if (done) {
            img2 = getImage(getCodeBase(), "siegerbild.gif");
            g.drawImage(img2, 320, 17 + (10 + 100*won), 50, 37, this);
        } // if

        g.setColor(Color.YELLOW);
        g.drawLine(300, 10, 300, 300);

        g.setFont(new Font("Arial",Font.PLAIN,24));
        g.setColor(Color.YELLOW);
        g.drawString(statusMsg, 10, 320);

    } // paint

    /**
     * @param args
     */

    public void run() {
        while (!done) {
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                //
            } // catch
        } // while
    } // run


    public static void main(String[] args) {
		/*
		ThreadStarship mainThread = new ThreadStarship();
		mainThread.start();
		 */

    }

} // class ThreadStarship

