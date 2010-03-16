package asePackage;

/**
 * The logEvent class contains details about one event that occurred in the 
 * bank running time. It will contain information about the customer, the transaction,
 * queue number and about the modified account. It also contains a status variable which
 * say if the transaction was successful and if not what was the error.
 * @author Ioan
 */
public class LogEvent {
	public static final String SUCCESS = "Success";
	public static final String FAIL = "Fail";
	public static final String ENTERQUEUE = "joins queue";
	private int queueNumber;
	private String transactionType;
	private Customer customer;
	private int accountID;
	private int tellerID;	
	private double oldBalance;
	private double newBalance;
	private double ammount;
	private String status;
	private String errorMessage;
	
	/**
	 * The constructor for the case we don't have any information about the event
	 */
	public LogEvent() {
		status="";
		errorMessage="";
	}
	/**
	 * The constructor for the case in which we have receive
	 * a complete set of information from the teller. It will be used for entering
	 * details about the transaction and about it's status (Success/Fail).
	 * @param queueNumber queue number of the customer
	 * @param customer instance of the customer who will do the transaction
	 * @param transaction all the data about the transaction (contains the account and money amount for the transaction)
	 * @param status indicates if the transaction was successful of failed
	 * @param errorMessage stores the message for the case of error
	 */
	public LogEvent(int queueNumber, Customer customer, Transaction transaction, String status, String errorMessage){
		this.queueNumber=queueNumber;
		this.customer=customer;
		this.accountID=transaction.getAccount().getId();
		this.transactionType=transaction.getType();
		this.tellerID=1;
		this.status=status;
		this.ammount=transaction.getAmmount();
		this.newBalance=transaction.getAccount().getBalance();
		this.errorMessage=errorMessage;
		
		if(this.status.equals(FAIL)) {
			this.oldBalance=this.newBalance;
		}
		else 
			if(this.transactionType.equals(Transaction.DEPOSIT) || this.transactionType.equals(Transaction.OPEN))
				this.oldBalance=this.newBalance-transaction.getAmmount();
			else
				this.oldBalance=this.newBalance+transaction.getAmmount();
	}
	/**
	 * The constructor for the case in which we have receive
	 * a normal set of information from the teller
	 * @param queueNumber the queue number of the customer
	 * @param customer the instance of the customer who will do the transaction
	 * @param status indicates that the customer has enter the queue or if the transaction
	 * was successful or not
	 */
	public LogEvent(int queueNumber, Customer customer, String status){
		this.queueNumber=queueNumber;
		this.customer=customer;
		this.accountID=-1;
		this.transactionType="";
		this.tellerID=1;
		this.status=status;
		this.ammount=0;
		this.newBalance=-1;
		this.oldBalance=-1;
		this.errorMessage="";
	}
	
	/**
	 * Provides the queue number of the customer that requested
	 * the current transaction.
	 * @return the queue number
	 */
	public int getQueueNumber() {
		return this.queueNumber;
	}
	/**
	 * Provides the type of the transaction from the current event.
	 * @return transaction type
	 */
	public String getTransactionType() {
		return this.transactionType;
	}
	/**
	 * Provides the id of the customer who triggered the event.
	 * @return customer id
	 */
	public int getCustomerID() {
		return this.customer.getId();
	}
	/**
	 * Provides the id of the account used in the current transaction.
	 * @return account id
	 */
	public int getAccountID() {
		return this.accountID;
	}
	/**
	 * Provides the id of the teller who resolved the request for the customer.
	 * @return teller id
	 */
	public int getTellerID() {
		return this.tellerID;
	}
	/**
	 * Provides the balance before any changes have been made in the current transaction.
	 * @return initial balance
	 */
	public double getOldBalance() {
		return this.oldBalance;
	}
	/**
	 * Provides the balance after the changes have been made in the current transaction.
	 * @return final balance
	 */
	public double getNewBalance() {
		return this.newBalance;
	}
	/**
	 * Provides the sum of money used in the current transaction. Is not depending on the transaction type.
	 * @return money amount
	 */
	public double getTransactionSum(){
		return this.ammount;
	}
	/**
	 * Provides the status of the transaction from the event or if the current event is the entering of the customer in the queue.
	 * @return event status
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * It will return all the details about the current event. The format of the result is depending on the type of event on which we saved the informations.
	 * @return event description
	 */
	@Override
	public String toString() {
		String result="";
		if(status.equals(ENTERQUEUE)) {
			result=getEnterQueue();
		}
		else {
			if(status.equals(SUCCESS) || status.equals(FAIL)) {
				result=getCustomerDetails();
				result+=getTransactionDetails();
				result+="\n  - Status: " + status;
				if(status.equals(SUCCESS))
					result+=getAccountDetails();
				if(status.equals(FAIL))
					result+=getErrorMessage();
			}
		}
		return result;
	}
	
	private String getTransactionDetails() {
		String result;
		result="\n  - Transaction: ";
		if(transactionType.equals(Transaction.OPEN))
			result+= transactionType + " account with initial amount: £" + ammount;
		else
			if(transactionType.equals(Transaction.CLOSE))
				result+= transactionType + " account number " + accountID;
			else
				if(transactionType.equals(Transaction.DEPOSIT))
					result+= transactionType + " to account " + accountID + " £" + ammount;
				else
					if(transactionType.equals(Transaction.VIEWBALANCE))
						result+= transactionType + " for account " + accountID;
					else
						result+= transactionType + " from account " + accountID + " £" + ammount;
		return result;
	}
	private String getCustomerDetails() {
		String result;
		result="Teller serves customer " + customer.getId() +
				" (" + customer.getFirstName() + ", " + customer.getLastName() + ")" +
				"with queue number " + queueNumber;
		
		return result;
	}
	private String getAccountDetails() {
		String result="";
		if(transactionType.equals(Transaction.DEPOSIT) 
				|| transactionType.equals(Transaction.WITHDRAWAL))
			result=" (New Balance: £" + newBalance + " from Old Balance: £" + oldBalance + ")";
		else
			if(transactionType.equals(Transaction.CLOSE))
				result=" (Old Balance: £" + oldBalance + " with Final Withdrawed Sum: £" + ammount + ")";
			else
				if(transactionType.equals(Transaction.VIEWBALANCE))
					result=" (Balance: £" + newBalance + ")";
				else
					result=" (New Account ID: " + accountID + " Balance: £" + newBalance + ")";
		
		return result;
	}	
	private String getEnterQueue() {
		String result="";

		result="Customer " + customer.getId() + " (" + customer.getFirstName() +
				", " + customer.getLastName() + ") " + ENTERQUEUE + " on position " + queueNumber;
		
		return result;
	}
	private String getErrorMessage() {
		String result="";
		result="(" + errorMessage + ")";
		return result;
	}
}
