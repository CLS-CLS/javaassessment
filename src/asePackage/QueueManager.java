package asePackage;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Queue Manager class is has the purpose to administrate the content elements of the program queue.
 * It contains a list all the queue elements and also methods to add and remove elements from the queue.
 * In addition to that it provides a testing method that returns true or false depending on the list
 * if is empty or not. The last method of the class returns the next available queue number.
 * @author Ioan
 *
 */

public class QueueManager{
	private static QueueManager qm = new QueueManager();
	private final static int MAXIMUMNUMBEROFELEMENTS = 10;
	private final static int FIRSTQUEUENUMBER = 1;
	private ArrayList<Queue> customerQueue;
	private int nextQueueNumber;
	
	/**
	 * The empty constructor which initialise the object with an empty queue and
	 * with a default queue number. 
	 */
	private QueueManager() {
		this.customerQueue=new ArrayList<Queue>();
		this.nextQueueNumber=FIRSTQUEUENUMBER;
	}
	/**
	 * The current constructor creates a new queue starting from a previous queue. It
	 * will get the next queue number from outside.
	 * @param customerQueue a customer queue
	 * @param nextQueueNumber next available queue number
	 */
	
	
	/**
	 * Will add a new element in the queue. The new queue element it created starting from
	 * the customer information and his transaction list. The method will allocate automatically
	 * the next available queue number to the new element. If the queue size is equal to the 
	 * MAXIMUMNUMNEROFELEMENTS then the element will not be added immediately. Instead it will be
	 * added when the queue size falls less than the MAXIMUMNUMNEROFELEMENTS.
	 * @param element customer details
	 * @param transactions customer's list of transactions
	 */
	public synchronized void addQueueElement(Customer element, ArrayList<Transaction> transactions) {
		if (customerQueue.size()==MAXIMUMNUMBEROFELEMENTS){
			try{
				wait();
			}catch (InterruptedException e){
				System.out.println("Oops");
				e.printStackTrace();
			}
		}
		customerQueue.add(new Queue(element,transactions,this.nextQueueNumber));
		nextQueueNumber++;
		notifyAll();
	}
	/**
	 * Will remove the first inserted element from the queue. It returns an queue element containing
	 * information about the customer and his transactions.
	 * @return a queue object
	 */
	public synchronized Queue removeQueueElement() {
		Queue returnQueue = null;
		if(customerQueue.isEmpty()){
			try{
				System.out.println(Thread.currentThread().getName() + " waits");
				wait();
			}catch (InterruptedException e) {
				System.out.println("Oops ");
				e.printStackTrace();
			}
		}
		else{
			System.out.println(Thread.currentThread().getName() +" accesses cq" );
			returnQueue = customerQueue.remove(0);
			notifyAll();
			
		}
		return returnQueue;
	}
	/**
	 * Tests if the queue is empty or not.
	 * @return queue status by true or false
	 */
	public synchronized boolean isQueueEmpty(){
		return this.customerQueue.isEmpty();
	}
	/**
	 * Provides the next available queue number.
	 * @return a queue number.
	 */
	public int getNextNumber(){
		return this.nextQueueNumber;
	}
	
	public synchronized boolean containsCustomer(Customer cust){
		for(Queue q:customerQueue){
			if(q.getCustomer().equals(cust))return true;
		}
		return false;
		
	}
	
	public synchronized String queueCustomersToString(){
		String str = new String();
		int counter = 0;
		for (Queue q : customerQueue){
			counter ++;
			str += counter +") " + q.getCustomer().toString() + "\n";
		}
		return str;
	}
	public synchronized void awakeAllThreads() {
		notifyAll();
		
	}
	
	public static QueueManager getInstance(){
		return qm;
	}
	
	
}
