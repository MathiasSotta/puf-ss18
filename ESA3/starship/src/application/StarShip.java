package application;

public class StarShip extends Thread{
	private int nr = 0;
	private int x = 0;
	private long sleepTime;
	
	public StarShip(int nr) {
		this.nr = nr;
	}

}
