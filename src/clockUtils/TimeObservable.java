package clockUtils;


public interface TimeObservable {
	public void notifyTimeObservers();
	public void addTimeObserver(TimeObserver tobs);
	public boolean deleteTimeObserver(TimeObserver tobs);

}
