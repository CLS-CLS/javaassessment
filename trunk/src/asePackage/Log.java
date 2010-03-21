package asePackage;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Log class is the event manager for the log. It will control the list of events 
 * created on the program execution. It contains methods for adding new log events
 * and also methods for creating statistic information from the log.
 * @author Ioan
 */

public class Log extends Observable {
	private ArrayList<LogEvent> logEventList;
	
	/**
	 * The constructor for the case we don't have an initial set of events.
	 * It creates a new instance of a list for keeping the events.
	 */
	public Log() {
		this.logEventList = new ArrayList<LogEvent>();
	}
	/**
	 * The constructor for the case in which a initial set of events is supplied.
	 * @param logEventList previous set of events
	 */
	public Log(ArrayList<LogEvent> logEventList) {
		this.logEventList = logEventList;
	}
	
	/**
	 * Add a new log event to the event list starting from the values at transaction certain moment.
	 * @param queueNumber queue number of the customer
	 * @param customer instance of the customer who will do the transaction
	 * @param transaction all the data about the transaction
	 * @param status indicates if the transaction was successful of failed
	 * @param errorMessage stores the message for the case of error
	 */
	public synchronized void addLogEvent(int queueNumber, int tellerID, Customer customer, Transaction transaction, String status, String errorMessage) {
		LogEvent le = new LogEvent(queueNumber, tellerID, customer, transaction, status, errorMessage);
		logEventList.add(le);
		setChanged();
		notifyObservers(le.toString());
	}
	/**
	 * Add a new log event to the event list for the case we don't know or we don't know all the normal transaction information.
	 * @param queueNumber queue number of the customer
	 * @param customer instance of the customer who will do the transaction
	 * @param status indicates that the customer has enter the queue or if the transaction
	 * was successful or not
	 */
	public void addLogEvent(int queueNumber, Customer customer, String status) {
		LogEvent le = new LogEvent(queueNumber, customer, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le.toString());
	}
	/*
	 * NEW
	 */
	public void addLogEvent(String status, String message) {
		LogEvent le = new LogEvent(status, message);
		logEventList.add(le);
		setChanged();
		notifyObservers(le.toString());
	}
	
	/**
	 * The purpose of this method is to return the total number of unique customers 
	 * served in the program execution time. A customer can be served for just a queue number,
	 * he cannot enter twice in the queue.
	 * @return number of served customers
	 */
	public int getProcessedCustomersNumber() {
		int queueNumber=0;
		
		for(int i=0;i<logEventList.size();i++) {		
			if(logEventList.get(i).getQueueNumber()>queueNumber) {
				queueNumber=logEventList.get(i).getQueueNumber();
			}
		}
		return queueNumber;
	}
	/**
	 * Compute the total amount of money deposited in the time of program execution and which
	 * was done from a successful transaction.
	 * @return total deposited money
	 */
	public double getDepositTotal(){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if((logEventList.get(i).getTransactionType().equals(Transaction.DEPOSIT) ||
					logEventList.get(i).getTransactionType().equals(Transaction.OPEN)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		}
		return total;
	}
	/**
	 * compute the total amount of money withdrawn in the time of program execution and which 
	 * was done from a successful transaction.
	 * @return total withdrawn money
	 */
	public double getWithdrawalTotal(){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if((logEventList.get(i).getTransactionType().equals(Transaction.WITHDRAWAL) ||
					logEventList.get(i).getTransactionType().equals(Transaction.CLOSE)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		}
		return total;
	}
	/**
	 * compute the total amount of money withdrawn in the time of program execution and which 
	 * was done from a successful transaction by a single customer.
	 * @param customer the customer that needs his total
	 * @return total withdrawn money
	 */
	public double getCustomerWithdrawalTotal(Customer customer){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getTransactionType().equals(Transaction.WITHDRAWAL) && 
					logEventList.get(i).getCustomerID()==customer.getId() && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		} 
		return total;
	}
	/**
	 * Creates a report that contains a list of all events that happened in program execution time.
	 * @return a report string
	 */
	@Override
	public String toString() {
		String result="";
		int i;
		
		for(i=0;i<logEventList.size();i++) {
			result+=logEventList.get(i)+"\n";
		}
		result+=getStatistics();
		addLogEvent(LogEvent.MESSAGE,this.getStatistics());
		return result;
	}
	
	private String getStatistics() {
		String result;
		result="\nStatistics\n";
		result+="-------------\n";
		result+="Number of Processed Customers : " + getProcessedCustomersNumber() + "\n";
		result+="Total Deposited Money : £" + getDepositTotal() + "\n";
		result+="Total Withdrawn Money : £" + getWithdrawalTotal() + "\n";
		return result;
	}
}
