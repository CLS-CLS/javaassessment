package asePackage;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Chris
 *
 */
public class Bank {
	/*
	 * used to generate random numbers needed for creating random transactions
	 * and pick random customers
	 */
    private Random rndGen;
    
    /*
	 * holds information about the customers in the queue
	 */
	private QueueManager qm;
	private Teller teller;
	private ArrayList<Customer> customers;
	
	/*
	 * holds the log of the bank
	 */
	private static Log log;
	
	/*
	 * manipulates the accounts
	 */
	private AccountManager am;
	
	
	public Bank(){
		rndGen = new Random();
		log = new Log();
		qm = new QueueManager();
		customers = new ArrayList<Customer>();
		ArrayList<Account> accounts = new ArrayList<Account>();
		am = new AccountManager();
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
		//proofOfAccurateTransactions should be set as true for full testing of//
		//transactions                                                         //
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
		boolean proofOfAccurateTransactions=true;
		
		if(proofOfAccurateTransactions==true) {
			proofOfAccurateTransactions();
		}
		else {
			//loads customers and accounts and creates connects the accounts
			//with the customers
			try{
				customers = MyUtilities.loadCustomers("customers.txt");
				accounts = MyUtilities.loadAccounts("accounts.txt",customers);
				am.addAcounts(accounts);   //adds the account to the account manager
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			//Add the accounts to the customer (connects the customer to the accounts)
			for (Account aca: am.getAccountList()){
				for(Customer customer: aca.getOwnerList()){
					customer.
					addAccount(aca);
				}
			}
			
			//pick some random customers
			ArrayList<Customer> selectedCustomers = pickRandomCustomers();
			int currentQueueNumber=0;
			//add customers to the queue with a transaction
			for (Customer customer:selectedCustomers){
				qm.addQueueElement(customer,generateTransactions(customer));
				//add a log event to the log about the customer that has been added
				currentQueueNumber=qm.getNextNumber()-1;
				log.addLogEvent(currentQueueNumber, customer, LogEvent.ENTERQUEUE);
			}
			
			teller = new Teller(qm,am,log);
		}
	}
	
	//generates a predefined set of transactions for 
	//proof of Accurate Transactions
	private void proofOfAccurateTransactions(){
		ArrayList<Account> accounts;
		//loads customers and accounts and creates connects the accounts
		//with the customers
		try{
			customers = MyUtilities.loadCustomers("customers_proof.txt");
			accounts = MyUtilities.loadAccounts("accounts_proof.txt",customers);
			am.addAcounts(accounts);   //adds the account to the account manager
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		//Add the accounts to the customer (connects the customer to the accounts)
		for (Account aca: am.getAccountList()){
			for(Customer customer: aca.getOwnerList()){
				customer.
				addAccount(aca);
			}
		}

		
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		log.addLogEvent(qm.getNextNumber(), customers.get(0), LogEvent.ENTERQUEUE);
		log.addLogEvent(qm.getNextNumber()+1, customers.get(2), LogEvent.ENTERQUEUE);
		log.addLogEvent(qm.getNextNumber()+2,customers.get(3),LogEvent.ENTERQUEUE);
		log.addLogEvent(qm.getNextNumber()+3, customers.get(1), LogEvent.ENTERQUEUE);
		
		try {
			trans.add(new Transaction(Transaction.OPEN, new Account(), 200));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(0).getAccountList().get(0),100));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(0).getAccountList().get(1),150));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		qm.addQueueElement(customers.get(0), trans);
		
		trans=new ArrayList<Transaction>();
		try {
			trans.add(new Transaction(Transaction.OPEN, new Account(),500));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		qm.addQueueElement(customers.get(2), trans);
		
		trans=new ArrayList<Transaction>();
		try {
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),100));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),50));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.DEPOSIT, customers.get(3).getAccountList().get(0),250));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.VIEWBALANCE, customers.get(3).getAccountList().get(0),0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),160));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.OPEN, new Account(),600));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.CLOSE, customers.get(3).getAccountList().get(0),0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),10));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.DEPOSIT, customers.get(3).getAccountList().get(0),900));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.VIEWBALANCE, customers.get(3).getAccountList().get(0),0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			trans.add(new Transaction(Transaction.CLOSE, customers.get(3).getAccountList().get(0),0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		qm.addQueueElement(customers.get(3), trans);
		trans=new ArrayList<Transaction>();
		try {
			trans.add(new Transaction(Transaction.DEPOSIT, am.getAccountList().get(1),50));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		qm.addQueueElement(customers.get(1), trans);
		
		teller = new Teller(qm,am,log);	
	}
	
	/**
	 * generates random transactions for the given customer
	 * The transaction may be 1 or 2
	 * @param customer the customer who will get a random transaction
	 * @return an arraylist of the transactions generated for the given customer
	 */
	private ArrayList<Transaction> generateTransactions(Customer customer) {
		int numberOfTransactions = rndGen.nextInt(2)+1;
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		for (int i = 0;i < numberOfTransactions;i++){
			Account aca = selectRandomAca(customer);
			trans.add(Transaction.generateRandomTransaction(aca, rndGen));
		}
		return trans;
	}

	/**
	 * selects a random account from the accounts that a customer has
	 * @param customer
	 * @return a random account, null if the customer has no accounts
	 */
	private Account selectRandomAca(Customer customer) {
		Account account = null;
		ArrayList<Account> accounts = customer.getAccountList();
		if(accounts.size()>0){
			account =  accounts.get(
					rndGen.nextInt(customer.getNumberOfAccounts()));
		}
		return account;
	}
	
	/**
	 * picks 10 distinct random customers from the list of the customers
	 * @return an array list of 10 random customers
	 */
	private ArrayList<Customer> pickRandomCustomers() {
		ArrayList<Customer> subList = new ArrayList<Customer>();
		while(subList.size()<10 && subList.size()<customers.size()){
			int rnd = rndGen.nextInt(customers.size());
			Customer customer = customers.get(rnd);
			if (!subList.contains(customer))subList.add(customer);
		}
		
		return subList;
		
	}
	
	
	public String getLog() {
		return log.toString();
	}
	
	
	public void runBank(){
		while(!qm.isQueueEmpty()){
			teller.getNextCustomer();
			teller.doTransaction();
		}
	
		MyUtilities.saveStringToFile(log.toString(), "log.txt");
		MyUtilities.saveCustomersToFile(customers, "newCustomers.txt");
		MyUtilities.saveAccountsToFile(am, "newAccounts.txt");
	}
}
