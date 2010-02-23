package asePackage;
import java.util.ArrayList;

/**
 * 
 * @author Chris
 *
 */
public class Teller {
	
	private QueueManager qm;
	private AccountManager accountManager;
	private Queue customerInQueue;
	private Log log;
	
	/*
	 *  holds a message about what went wrong if the transaction is not valid
	 */
	private String errorMessage;  
	
		
	public Teller(QueueManager qm, AccountManager accountManager, Log log) {
		this.qm = qm;
		this.accountManager = accountManager;
		this.log = log;
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
		}
	}
	
	private boolean viewBalance(Transaction transaction,
			Customer currentCustomer) {
		return isValidTransaction(transaction, currentCustomer);
		
	}

	private boolean closeAccount(Transaction transaction, Customer currentCustomer) {
		boolean isValidTransaction = isValidTransaction(transaction, currentCustomer);
		if(isValidTransaction){
			Account account = transaction.getAccount();
			//IOAN: I added the next field to add the info for the log regarding the sum which is withdrawed
			transaction.setAmmount(account.getBalance());
			if(account.getBalance()>0) 
				account.withdrawMoney(account.getBalance());
			accountManager.deleteAccount(account);
		}
		return isValidTransaction;
	}

	private boolean openAccount(Transaction transaction, Customer currentCustomer) {
		boolean isValidTransaction = isValidTransaction(transaction,currentCustomer);
		if(isValidTransaction){
			Account account = accountManager.addAccount(currentCustomer);
			transaction.setAccount(account);
			account.depositMoney(transaction.getAmmount());
		    
		}
		return isValidTransaction;
	}

	private boolean doWithdrawal(Transaction transaction, Customer currentCustomer) {
		boolean isValidTransaction = isValidTransaction(transaction,currentCustomer);
		if (isValidTransaction){
			transaction.getAccount().withdrawMoney(transaction.getAmmount());
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
		boolean isValidTransaction = 
			isValidTransaction(transaction,currentCustomer);
		
		if (isValidTransaction){
			Account account = transaction.getAccount();
			double money = transaction.getAmmount();
			account.depositMoney(money);
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
			//IOAN: from testing I seen that for the case in which in the first transaction an account was deleted
			//and the customer has an another transaction it will appear the not owner of the account error
			//I believe is better to have an another message for this.
			//if is ok with you and you didn't think it different
			if(transaction.getAccount().isClosed()==true){
				errorMessage = "No account found";
				isValid = false;
			}
			else if(!currentCustomer.hasAccount(transaction.getAccount())){
					errorMessage = "Not owner of the account";
					isValid = false;
				}
				else if(transaction.getAccount().getBalance()< transaction.getAmmount()){
					errorMessage = "There is not enough money";
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
		
		if(transaction.getType().equals(Transaction.DEPOSIT) || 
				transaction.getType().equals(Transaction.CLOSE) ||
				transaction.getType().equals(Transaction.VIEWBALANCE)){
			//IOAN: from testing I seen that for the case in which in the first transaction an account was deleted
			//and the customer has an another transaction it will appear the not owner of the account error
			//I believe is better to have an another message for this.
			//if is ok with you and you didn't think it different
			if(transaction.getAccount().isClosed()==true){
				errorMessage = "No account found";
				isValid = false;
			}
			else if(!currentCustomer.hasAccount(transaction.getAccount())){
					errorMessage = "Not owner of the account";
					isValid = false;
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
		if(isValidTransaction){
			log.addLogEvent(customerInQueue.getQueueNumber(), customerInQueue.getCustomer(), transaction, LogEvent.SUCCESS,errorMessage);
		}
		else{
			//TODO here you have to change the signature of the medthod (add the parameter errorMessage so you get the reason 
			//which prevented the transaction
			log.addLogEvent(customerInQueue.getQueueNumber(), customerInQueue.getCustomer(), transaction, LogEvent.FAIL,errorMessage);
			//log.addLogEvent(customerInQueue.getQueueNumber(), customerInQueue.getCustomer(), transaction, LogEvent.FAIL);
		}
		
	}
	
}


