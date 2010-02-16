package asePackage;

import java.util.ArrayList;

public class Teller {
	
	private QueueManager qm;
	private ArrayList<Transaction> transactions;
	private AccountManager accountManager;
	private Queue customerInQueue;
	
		
	public Teller(QueueManager qm,AccountManager accountManager) {
		this.qm = qm;
		this.accountManager = accountManager;
	}
	
	public void getNextCustomer(){
		customerInQueue = qm.removeQueueElement();
		transactions = customerInQueue.getTransactionList();
	}
	
	/**
	 * Makes all the transactions that the customers wants to do if they are valid
	 */
	public void doTransaction(){
		Customer currentCustomer = customerInQueue.getCustomer();
		boolean isValidTransaction;
		
		for (Transaction transaction : transactions){
			isValidTransaction = false;
			if (transaction.getType().equals(Transaction.DEPOSIT)){
				Account account = transaction.getAccount();
				account.depositMoney(transaction.getAmmount());
				System.out.println("deposit done");
				isValidTransaction = true;
				
			}
			if (transaction.getType().equals(Transaction.WITHDRAWAL)){
				if (isValidTransaction(transaction,currentCustomer)){
					transaction.getAccount().withdrawMoney(transaction.getAmmount());
					System.out.println("withdrawal succeeded");
					isValidTransaction = true;
				}else System.out.println("withdrawal failed");
			}
			if(transaction.getType().equals(Transaction.OPEN)){
				if(isValidTransaction(transaction,currentCustomer)){
					Account account = accountManager.addAccount(currentCustomer);
					account.depositMoney(transaction.getAmmount());
					System.out.println("open succeded");
					isValidTransaction = true;
				}else System.out.println("opened failed");
			}
			if(transaction.getType().equals(Transaction.CLOSE)){
				if(isValidTransaction(transaction,currentCustomer)){
					Account account = transaction.getAccount();
					account.withdrawMoney(account.getBalance());
					accountManager.deleteAccount(account);
					System.out.println("close succeded");
					isValidTransaction = true;
				}else System.out.println("close failed");
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
		
		if(transaction.getType().equals(Transaction.DEPOSIT) || 
				transaction.getType().equals(Transaction.CLOSE)){
			if(!currentCustomer.hasAccount(transaction.getAccount()))isValid = false;
		}
	
		return isValid;
	}

	private void generateReport(boolean isValidTransaction, Transaction transaction) {
		if(isValidTransaction){
			//TODO *****FOR IOAN****   call constructor of LOG class for succeeded transaction
			//available fields are customerinQueue
			//available local fields isValidTransaction, transaction
		}
		else{
			//TODO call constructor of LOG class for failed transaction
		}
		
	}
	
}


