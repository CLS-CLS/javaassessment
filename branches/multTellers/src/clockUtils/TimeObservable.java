package clockUtils;

/**
 * 
 * @author Chris
 * This interface is implemented by any class which wants to register
 * TimeObserver objects
 * 
 */
public interface TimeObservable {
	public void notifyTimeObservers();
	public void addTimeObserver(TimeObserver tobs);
	public boolean deleteTimeObserver(TimeObserver tobs);

}
