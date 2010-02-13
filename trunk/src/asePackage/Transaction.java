
package asePackage;

import java.util.Random;

/**
 * @author Chris
 * Contains the type of transactions that is to be made and information about the
 * amount of money they are going to be transated (in case the transaction is a 
 * deposit or withdrawal 
 *
 */
public class Transaction {
	private static final String[] TRANSACTIONTYPE = {"open", "close",
		"withdrawal","deposit"};
	public static final String OPEN = TRANSACTIONTYPE[0];
	public static final String CLOSE = TRANSACTIONTYPE[1];
	public static final String WITHDRAWAL = TRANSACTIONTYPE[2];
	public static final String DEPOSIT = TRANSACTIONTYPE[3];
	private String transactionType;
	private Account account;
	private double ammount;
	/**
	 * @param transactionType 
	 * @param account The account which the transactions will take place.
	 * @param ammount The amount of money is going to be transacted.
	 * @throws exception if the transactionType is not a valid string.
	 */
	public Transaction(String transactionType, Account account, double ammount)throws Exception{
		if (!isValidTransaction(transactionType,account, ammount))throw new Exception("The type " + transactionType + 
				"with the account ^^ " + "and amount of" + ammount + "is notApplicable" );
		this.transactionType = transactionType;
		this.account = account;
		this.ammount = ammount;
	}
	
	/**
	 * @return true if the transactionType is a valid type
	 */
	private boolean isValidTransaction(String transactionType, Account account, double ammount) {
		boolean isValid = false;
		for (String type:TRANSACTIONTYPE){
			if (type.equals(transactionType))isValid = true;
		}
		return isValid;
	}
	
	
	
	
	/// GETTERS AND SETTERS
	public String getType() {
		return transactionType;
	}
	public void setType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public double getAmmount() {
		return ammount;
	}
	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}
	
	public static Transaction generateRandomTransaction(Account account,Random rnd){
		int randomInt = rnd.nextInt(10);
		String transactionType =new String();
		double amount = 0;
		if(randomInt==0){
			transactionType = Transaction.OPEN;
			amount = rnd.nextInt(1000);
		}
		if(randomInt==1){
			transactionType = Transaction.CLOSE;
		}
		if(randomInt >= 2 && randomInt < 6){
			transactionType = Transaction.DEPOSIT;
			amount = rnd.nextInt(1000);
		}
		if(randomInt >= 6){
			transactionType = Transaction.WITHDRAWAL;
			amount = rnd.nextInt(1000);
		}
		
		Transaction trans = null;
		try {
			trans = new Transaction(transactionType, account, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return trans;
		
		
	}
	
	

}
