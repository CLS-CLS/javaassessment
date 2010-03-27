package asePackage;
import java.util.ArrayList;
import java.util.Observable;

/**
 * 
 * @author Chris
 *
 */
public class Teller extends Observable implements Runnable{
	private int id;
	private QueueManager qm;
	private AccountManager accountManager;
	private Queue customerInQueue;
	private Log log;
	private static int tellerGenerationDelay;
	private boolean bankIsClosed = false;
	private static boolean bankIsPaused = false;
	
	/*
	 *  holds a message about what went wrong if the transaction is not valid
	 */
	private String errorMessage;  
	
		
	public Teller(QueueManager qm, AccountManager accountManager, Log log, int tellerID) {
		//super("T("+tellerID+")");
		this.qm = qm;
		this.accountManager = accountManager;
		this.log = log;
		this.id = tellerID;
		tellerGenerationDelay = 2000;
	}
	
	/**
	 * gets the next customer from the queue.
	 */
	public void getNextCustomer(){
		customerInQueue = qm.removeQueueElement();
    }
	
	/**
	 * Makes all the transactions that the customers wants to do if they are valid
	 */
	public void doTransaction(){
		if (customerInQueue==null)return;
		Customer currentCustomer = customerInQueue.getCustomer();
		ArrayList<Transaction> transactions = customerInQueue.getTransactionList();
		boolean isValidTransaction;
		for (Transaction transaction : transactions){
			isValidTransaction = false;
			
			if (transaction.getType().equals(Transaction.DEPOSIT)) 
				isValidTransaction = doDeposit(transaction,currentCustomer);
			
			if (transaction.getType().equals(Transaction.WITHDRAWAL))
				isValidTransaction = doWithdrawal(transaction,currentCustomer);
				
			if(transaction.getType().equals(Transaction.OPEN))
				isValidTransaction = openAccount(transaction, currentCustomer);
				
			if(transaction.getType().equals(Transaction.CLOSE)){
				isValidTransaction = closeAccount(transaction, currentCustomer);
			}
			
			if(transaction.getType().endsWith(Transaction.VIEWBALANCE)){
				isValidTransaction = viewBalance(transaction,currentCustomer);
			}
			
			generateReport(isValidTransaction,transaction);
			currentCustomer.setInsideBank(false);
		}
	}
	
	/**
	 * doesn't do anything special.
	 * @param transaction 
	 * @param currentCustomer
	 * @return true if this transaction is valid
	 */
	private boolean viewBalance(Transaction transaction,
			Customer currentCustomer) {
		return isValidTransaction(transaction, currentCustomer);
		
	}
   
	private boolean closeAccount(Transaction transaction, Customer currentCustomer) {
		boolean isValidTransaction = isValidTransaction(transaction, currentCustomer);
		if(isValidTransaction){
			Account account = transaction.getAccount();
			//sets the ammount to be withdrawed in the transaction so it can be used as
			//info for the log 
			transaction.setAmount(account.getBalance());
			//first withdraws all the money from the account
			if(account.getBalance()>0) 
				account.withdrawMoney(account.getBalance());
			accountManager.deleteAccount(account);
		}
		return isValidTransaction;
	}
   
	private boolean openAccount(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = isValidTransaction(transaction,currentCustomer);
		if(isValidTransaction){
			Account account = accountManager.addAccount(currentCustomer);
			transaction.setAccount(account);
			amount=MyUtilities.roundDouble(transaction.getAmount());
			account.depositMoney(amount);
		    
		}
		return isValidTransaction;
	}

	private boolean doWithdrawal(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = isValidTransaction(transaction,currentCustomer);
		if (isValidTransaction){
			amount=MyUtilities.roundDouble(transaction.getAmount());
			transaction.getAccount().withdrawMoney(amount);
		}
		return isValidTransaction;
	}
    
	
	/**
	 * deposits money in the an account. In order to the deposit to be complete 
	 * the transaction must be a valid one (check the comments on the isValidTransaction 
	 * for more information about valid transactions)
	 * 
	 * @param transaction The transaction to be made
	 * @param currentCustomer the customer that wants to make the transaction
	 * @return true if the transaction is done, false if the transactions is not valid.
	 */
	private boolean doDeposit(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = 
			isValidTransaction(transaction,currentCustomer);
		
		if (isValidTransaction){
			Account account = transaction.getAccount();
			amount=MyUtilities.roundDouble(transaction.getAmount());
			account.depositMoney(amount);
		}
		
		return isValidTransaction;
	}

	

