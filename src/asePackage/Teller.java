package asePackage;

import java.util.ArrayList;

public class Teller {
	
	private QueueManager qm;
	private Customer currentCustomer;
	private ArrayList<Transaction> transactions;
	private AccountManager accountManager;
	
	/*
	 * Ioan: A field like tellerID will be very useful for the log file
	 */
	
	
	public Teller(QueueManager qm,AccountManager accountManager) {
		this.qm = qm;
		this.accountManager = accountManager;
	}
	
	public void getNextCustomer(){
		Queue customerInQueue = qm.removeQueueElement();
		currentCustomer = customerInQueue.getCustomer();
		transactions = customerInQueue.getTransactionList();
	}
	
	/**
	 * Makes all the transactions that the customers wants to do if they are valid
	 */
	public void doTransaction(){
		/** 
		 * 
		 * 
		 * somewhere here (in the if statements or maybe after there must be a report generations
		 * 
		 * 
		 * 
		 * 
		 */
		for (Transaction transaction : transactions){
			if (transaction.getType().equals(Transaction.DEPOSIT)){
				Account account = transaction.getAccount();
				account.depositMoney(transaction.getAmmount());
				System.out.println("deposit done");
			}
			if (transaction.getType().equals(Transaction.WITHDRAWAL)){
				if (isValidTransaction(transaction)){
					transaction.getAccount().withdrawMoney(transaction.getAmmount());
					System.out.println("withdrawal succeeded");
				}else System.out.println("withdrawal failed");
			}
			if(transaction.getType().equals(Transaction.OPEN)){
				if(isValidTransaction(transaction)){
					Account account = accountManager.addAccount(currentCustomer);
					account.depositMoney(transaction.getAmmount());
					System.out.println("open succeded");
				}else System.out.println("opened failed");
			}
			if(transaction.getType().equals(Transaction.CLOSE)){
				if(isValidTransaction(transaction)){
					Account account = transaction.getAccount();
					account.withdrawMoney(account.getBalance());
					accountManager.deleteAccount(account);
					System.out.println("close succeded");
				}else System.out.println("close failed");
			}
		}
		
		//TODO call the generate report?
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
	private boolean isValidTransaction(Transaction transaction){
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

	private void generateReport() {
		// TODO Auto-generated method stub
		
	}
	
}


