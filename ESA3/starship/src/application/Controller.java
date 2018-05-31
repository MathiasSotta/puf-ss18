package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.sun.prism.paint.Color;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable{
	
	@FXML
	private Rectangle blueSpaceShuttle;
	
	@FXML
	private Rectangle redSpaceShuttle;
	
	@FXML
	private Rectangle greenSpaceShuttle;
	
	private Rectangle rect;
	private final int COUNT_STARSHIPS = 3;
	private int posX[] = new int[COUNT_STARSHIPS];
	private int posY[] = new int[COUNT_STARSHIPS];
	private Starship threads[] = new Starship[COUNT_STARSHIPS];
	private StatusThread statusThread = new StatusThread();
	private boolean done = false;
	private int end = 200;   // Sieger-x-Wert
	private Random random = new Random();
	private int won = -1;   
	//Statusanzeige
	private String statusMsg = new String(" ");
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//blueSpaceShuttle.setTranslateX(blueSpaceShuttle.getTranslateX() + 100);
		//move();
		statusThread.init();
	}
	
	
	public class Starship extends Thread{
		private int nr = 0;
		private int x = 0;
		private long sleepTime;
		
		public Starship(int nr) {
			this.nr = nr;
		}
		public void run() {
			int _nr = nr + 1;
			while (x<end) {
				sleepTime = Math.abs(random.nextLong() %1000);
				
				try {
					Thread.sleep(sleepTime);
				}catch(InterruptedException e) {
					System.out.println("Schade, ich (Nr " + nr + "habe verloren");
					return;
				}
				x +=10;
				posX[nr] = x;
				move();
			}
			System.out.println("Ich habe gewonnen " + nr);
			for (int i=0; i<COUNT_STARSHIPS; i++) {
				if(i!=nr) {
					threads[i].interrupt();
				}
			}
			won = nr;
			done = true;
		}
		public int getX() {
			return x;
		}
		public void move() {
		       blueSpaceShuttle.setTranslateX(blueSpaceShuttle.getTranslateX() + 50);
		   	   redSpaceShuttle.setTranslateX(redSpaceShuttle.getTranslateX()+50);
		   	   greenSpaceShuttle.setTranslateX(greenSpaceShuttle.getTranslateX()+50);
		}
	}
	
	private class StatusThread extends Thread{
		public void run() {
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
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					return;
				} 
			}
		}
		public void init() {
			for (int i = 0; i < COUNT_STARSHIPS; i++) {
				posX[i] = 10;
				posY[i] = 10 + (10 + 100*i);
				threads[i] = new Starship(i);
				threads[i].start();
			} // for
			statusThread.start();
		}

}
}
