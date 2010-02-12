package asePackage;

import java.util.ArrayList;

/**
 * @author Ioan
 *
 */

public class QueueManager {
	private final static int FIRSTQUEUENUMBER=1;
	private ArrayList<Queue> customerQueue;
	private int nextQueueNumber;
	
	public QueueManager() {
		this.customerQueue=new ArrayList<Queue>();
		this.nextQueueNumber=FIRSTQUEUENUMBER;
	}
	public void addQueueElement(Customer element, ArrayList<Transaction> transactions) {
		this.customerQueue.add(new Queue(element,transactions,this.nextQueueNumber));
		this.nextQueueNumber++;
	}
	public Queue removeQueueElement() {
		return this.customerQueue.remove(0);
	}
	public boolean isQueueEmpty(){
		return this.customerQueue.isEmpty();
	}
	public int getNextNumber(){
		return this.nextQueueNumber;
	}
}
