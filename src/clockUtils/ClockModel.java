package clockUtils;


import java.util.ArrayList;
import java.util.Observable;

import org.hamcrest.core.IsEqual;

public class ClockModel extends Observable implements BankClock{
	boolean endOfTime = false;
	int minutes;
	int seconds;
	
	
	
	public ClockModel(int minutes, int seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	private void decrease(int value){
		seconds = seconds - value;
		if (seconds < 0){
			minutes = minutes -  Math.abs(seconds)%60;
			seconds = 60 + seconds;
		}
		setChanged();
		notifyObservers(new int[] {minutes , seconds});
	}

	private boolean isEndOfTime() {
		return (seconds <=0 && minutes <=0);
	}

	public void run() {
		while(!isEndOfTime()){
			try {
				Thread.sleep(1000);
				decrease(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyTimeObservers();
	}
	
		
	
	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}





	///////////////////  Time Observer Pattern ////////////////////////
	private ArrayList<TimeObserver> timeObservers = new ArrayList<TimeObserver>();
	
	public void notifyTimeObservers() {
		for (TimeObserver tobs : timeObservers){
			tobs.endOfTime();
		}
	}
	
	public void addTimeObserver(TimeObserver tobs){
		timeObservers.add(tobs);
	}
	
	public boolean deleteTimeObserver(TimeObserver tobs){
		return timeObservers.remove(tobs);
	}
	
}
