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
	public static final String MESSAGE = "Message";

	public static final String EXITQUEUE = "exit queue";
	public static final String STARTTRANSACTION = "start transaction";
	public static final String EXITBANK = "left bank";
	
	private int queueNumber;
	private String transactionType;
	private Customer customer;
	private int accountID;
	private int foreignAccountID;
	private int tellerID;	
	private double oldBalance;
	private double newBalance;
	private double amount;
	private String status;
	private String message;
	
	/**
	 * The constructor for the case we don't have any information about the event
	 */
	public LogEvent() {
		status="";
		message="";
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
	public LogEvent(int queueNumber, int tellerID, Customer customer, Transaction transaction, String status, String message){
		this.queueNumber=queueNumber;
		this.customer=customer;
		this.accountID=transaction.getAccount().getId();
		if(transaction.getForeignAccount()==null)
			this.foreignAccountID=-1;
		else
			this.foreignAccountID=transaction.getForeignAccount().getId();
		this.transactionType=transaction.getType();
		this.tellerID=tellerID;
		this.status=status;
		this.amount=transaction.getAmount();
		this.newBalance=transaction.getAccount().getBalance();
		this.message=message;
		
		if(this.status.equals(FAIL)) {
			this.oldBalance=this.newBalance;
		}
		else 
			if(this.transactionType.equals(Transaction.DEPOSIT) || this.transactionType.equals(Transaction.OPEN))
				this.oldBalance=this.newBalance-transaction.getAmount();
			else
				this.oldBalance=this.newBalance+transaction.getAmount();
	}
	/*
	 * NEW for entering queue
	 */
	public LogEvent(int queueNumber, Customer customer, String status){
		this.queueNumber=queueNumber;
		this.customer=customer;
		this.accountID=-1;
		this.foreignAccountID=-1;
		this.transactionType="";
		this.tellerID=-1;
		this.status=status;
		this.amount=0;
		this.newBalance=-1;
		this.oldBalance=-1;
		this.message="";
	}
	/*
	 * NEW for statistics
	 */
	public LogEvent(String status, String message){
		this.customer = null;
		this.tellerID = -1;
		this.status=status;
		this.message=message;
	}
	/*
	 * NEW for exit queue
	 */
	public LogEvent(int queueNumber, int tellerID, Customer customer, String status) {
		this.queueNumber=queueNumber;
		this.customer=customer;
		this.accountID=-1;
		this.foreignAccountID=-1;
		this.transactionType="";
		this.tellerID=tellerID;
		this.status=status;
		this.amount=0;
		this.newBalance=-1;
		this.oldBalance=-1;
		this.message="";
	}
	/*
	 * NEW for starting transaction
	 */
	public LogEvent(int queueNumber, int tellerID, Customer customer, Transaction transaction, String status) {
		this.queueNumber=queueNumber;
		this.customer=customer;
		if(transaction.getAccount() == null)
			this.accountID=-1;
		else
			this.accountID=transaction.getAccount().getId();
		if(transaction.getForeignAccount() == null)
			this.foreignAccountID=-1;
		else
			this.foreignAccountID=transaction.getForeignAccount().getId();
		this.transactionType=transaction.getType();
		this.tellerID=tellerID;
		this.status=status;
		this.amount=transaction.getAmount();
		this.newBalance=-1;
		if(transaction.getAccount() == null)
			this.oldBalance=-1;
		else
			this.oldBalance=transaction.getAccount().getBalance();
		this.message="";
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
		if(this.customer!=null)
			return this.customer.getId();
		else
			return -1;
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
		return this.amount;
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
		if(status.equals(EXITQUEUE))
			result+=getExitQueue();
		if(status.equals(ENTERQUEUE))
			result+=getEnterQueue();
		if(status.equals(STARTTRANSACTION))
			result+=getStartTransaction();
		if(status.equals(SUCCESS))
			result+=getSuccess();
		if(status.equals(FAIL)) 
			result+=getFail();
		if(status.equals(EXITBANK)) 
			result+=getExitBank();
		if(status.equals(MESSAGE))
			result+=getMessage();

		return result;
	}
	
	/*
	 * NEW
	 */
	public String getMessage() {
		return message;
	}

	private String getSuccessDetails() {
		String result="";
		if(transactionType.equals(Transaction.DEPOSIT) 
				|| transactionType.equals(Transaction.DEPOSITFOREIGNACCOUNT)
				|| transactionType.equals(Transaction.WITHDRAWAL))
			result=" - New Balance: £" + newBalance + " from Old Balance: £" + oldBalance;
		else
			if(transactionType.equals(Transaction.CLOSE))
				result=" - Old Balance: £" + oldBalance + " with Final Withdrawed Sum: £" + amount;
			else
				if(transactionType.equals(Transaction.VIEWBALANCE))
					result=" - Balance: £" + newBalance;
				else
					if(transactionType.equals(Transaction.OPEN))
						result=" - New Account ID: " + accountID + " Balance: £" + newBalance;
					else
						if(transactionType.equals(Transaction.TRANSFER))
							result=" - Customer account New Balance: £" + newBalance + " from Old Balance: £" + oldBalance;
		
		return result;
	}	
	/*
	 * NEW
	 */
	private String getEnterQueue() {
		String result="";
		result="Customer " + customer.getId() + " (" + customer.getFirstName() +
			" " + customer.getLastName() + ") " + ENTERQUEUE + " on position " + queueNumber;
		return result;
	}
	/*
	 * NEW
	 */
	private String getExitQueue() {
		String result="";
		result="Teller " + tellerID + " receive customer " + customer.getId() +
			" (" + customer.getFirstName() + " " + customer.getLastName() + ")" +
			"with queue number " + queueNumber + " to serve";
		return result;
	}
	/*
	 * NEW
	 */
	private String getStartTransaction() {
		String result;
		result = "Teller " + tellerID + " serves customer " + customer.getId() + " with transaction: ";
		if(transactionType.equals(Transaction.OPEN))
			result+= transactionType + " account with initial amount: £" + amount;
		else
			if(transactionType.equals(Transaction.CLOSE))
				result+= transactionType + " account number " + accountID;
			else
				if(transactionType.equals(Transaction.DEPOSIT))
					result+= transactionType + " to account " + accountID + " £" + amount;
				else
					if(transactionType.equals(Transaction.VIEWBALANCE))
						result+= transactionType + " for account " + accountID;
					else
						if(transactionType.equals(Transaction.WITHDRAWAL))
							result+= transactionType + " from account " + accountID + " £" + amount;
						else
							if(transactionType.equals(Transaction.DEPOSITFOREIGNACCOUNT))
								result+= transactionType + " " + accountID + " £" + amount;
							else
								if(transactionType.equals(Transaction.TRANSFER))
									result+= transactionType + " " + accountID + " from account " + accountID + " £" + amount;
		return result;
	}
	/*
	 * NEW
	 */
	private String getSuccess() {
		String result;
		result = "Teller " + tellerID + " SUCCESSFULLY completes the " + transactionType + " for customer " + customer.getId()+"\n";
		result += getSuccessDetails();
		return result;
	}
	/*
	 * NEW
	 */
	private String getFail() {
		String result;
		result = "Teller " + tellerID + " FAIL completing the " + transactionType + " for customer " + customer.getId()+"\n";
		result += " - " + getMessage();
		return result;
	}
	
	/*
	 * NEW
	 */
	private String getExitBank() {
		String result="";
		result="Customer " + customer.getFirstName() + " " + customer.getLastName()+
			" (ID: " + customer.getId() + ") left bank";
		return result;
	}
	
	public String toStringQueue() {
		String result;
		result = queueNumber + ". " + customer.getFirstName() + " " + customer.getLastName() + " (ID: " + customer.getId() + ")";
		return result;
	}
	
}
