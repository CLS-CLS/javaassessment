package asePackage;

import java.util.ArrayList;

public class Teller {
	
	QueueManager qm;
	Customer currentCustomer;
	ArrayList<Transaction> transactions;
	String report;
	/*
	 * Ioan: A field like tellerID will be very useful for the log file
	 */
	
	
	public Teller(QueueManager qm) {
		this.qm = qm;
	}
	
	public void getNextCustomer(){
		Queue customerInQueue = qm.removeQueueElement();
		currentCustomer = customerInQueue.getCustomer();
		transactions = customerInQueue.getTransactionList();
	}
	
	public void doTransaction(){
		for (Transaction transaction : transactions){
			if (transaction.getType().equals(Transaction.DEPOSIT)){
				Account account = transaction.getAccount();
				account.depositMoney(transaction.getAmmount());
				System.out.println("deposit done");
				generateReport();
				
			}
			if (transaction.getType().equals(Transaction.WITHDRAWAL)){
				if (isValidTransaction(transaction)){
					transaction.getAccount().withdrawMoney(transaction.getAmmount());
				}else System.out.println("withdrawal failed");
					
			}
			
				
		}
	}

	private boolean isValidTransaction(Transaction transaction){

		boolean isValid = true;
		//check if the account belongs to the customer and has enough money
		if (!currentCustomer.hasAccount(transaction.getAccount())||
				transaction.getAccount().getBalance()< transaction.getAmmount())
			isValid = false;
			
		return isValid;
	}

	private void generateReport() {
		// TODO Auto-generated method stub
		
	}
	
}