	/**
	 * checks if the current transaction is valid (e.g. can be made). Valid transcations are
	 * Deposit / Close : only if the customer owns the account he want to deposit money
	 * Withdrawal : only if the customer own the account he wants to withdraw money from
	 * and also the money to be withdrawn does not exceed the money in the account
	 * Open : An account can be opened only if the customer does not have more that the maximum 
	 * account allowance.
	 * It also generates an error message in case the transaction is not valid
	 * @param transaction the transaction to be validated
	 * @return true if the transaction is valid, false otherwise
	 */
	private boolean isValidTransaction(Transaction transaction,Customer currentCustomer){
		boolean isValid = true;
		
		//in case the transaction is withdrawal
		//checks if the account belongs to the customer and has enough money
		if (transaction.getType().equals(Transaction.WITHDRAWAL)){
			
			//in case the account was closed during the bank session we give a 
			//different message
			if(transaction.getAccount().isClosed()==true){
				errorMessage = "Account is closed";
				isValid = false;
			}
			else if(!currentCustomer.hasAccount(transaction.getAccount())){
					errorMessage = "Not owner of the account";
					isValid = false;
				}
				else if(transaction.getAccount().getBalance()< transaction.getAmount() ){
					errorMessage = "There is not enough money";
					isValid = false;
					
				}
				else if (log.getCustomerWithdrawalTotal(currentCustomer)+ transaction.getAmount()> 200){
					errorMessage = "Reached total withdrawal limit ";
					isValid = false;
				}
		}
		
		//in case the transaction is to open an account checks if the customer has less than 2 
		//accounts.
		if (transaction.getType().equals(Transaction.OPEN)){
			if(currentCustomer.getNumberOfAccounts()== Customer.MAXACCOUNTS){
				isValid = false;
				errorMessage = "Customer has already "+ Customer.MAXACCOUNTS + " accounts";
			}
		}
		
		//in case the transaction is to deposit money or close the account or 
		// view balance, it checks if the customer owns the account
		
		if(transaction.getType().equals(Transaction.CLOSE) ||
				transaction.getType().equals(Transaction.VIEWBALANCE)){
			
			//in case the account was closed during the bank session we give a 
			//different message
			if(transaction.getAccount().isClosed()==true){
				errorMessage = "Account is closed";
				isValid = false;
			}
			else if(!currentCustomer.hasAccount(transaction.getAccount())){
					errorMessage = "Not owner of the account";
					isValid = false;
				}
			
		if(transaction.getType().equals(Transaction.DEPOSIT)) {
			if(transaction.getAccount().isClosed()==true){
				errorMessage = "Account is closed";
				isValid = false;
			}
		}
	}
		
		
	
		return isValid;
	}
	
	
	/**
	 * generates the log of the current transaction
	 * @param isValidTransaction 
	 * @param transaction
	 */
	private void generateReport(boolean isValidTransaction, Transaction transaction) {
		LogEvent logEvent;
		if(isValidTransaction){
			logEvent = new LogEvent(customerInQueue.getQueueNumber(), id, customerInQueue.getCustomer(), transaction, LogEvent.SUCCESS,errorMessage);
		}
		else{
			logEvent = new LogEvent(customerInQueue.getQueueNumber(), id, customerInQueue.getCustomer(), transaction, LogEvent.FAIL,errorMessage);
		}
		log.addLogEvent(logEvent);
		setChanged();
		notifyObservers(logEvent.toString());
		//notifyAll();
		
	}
	public void run(){
		while(!bankIsClosed || !qm.isQueueEmpty() ) {
			if (!bankIsPaused){
				getNextCustomer();
				doTransaction();
			}
			try {
				Thread.sleep(tellerGenerationDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}		
	

	public void setBankIsClosed(boolean bankIsClosed) {
		this.bankIsClosed = bankIsClosed;
	}

	public static void  setTellerGenerationDelay(int tellerGenerationDelay) {
		Teller.tellerGenerationDelay = tellerGenerationDelay;
	}

	public static int getTellerGenerationDelay() {
		return tellerGenerationDelay;
	}

	public static void setBankIsPaused(boolean bankIsPaused) {
		Teller.bankIsPaused = bankIsPaused;
	}

	public static boolean isBankIsPaused() {
		return bankIsPaused;
	}
	
}


