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
			if (transaction.getType().equals(Transaction.DEPOSIT)){
				Account account = transaction.getAccount();
				account.depositMoney(transaction.getAmmount());
				//System.out.println("deposit done");
				isValidTransaction = true;
				
			}
			if (transaction.getType().equals(Transaction.WITHDRAWAL)){
				if (isValidTransaction(transaction,currentCustomer)){
					transaction.getAccount().withdrawMoney(transaction.getAmmount());
					//System.out.println("withdrawal succeeded");
					isValidTransaction = true;
				}//else System.out.println("withdrawal failed");
			}
			if(transaction.getType().equals(Transaction.OPEN)){
				if(isValidTransaction(transaction,currentCustomer)){
					Account account = accountManager.addAccount(currentCustomer);
					account.depositMoney(transaction.getAmmount());
					//System.out.println("open succeded");
					isValidTransaction = true;
				}//else System.out.println("opened failed");
			}
			if(transaction.getType().equals(Transaction.CLOSE)){
				if(isValidTransaction(transaction,currentCustomer)){
					Account account = transaction.getAccount();
					if(account.getBalance()>0) {
						Transaction trans = null;
						try {
							trans = new Transaction(Transaction.WITHDRAWAL, account, account.getBalance());
						} catch (Exception e) {
							e.printStackTrace();
						}
						account.withdrawMoney(account.getBalance());
						generateReport(true, trans);
					}
					accountManager.deleteAccount(account);
					
					isValidTransaction = true;
				}
			}
			generateReport(isValidTransaction,transaction);
		}
	}
	
	/**
	 * checks if the current transaction is valid (e.g. can be made). Valid trascations are
	 * Deposit / Close : only if the customer owns the account he want to deposit money
	 * Withdrawal : only if the customer own the account he wants to withdraw money from
	 * and also the money to be withdrawn does not exceed the money in the account
	 * Open : An account can be opened only if the customer does not have more that the maximum 
	 * account allowance
	 * @param transaction the transaction to be validated
	 * @return true if the transaction is valid, false otherwise
	 */
	private boolean isValidTransaction(Transaction transaction,Customer currentCustomer){
		boolean isValid = true;
		
		//in case the transaction is withdrawal
		//checks if the account belongs to the customer and has enough money
		if (transaction.getType().equals(Transaction.WITHDRAWAL)){
				if(!currentCustomer.hasAccount(transaction.getAccount())||
				transaction.getAccount().getBalance()< transaction.getAmmount())
			isValid = false;
		}
		
		//in case the transaction is to open an account checks if the customer has less than 2 
		//accounts.
		if (transaction.getType().equals(Transaction.OPEN)){
			if(currentCustomer.getNumberOfAccounts()== Customer.MAXACCOUNTS)
				isValid = false;
		}
		
		//in case the transaction is to deposit money or close the account
		//it checks if the customer owns 
		
		if(transaction.getType().equals(Transaction.DEPOSIT) || 
				transaction.getType().equals(Transaction.CLOSE)){
			if(!currentCustomer.hasAccount(transaction.getAccount()))isValid = false;
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
			log.addLogEvent(customerInQueue.getQueueNumber(), customerInQueue.getCustomer(), transaction, LogEvent.SUCCESS);
		}
		else{
			log.addLogEvent(customerInQueue.getQueueNumber(), customerInQueue.getCustomer(), transaction, LogEvent.FAIL);
		}
		
	}
	
}


