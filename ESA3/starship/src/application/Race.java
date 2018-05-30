package application;

import java.util.Random;


import javafx.animation.Animation.Status;
import javafx.scene.shape.Rectangle;

public class Race implements Runnable{

	private Rectangle rect;
	private final int COUNT_STARSHIPS = 3;
	private int posX[] = new int[COUNT_STARSHIPS];
	private int posY[] = new int[COUNT_STARSHIPS];
	private StarShip threads[] = new StarShip[COUNT_STARSHIPS];
	private StatusThread statusThread = new StatusThread();
	private boolean done = false;
	private int end = 200;   // Sieger-x-Wert
	private Random random = new Random();
	private int won = -1;    // Thread-Nr. des Gewinners
	
	//Statusanzeige
	private String statusMsg = new String(" ");
	
	public class StarShip extends Thread{
		private int nr = 0;
		private int x = 0;
		private long sleepTime;
		
		public StarShip(int nr) {
			this.nr = nr;
		}
		@Override
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
	}
	
	public void init() {
		for (int i = 0; i < COUNT_STARSHIPS; i++) {
			posX[i] = 10;
			posY[i] = 10 + (10 + 100*i);
			threads[i] = new StarShip(i);
			threads[i].start();
		} // for
		statusThread.start();
	}
	
	public void run() {
		while (!done) {
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				//
			} // catch
		} // while
	} //
	


}
