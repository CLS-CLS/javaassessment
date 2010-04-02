package asePackage;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Log class is the event manager for the log. It will control the list of events 
 * created on the program execution. It contains methods for adding new log events
 * and also methods for creating statistic information from the log.
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

	/**
	 * Add a new log event in our log manager for entering queue situation
	 * @param queueNumber queue number of the customer
	 * @param customer instance of the customer who will do the transaction
	 * @param status
	 */
	public synchronized void addLogEventJoinQueue(int queueNumber, Customer customer, String status) {
		LogEvent le = new LogEvent(queueNumber, customer, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}

	/**
	 * Add a new log event in our log manager for the exit queue situation
	 * @param queueNumber queue number of the customer
	 * @param tellerID teller that served the customer
	 * @param customer instance of the customer who will do the transaction
	 * @param status 
	 */
	public synchronized void addLogEventExitQueue(int queueNumber, int tellerID, Customer customer, String status) {
		LogEvent le = new LogEvent(queueNumber, tellerID, customer, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}

	/**
	 * Add a new log event in our log manager for the starting a transaction situation
	 * @param queueNumber queue number of the customer
	 * @param tellerID teller that served the customer
	 * @param customer instance of the customer who will do the transaction
	 * @param transaction all the data about the transaction
	 * @param status
	 */
	public synchronized void addLogEventStartTrans(int queueNumber, int tellerID, Customer customer, Transaction transaction, String status) {
		LogEvent le = new LogEvent(queueNumber, tellerID, customer, transaction, status);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}


	/**
	 * Add a new Log for statistics
	 * @param status
	 * @param message statistics text
	 */
	public synchronized void addLogEventStatistics(String status, String message) {
		LogEvent le = new LogEvent(status, message);
		logEventList.add(le);
		setChanged();
		notifyObservers(le);
	}

	/**
	 * The purpose of this method is to return the total number of customers 
	 * served in the program execution time. A customer can be served for just a queue number,
	 * but he can enter twice in the queue.
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
	 * Compute the total amount of money withdrawn in the time of program execution and which 
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

	/**
	 * Compute the total amount of money transfered in the time of program execution and which 
	 * was done from a successful transaction.
	 * @return total transfered money
	 */
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
	 * Compute the total amount of money withdrawn in the time of program execution and which 
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

	/**
	 * Extract all the statistics for the bank session from the log
	 * @return statistics text
	 */
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

	/**
	 * Extract all the statistics for the bank session from the log organised by customers
	 * @return
	 */
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

	/**
	 * Get statistics for a customer from the current bank session
	 * @param firstOccurence position in log for the first customer occurence
	 * @return summary of customer transactions
	 */
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

	/**
	 * Supply the number of entrances in the queue for a customer in a bank session
	 * @param id customer id
	 * @return number of entrances in the queue
	 */
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

	/**
	 * Supply the total amount of money deposit by a specific customer
	 * @param id customer id
	 * @return total deposited money
	 */
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

	/**
	 * Supply the total amount of money withdraw by a specific customer
	 * @param id customer id
	 * @return total withdraw money
	 */
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

	/**
	 * Supply the total amount of money transfered by a specific customer
	 * @param id customer id
	 * @return total transfered money
	 */
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

	/**
	 * Provides the summary created ordered by the teller
	 * @return summary text
	 */
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

	/**
	 * The purpose of this method is to return the total number of unique customers 
	 * served in the program execution time. A customer can be served for just a queue number,
	 * but he can enter several times in the queue.
	 * @return number of served customers
	 */
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

	/**
	 * Get number of customers served by a teller
	 * @param id teller id
	 * @return number of served customers
	 */
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

	/**
	 * Supply the total amount of money deposit by customers served by a teller
	 * @param id teller id
	 * @return total deposited money
	 */
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

	/**
	 * Supply the total amount of money withdraw by customers served by a teller
	 * @param id teller id
	 * @return total withdraw money
	 */
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

	/**
	 * Supply the total amount of money transfered by customers served by a teller
	 * @param id teller id
	 * @return total transfered money
	 */
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

	/**
	 * Creates a summary of the entire bank session by tellers and by customers
	 * @return summary text
	 */
	public String getSummary() {
		String result;
		result=tellersSummary();
		result+=customersSummary();

		return result;
	}
}
