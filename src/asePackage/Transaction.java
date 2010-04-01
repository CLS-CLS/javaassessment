package asePackage;
import java.util.Random;

/**
 * @author Chris
 * 
 * Contains the type of transactions that is to be made and information about the
 * amount of money they are going to be transacted (in case the transaction is a 
 * deposit or withdrawal 
 *
 */
public class Transaction {
	private static final String[] TRANSACTIONTYPE = {"open", "close",
		"withdrawal","deposit","viewBalance","transfer to foreign account","deposit to foreign account"};
	public static final String OPEN = TRANSACTIONTYPE[0];
	public static final String CLOSE = TRANSACTIONTYPE[1];
	public static final String WITHDRAWAL = TRANSACTIONTYPE[2];
	public static final String DEPOSIT = TRANSACTIONTYPE[3];
	public static final String VIEWBALANCE = TRANSACTIONTYPE[4];
	public static final String TRANSFER = TRANSACTIONTYPE[5];
	public static final String DEPOSITFOREIGNACCOUNT = TRANSACTIONTYPE[6];
	private String transactionType;
	private Account account;
	private Account foreignAccount;
	private double amount;

	/**
	 * The constructor for a normal Transaction object. It verify the existence of a valid Transaction Type.
	 * @param transactionType 
	 * @param account The account which the transactions will take place.
	 * @param amount The amount of money is going to be transacted.
	 * @throws exception if the transactionType is not a valid string.
	 */
	public Transaction(String transactionType, Account account, double amount, Account foreignAccount)throws NotCorrectConstructorArgumentsException{
		if (!isValidTransaction(transactionType,account, amount, foreignAccount))
			throw new NotCorrectConstructorArgumentsException(new String[]{
					transactionType,Integer.toString(account.getId()),
					Integer.toString(foreignAccount.getId()),
					Double.toString(amount)} );

		this.transactionType = transactionType;
		this.account = account;
		this.foreignAccount = foreignAccount;
		this.amount = amount;
	}

	/**
	 * Tests the usage of a valid transaction type.
	 * @return true if the transactionType is a valid type
	 */
	private boolean isValidTransaction(String transactionType, Account account, double amount, Account foreignAccount) {
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
	public void setForeignAccount(Account foreignAccount) {
		this.foreignAccount = foreignAccount;
	}
	public Account getForeignAccount() {
		return foreignAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * 
	 * Generates a new transaction starting from an account and a foreign account
	 * The generations percentage are:
	 *  - 5% for open
	 *  - 5% for close
	 *  - 20% for deposit
	 *  - 20% for withdrawal
	 *  - 15% for transfer
	 *  - 15% for view balance
	 *  - 20% for transfer to foreign account
	 * @param account the account of the customer for whom to generate the transaction
	 * @param foreignAccount foreign account supply required by some transactions
	 * @param rnd number random generator
	 * @return new generated Transaction
	 * @author First version Chris; Actual version Ioan
	 */
	public static Transaction generateRandomTransaction(Account account, Account foreignAccount,Random rnd){
		int randomInt = rnd.nextInt(20)+1;
		String transactionType =new String();
		double amount = 0;
		//create a new open transaction
		if(randomInt==0 || account==null){
			transactionType = Transaction.OPEN;
			amount = rnd.nextInt(1000);
		}
		//create a new close transaction
		else if(randomInt==1){
			transactionType = Transaction.CLOSE;
		}
		//create a deposit transaction
		else if(randomInt >= 2 && randomInt < 6){
			transactionType = Transaction.DEPOSIT;
			amount = rnd.nextInt(1000);
		}
		//create a withdrawal transaction
		else if(randomInt >= 6 && randomInt <10){
			transactionType = Transaction.WITHDRAWAL;
			amount = rnd.nextInt(201);
		}
		//create a Transfer transaction between two accounts
		else if(randomInt >= 10 && randomInt < 13){
			transactionType = Transaction.TRANSFER;
			amount = rnd.nextInt(1000);
		}
		//create a view balance transaction
		else if(randomInt >= 13 && randomInt < 16){
			transactionType = Transaction.VIEWBALANCE;
		}
		//create a deposit to foreign account
		else if(randomInt >= 16){
			transactionType = Transaction.DEPOSITFOREIGNACCOUNT;
			amount = rnd.nextInt(1000);
		}

		Transaction trans = null;
		try {
			trans = new Transaction(transactionType, account, amount, foreignAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return trans;		
	}
}
