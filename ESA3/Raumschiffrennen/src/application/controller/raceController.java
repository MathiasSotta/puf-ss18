package application.controller;

import java.net.URL;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class raceController implements Initializable {

    Image starshipSprite = new Image(getClass().getResource("/images/raumschiff.gif").toString());

    @FXML
    private Rectangle ship1;

    @FXML
    private Rectangle ship2;

    @FXML
    private Rectangle ship3;

    @FXML
    private Text statusText;

    ArrayList<Object> shipList = new ArrayList<>();
    private int nr = 0;
    private int end = 300;
    private Random random = new Random();
    private TranslateTransition trans = new TranslateTransition();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Starship r1 = new Starship(ship1);
        Starship r2 = new Starship(ship2);
        Starship r3 = new Starship(ship3);

        r1.start();
        r2.start();
        r3.start();

        statusText.textProperty().bind(
                r1.rect.xProperty().asString());

        System.out.println("Init done");
        //r1.rect.setOnMouseEntered(e -> move(raumschiffBlau) );
        //t.xProperty().add(100);
        //statusText.setText("Und los gehts");
    }

    private class Starship extends Thread {

        private Rectangle rect;
        private double pos;
        private double x;
        private long sleepTime;
        private int _nr;


        private Starship(Rectangle rect) {
            this.rect = rect;
            this.rect.setFill(new ImagePattern(starshipSprite, 0, 0, 150, 60, false));
            shipList.add(this);
            this.x = this.rect.xProperty().getValue();
            this._nr = nr++;
        }

        public void run() {
            System.out.println("Run ist gestartet");

            while (pos < end) {
                if (isInterrupted()) {return;};
                sleepTime = Math.abs(random.nextLong() % 1000);

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println("Schade, ich " + this._nr +  " habe verloren.");
                    return;
                }
                pos+=20;
                move(this.rect, this.x+20);
                //this.rect.setX(this.rect.getX()+20);
                System.out.println(_nr + " " + " x: " + this.rect.getX() + "    Pos: " + pos);

            }
            this.interrupt();

            Runnable task = () -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(() -> this.interrupt());
                } catch (InterruptedException e) {
                    System.out.println("Interruption!");
                    return;
                }
            };
            new Thread(task).start();


            System.out.println("Hurra! Ich (Nr. " + this._nr + " habe gewonnen!");

        }

        public void move(Rectangle rect, double x) {

//            trans.setByX(x);
////            trans.setDuration(Duration.millis(1000));
//            trans.setNode(rect);
//            trans.play();

            rect.setTranslateX(rect.getTranslateX() + x);
        }

    }

    public void setText(String s) {
        statusText.setText(s);
    }

}
