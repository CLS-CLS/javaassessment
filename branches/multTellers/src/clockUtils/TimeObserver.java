package clockUtils;

/**
 * 
 * @author Chris
 * This interface is implemented by any class wanting to update its
 * value when the count down is over
 */
public interface TimeObserver {
	public void endOfTime();

}
