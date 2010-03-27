
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
		"withdrawal","deposit","viewBalance"};
	public static final String OPEN = TRANSACTIONTYPE[0];
	public static final String CLOSE = TRANSACTIONTYPE[1];
	public static final String WITHDRAWAL = TRANSACTIONTYPE[2];
	public static final String DEPOSIT = TRANSACTIONTYPE[3];
	public static final String VIEWBALANCE = TRANSACTIONTYPE[4];
	private String transactionType;
	private Account account;
	private double amount;
	/**
	 * @param transactionType 
	 * @param account The account which the transactions will take place.
	 * @param amount The amount of money is going to be transacted.
	 * @throws exception if the transactionType is not a valid string.
	 */
	public Transaction(String transactionType, Account account, double amount)throws Exception{
		if (!isValidTransaction(transactionType,account, amount))throw new Exception("The type " + transactionType + 
				"with the account ^^ " + "and amount of" + amount + "is notApplicable" );
		this.transactionType = transactionType;
		this.account = account;
		this.amount = amount;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public static Transaction generateRandomTransaction(Account account,Random rnd, boolean owner){
		int randomInt = rnd.nextInt(12);
		String transactionType =new String();
		double amount = 0;
		if(owner==true) {
			if(randomInt==0 || account==null){
				transactionType = Transaction.OPEN;
				amount = rnd.nextInt(1000);
			}
			else if(randomInt==1){
				transactionType = Transaction.CLOSE;
			}
			else if(randomInt >= 2 && randomInt < 6){
				transactionType = Transaction.DEPOSIT;
				amount = rnd.nextInt(1000);
			}
			else if(randomInt >= 6 && randomInt <10){
				transactionType = Transaction.WITHDRAWAL;
				amount = rnd.nextInt(201);
			}
			else if (randomInt >=10){
				transactionType = Transaction.VIEWBALANCE;
			}
		}
		else {
			transactionType = Transaction.DEPOSIT;
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
