package clockUtils;


import java.util.ArrayList;
import java.util.Observable;


/**
 * @author Chris
 * A countDown clock. Starts the countdown
 * from a given value until it reaches 0.
 * 
 * Implements Observable Gui Observers can register on it
 * Implements BankClock so Observers that are intrested only when the countDown 
 * is over can register on it.
 * BankClock interface extends runnable too.
 * 
 *
 */
public class ClockModel extends Observable implements BankClock{
	public static final int SECONDSOFDECREASE = 1;
	boolean endOfTime = false;
	int minutes;
	int seconds;
	
	
	
	public ClockModel(int minutes, int seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	/**
	 * Decreases the timer by some seconds
	 * @param value the seconds which the timer will be decreased
	 */
	private void decrease(int value){
		seconds = seconds - value;
		if (seconds < 0){
			minutes = minutes -  Math.abs(seconds)%60;
			seconds = 60 + seconds;
		}
		setChanged();
		notifyObservers(new int[] {minutes , seconds});
	}

	/**
	 * checks if the countdown is over
	 * @return true if the countdown is over
	 */
	private boolean isEndOfTime() {
		return (minutes <=0 && seconds <=0);
	}
    
	/**
	 * each second decreases the timer by SECONDEOFDECRESE seconds
	 * and notify time the timeObservers when the count down i over
	 */
	public void run() {
		while(!isEndOfTime()){
			try {
				Thread.sleep(1000);
				decrease(SECONDSOFDECREASE);
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

	/**
	 * resets the clock
	 */
	public void setToZero() {
		minutes = 0;
		//set seconds to 1 so the next call of the decrease will give 0 
		seconds = 1;
		notifyObservers();
		notifyTimeObservers();
		
	}
	
}
