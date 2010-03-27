package asePackage;

import java.util.ArrayList;

/**
 * Queue Manager class is has the purpose to administrate the content elements of the program queue.
 * It contains a list all the queue elements and also methods to add and remove elements from the queue.
 * In addition to that it provides a testing method that returns true or false depending on the list
 * if is empty or not. The last method of the class returns the next available queue number.
 * @author Ioan
 *
 */

public class QueueManager {
	private final static int FIRSTQUEUENUMBER=1;
	private ArrayList<Queue> customerQueue;
	private int nextQueueNumber;
	
	/**
	 * The empty constructor which initialise the object with an empty queue and
	 * with a default queue number. 
	 */
	public QueueManager() {
		this.customerQueue=new ArrayList<Queue>();
		this.nextQueueNumber=FIRSTQUEUENUMBER;
	}
	/**
	 * The current constructor creates a new queue starting from a previous queue. It
	 * will get the next queue number from outside.
	 * @param customerQueue a customer queue
	 * @param nextQueueNumber next available queue number
	 */
	public QueueManager(ArrayList<Queue> customerQueue, int nextQueueNumber){
		this.customerQueue = customerQueue;
		this.nextQueueNumber = nextQueueNumber;
	}
	
	/**
	 * Will add a new element in the queue. The new queue element it created starting from
	 * the customer information and his transaction list. The method will allocate automatically
	 * the next available queue number to the new element.
	 * @param element customer details
	 * @param transactions customer's list of transactions
	 */
	public void addQueueElement(Customer element, ArrayList<Transaction> transactions) {
		this.customerQueue.add(new Queue(element,transactions,this.nextQueueNumber));
		this.nextQueueNumber++;
	}
	/**
	 * Will remove the first inserted element from the queue. It returns an queue element containing
	 * information about the customer and his transactions.
	 * @return a queue object
	 */
	public Queue removeQueueElement() {
		return this.customerQueue.remove(0);
	}
	/**
	 * Tests if the queue is empty or not.
	 * @return queue status by true or false
	 */
	public boolean isQueueEmpty(){
		return this.customerQueue.isEmpty();
	}
	/**
	 * Provides the next available queue number.
	 * @return a queue number.
	 */
	public int getNextNumber(){
		return this.nextQueueNumber;
	}
}