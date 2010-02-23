package asePackage;

/**
 * @author Ioan
 *
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
	
	public LogEvent() {
	}
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
		else {
			if(this.transactionType.equals(Transaction.DEPOSIT) || this.transactionType.equals(Transaction.OPEN))
				this.oldBalance=this.newBalance-transaction.getAmmount();
			else
				this.oldBalance=this.newBalance+transaction.getAmmount();
		}
	}
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
	/*
	public LogEvent(int queueNumber, String transactionType, int customerID, int accountID, int tellerID, double oldBalance, double newBalance) {
		this.queueNumber=queueNumber;
		this.transactionType=transactionType;
		this.customerID=customerID;
		this.accountID=accountID;
		this.tellerID=tellerID;
		this.oldBalance=oldBalance;
		this.newBalance=newBalance;
	}
	*/
	public int getQueueNumber() {
		return this.queueNumber;
	}
	public String getTransactionType() {
		return this.transactionType;
	}
	public int getCustomerID() {
		return this.customer.getId();
	}
	public int getAccountID() {
		return this.accountID;
	}
	public int getTellerID() {
		return this.tellerID;
	}
	public double getOldBalance() {
		return this.oldBalance;
	}
	public double getNewBalance() {
		return this.newBalance;
	}
	public double getTransactionSum(){
		return this.ammount;
	}

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
			result+= transactionType + " account number " + accountID + " with initial amount: $" + ammount;
		else
			if(transactionType.equals(Transaction.CLOSE))
				result+= transactionType + " account number " + accountID;
			else
				if(transactionType.equals(Transaction.DEPOSIT))
					result+= transactionType + " to account " + accountID + " $" + ammount;
				else
					result+= transactionType + " from account " + accountID + " $" + ammount;
		
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
				|| transactionType.equals(Transaction.WITHDRAWAL) 
				|| transactionType.equals(Transaction.CLOSE)) {
			result=" (New Balance: $" + newBalance + " from Old Balance: $" + oldBalance + ")";
		}
		
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
