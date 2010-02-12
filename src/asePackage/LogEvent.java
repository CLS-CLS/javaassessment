package asePackage;

/**
 * @author Ioan
 *
 */
public class LogEvent {
	private int queueNumber;
	private String transactionType;
	private int customerID;
	private int accountID;
	private int tellerID;	
	private double oldBalance;
	private double newBalance;
	
	public LogEvent() {
	}
	public LogEvent(int queueNumber, String transactionType, int customerID, int accountID, int tellerID, double oldBalance, double newBalance) {
		this.queueNumber=queueNumber;
		this.transactionType=transactionType;
		this.customerID=customerID;
		this.accountID=accountID;
		this.tellerID=tellerID;
		this.oldBalance=oldBalance;
		this.newBalance=newBalance;
	}
	public int getQueueNumber() {
		return this.queueNumber;
	}
	public String getTransactionType() {
		return this.transactionType;
	}
	public int getCustomerID() {
		return this.customerID;
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
		return Math.abs(this.newBalance-this.oldBalance);
	}

	@Override
	public String toString() {
		return "[QueueNo.: " + queueNumber + "][Customer: " + customerID
				+ ", Account: " + accountID + ", Transaction: "
				+ transactionType + ", Sum: " + Math.abs(newBalance-oldBalance) 
				+ "][tellerID=" + tellerID + "]";
	}
	
}
