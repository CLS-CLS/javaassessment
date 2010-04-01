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
		notifyObservers(le);
	}

	/*
	 * NEW
	 */
	public synchronized void addLogEventJoinQueue(int queueNumber, Customer customer, String status) {
		LogEvent le = new LogEvent(queueNumber, customer, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}
	/*
	 * NEW
	 */
	public synchronized void addLogEventExitQueue(int queueNumber, int tellerID, Customer customer, String status) {
		LogEvent le = new LogEvent(queueNumber, tellerID, customer, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}
	public synchronized void addLogEventStartTrans(int queueNumber, int tellerID, Customer customer, Transaction transaction, String status) {
		LogEvent le = new LogEvent(queueNumber, tellerID, customer, transaction, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}
	/*
	 * NEW
	 */
	public synchronized void addLogEventStatistics(String status, String message) {
		LogEvent le = new LogEvent(status, message);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
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
	private double getDepositTotal(){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if((logEventList.get(i).getTransactionType().equals(Transaction.DEPOSIT) ||
					logEventList.get(i).getTransactionType().equals(Transaction.OPEN) ||
					logEventList.get(i).getTransactionType().equals(Transaction.DEPOSITFOREIGNACCOUNT)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}
	/**
	 * compute the total amount of money withdrawn in the time of program execution and which 
	 * was done from a successful transaction.
	 * @return total withdrawn money
	 */
	private double getWithdrawalTotal(){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if((logEventList.get(i).getTransactionType().equals(Transaction.WITHDRAWAL) || 
					logEventList.get(i).getTransactionType().equals(Transaction.CLOSE)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}
	
	
	private double getTransferedTotal() {
		double total=0;
		for(int i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getTransactionType().equals(Transaction.TRANSFER) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}
	
	/**
	 * compute the total amount of money withdrawn in the time of program execution and which 
	 * was done from a successful transaction by a single customer.
	 * @param customer the customer that needs his total
	 * @return total withdrawn money
	 */
	public synchronized double getCustomerWithdrawalTotal(Customer customer){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getTransactionType().equals(Transaction.WITHDRAWAL) && 
					logEventList.get(i).getCustomerID()==customer.getId() && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS))
				total+=logEventList.get(i).getTransactionSum();
		} 
		total = MyUtilities.roundDouble(total);
		return total;
	}

	
	/**
	 * Creates a report that contains a list of all events that happened in program execution time.
	 * @return a report string
	 */
	@Override
	public synchronized String toString() {
		StringBuilder result = new StringBuilder();
						
		for(int i=0;i<logEventList.size();i++) {
			result.append(logEventList.get(i)+"\n");
		}
		result.append(getStatistics());
		addLogEventStatistics(LogEvent.MESSAGE,this.getStatistics());
		return result.toString();
	}
	
	private String getStatistics() {
		String result;
		result="\nStatistics\n";
		result+="-------------\n";
		result+="Number of Processed Customers : " + getProcessedCustomersNumber() + "\n";
		result+="Total Deposited Money : £" + getDepositTotal() + "\n";
		result+="Total Withdrawn Money : £" + getWithdrawalTotal() + "\n";
		result+="Total Transfered Money : £" + getTransferedTotal() + "\n";
		return result;
	}
	
	public String customersSummary() {
		StringBuilder result = new StringBuilder();
		ArrayList<Integer> processedCustomers = new ArrayList<Integer>();
		int currentCustomer = 0;
		for(int i=0; i<logEventList.size();i++) {
			if (logEventList.get(i).getCustomerID()!=-1) {					
				currentCustomer = logEventList.get(i).getCustomerID();
				if(!processedCustomers.contains(currentCustomer)) {
					processedCustomers.add(currentCustomer);
					result.append(getCustomerSummary(i));
				}
			}
		}
		return result.toString();			
	}
	
	private String getCustomerSummary(int firstOccurence) {
		StringBuilder result = new StringBuilder();
		int id = logEventList.get(firstOccurence).getCustomerID();
		result.append("\n Customer " + id + " summary:");
		result.append("\n---------------------------------\n");
		result.append("Number of entrance in the queue : " + getQueueEntranceCustomer(id) + "\n");
		result.append("Total Deposited Money : £" + getDepositTotalCustomer(id) + "\n");
		result.append("Total Withdrawn Money : £" + getWithdrawTotalCustomer(id) + "\n");
		result.append("Total Transfered Money : £" + getTransferTotalCustomer(id) + "\n");
		return result.toString();
	}
	
	private int getQueueEntranceCustomer(int id){
		int i;
		int total=0;
		for(i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getStatus().equals(LogEvent.ENTERQUEUE) && 
					logEventList.get(i).getCustomerID()==id)
				total++;
		}
		return total;
	}
	
	private double getDepositTotalCustomer(int id){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if (logEventList.get(i).getTransactionType()!=null)
				if((logEventList.get(i).getTransactionType().equals(Transaction.DEPOSIT) ||
					logEventList.get(i).getTransactionType().equals(Transaction.OPEN) ||
					logEventList.get(i).getTransactionType().equals(Transaction.DEPOSITFOREIGNACCOUNT)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS) && 
					logEventList.get(i).getCustomerID()==id)
						total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}

	private double getWithdrawTotalCustomer(int id){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if (logEventList.get(i).getTransactionType()!=null)
				if((logEventList.get(i).getTransactionType().equals(Transaction.WITHDRAWAL) || 
					logEventList.get(i).getTransactionType().equals(Transaction.CLOSE)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS) && 
					logEventList.get(i).getCustomerID()==id)
						total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}	
	
	private double getTransferTotalCustomer(int id) {
		double total=0;
		for(int i=0;i<logEventList.size();i++) {
			if (logEventList.get(i).getTransactionType()!=null)
				if(logEventList.get(i).getTransactionType().equals(Transaction.TRANSFER) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS) && 
					logEventList.get(i).getCustomerID()==id)
						total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}
	
	public String tellersSummary() {
		StringBuilder result = new StringBuilder();
		ArrayList<Integer> processedTellers = new ArrayList<Integer>();
		int currentTeller = 0;
		for(int i=0; i<logEventList.size();i++) {
			currentTeller = logEventList.get(i).getTellerID();
			if(currentTeller != -1 && !processedTellers.contains(currentTeller)) {
				processedTellers.add(currentTeller);
				result.append(getTellerSummary(i));
			}
		}
		return result.toString();	
	}
	
	private String getTellerSummary(int firstOccurence) {
		StringBuilder result = new StringBuilder();
		int id = logEventList.get(firstOccurence).getTellerID();
		result.append("\n Teller " + id + " summary:");
		result.append("\n---------------------------------\n");
		result.append("Number of served customers : " + getTellerServedCustomers(id) + "\n");
		result.append("Total Deposited Money : £" + getDepositTotalTeller(id) + "\n");
		result.append("Total Withdrawn Money : £" + getWithdrawTotalTeller(id) + "\n");
		result.append("Total Transfered Money : £" + getTransferTotalTeller(id) + "\n");
		return result.toString();
	}
	
	private int getTellerServedCustomers(int id){
		int i;
		int total=0;
		for(i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getStatus().equals(LogEvent.EXITBANK) && 
					logEventList.get(i).getTellerID()==id)
				total++;
		}
		return total;
	}
	
	private double getDepositTotalTeller(int id){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if (logEventList.get(i).getTransactionType()!=null)
				if((logEventList.get(i).getTransactionType().equals(Transaction.DEPOSIT) ||
						logEventList.get(i).getTransactionType().equals(Transaction.OPEN) ||
						logEventList.get(i).getTransactionType().equals(Transaction.DEPOSITFOREIGNACCOUNT)) && 
						logEventList.get(i).getStatus().equals(LogEvent.SUCCESS) && 
						logEventList.get(i).getTellerID()==id)
					total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}

	private double getWithdrawTotalTeller(int id){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if (logEventList.get(i).getTransactionType()!=null)
				if((logEventList.get(i).getTransactionType().equals(Transaction.WITHDRAWAL) || 
					logEventList.get(i).getTransactionType().equals(Transaction.CLOSE)) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS) && 
					logEventList.get(i).getTellerID()==id)
					total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}	
	
	private double getTransferTotalTeller(int id) {
		double total=0;
		for(int i=0;i<logEventList.size();i++) {
			if (logEventList.get(i).getTransactionType()!=null)
				if(logEventList.get(i).getTransactionType().equals(Transaction.TRANSFER) && 
					logEventList.get(i).getStatus().equals(LogEvent.SUCCESS) && 
					logEventList.get(i).getTellerID()==id)
					total+=logEventList.get(i).getTransactionSum();
		}
		total = MyUtilities.roundDouble(total);
		return total;
	}
	
	public String getSummary() {
		String result;
		result=tellersSummary();
		result+=customersSummary();
		
		return result;
	}
}
