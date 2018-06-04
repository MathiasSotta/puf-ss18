package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Controller implements Initializable{

    @FXML
    private Rectangle blueSpaceShuttle;

    @FXML
    private Rectangle redSpaceShuttle;

    @FXML
    private Rectangle greenSpaceShuttle;

    @FXML
    private Text statusText;

    @FXML
    private Rectangle winner;


    private ArrayList<Rectangle> arri = new ArrayList<>();
    private Image starshipImg = new Image(getClass().getResource("/images/raumschiff.gif").toString());

    private final int COUNT_STARSHIPS = 3;
    private int posX[] = new int[COUNT_STARSHIPS];
    private int posY[] = new int[COUNT_STARSHIPS];
    private Starship threads[] = new Starship[COUNT_STARSHIPS];
    private StatusThread statusThread = new StatusThread();
    private boolean done = false;
    private int end = 370;   // Sieger-x-Wert
    private Random random = new Random();
    private Image winnerImg = new Image(getClass().getResource("/images/siegerbild.gif").toString());


    private int won = -1;
    //Statusanzeige
    private String statusMsg = new String(" ");
    private StringProperty statusMsgDisplay = new SimpleStringProperty();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        statusText.textProperty().bind(statusMsgDisplay);

        blueSpaceShuttle.setFill(new ImagePattern(starshipImg, 0, 0, 120, 60, false));
        redSpaceShuttle.setFill(new ImagePattern(starshipImg, 0, 0, 120, 60, false));
        greenSpaceShuttle.setFill(new ImagePattern(starshipImg, 0, 0, 120, 60, false));

        winner.setFill(new ImagePattern(winnerImg));

        arri.add(blueSpaceShuttle);
        arri.add(redSpaceShuttle);
        arri.add(greenSpaceShuttle);

        // init StatusThread - excluding Threads
        statusThread.init();


        }


    private class Starship extends Thread {
        private int nr = 0;
        private int x = 0;
        private long sleepTime;
        private Rectangle r;

        public Starship(int nr, Rectangle r) {
            this.nr = nr;
            this.r = r;
        }
        public void run() {
            System.out.println(Thread.currentThread().getName() + " in " + this.getClass() + " gestartet");
            int _nr = nr + 1;
            //System.out.println(_nr);
            r = arri.get(_nr-1);
            while (x < end) {
                sleepTime = Math.abs(random.nextLong() %1000);
                move(r);
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    System.out.println("Schade, ich (Nr " + _nr + ") habe verloren");
                    return;
                }
                x += 10;
                posX[nr] = x;
            }
            System.out.println("Ich habe gewonnen: Nr " + _nr);

                Platform.runLater(() -> {
                    for (int i=0; i<COUNT_STARSHIPS; i++) {
                        if (i != nr) {
                            threads[i].interrupt();
                            statusThread.interrupt();
                        }
                        won = nr;
                        done = true;
                        displayWinner(_nr);
                    }
                });


        }

        private void displayWinner(int nr) {
            winner.setVisible(true);
            winner.setTranslateY(posY[nr-1]);
            statusMsgDisplay.setValue("Raumschiff Nr. " + nr + " hat gesiegt.");
        }

        public int getX() {
            return x;
        }
        public void move(Rectangle r) {
            r.setTranslateX(r.getTranslateX() + 10);

        }
    }

     class StatusThread extends Thread {
        public void run() {
            System.out.println(Thread.currentThread().getName() + " in " + this.getClass() + " gestartet");
            while(true) {
                String nr = "1";
                statusMsg = "Raumschiff Nr. ";
                if (threads[0].getX() < threads[1].getX()) {
                    nr = "2";
                } // if
                if (threads[1].getX() < threads[2].getX()) {
                    nr = "3";
                }
                statusMsg += nr;
                statusMsg += " führt!";
                statusMsgDisplay.setValue(statusMsg);
                //System.out.println(statusMsg);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
         public void init() {

             // init FixedThreadpool
             ExecutorService executor = Executors.newFixedThreadPool(2);

             for (int i = 0; i < COUNT_STARSHIPS; i++) {
                 posX[i] = 10;
                 posY[i] = (15 + 100*i);

                     threads[i] = new Starship(i, arri.get(i));
                     Thread starshipWorker = new Thread(threads[i]::start);
                     executor.execute(starshipWorker);

             } // for
             Thread statusWorker = new Thread(statusThread::start);
             executor.execute(statusWorker);
             executor.shutdown();
         } // for

     }

}

