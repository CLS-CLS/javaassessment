package asePackage;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;

/**
 * 
 * @author Chris
 *
 */
public class Bank extends Thread{
	private static final int INITIALCUSTOMERDELAY = 700;
	public static final int NUMBEROFTELLERS = 3;
	/*
	 * used to generate random numbers needed for creating random transactions
	 * and pick random customers
	 */
	private Random rndGen;

	/*
	 * holds information about the customers in the queue
	 */
	private boolean isOpen = false;
	private boolean isPaused = false;
	private int customerGenerationDelay;
	private QueueManager qm;
	private Teller[] teller = new Teller[NUMBEROFTELLERS];
	private ArrayList<Customer> customers;
	private boolean proofOfAccurateTransactions = false;

	/*
	 * holds the log of the bank
	 */
	private static Log log;

	/*
	 * manipulates the accounts
	 */
	private AccountManager am;


	public Bank(){
		customerGenerationDelay= INITIALCUSTOMERDELAY;
		rndGen = new Random();
		log = new Log();
		qm = new QueueManager();
		customers = new ArrayList<Customer>();
		am = new AccountManager();
		
		for (int i = 0; i < NUMBEROFTELLERS; i++){
			teller[i] = new Teller(qm,am,log,i+1);
		}
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
		//proofOfAccurateTransactions should be set as true for full testing of//
		//transactions                                                         //
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//

		loadData(proofOfAccurateTransactions);

	}

	public static int getInitialcustomerdelay() {
		return INITIALCUSTOMERDELAY;
	}

	public QueueManager getQueueManager() {
		return qm;
	}

	private void loadData(boolean proofOfAccurateTransactions) {
		ArrayList<Account> accounts = new ArrayList<Account>();

		if(proofOfAccurateTransactions == true) proofOfAccurateTransactions();
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
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(0).getAccountList().get(0),100));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(0).getAccountList().get(1),150));
			qm.addQueueElement(customers.get(0), trans);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.OPEN, new Account(),500));
			qm.addQueueElement(customers.get(2), trans);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),100));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),50));
			trans.add(new Transaction(Transaction.DEPOSIT, customers.get(3).getAccountList().get(0),250));
			trans.add(new Transaction(Transaction.VIEWBALANCE, customers.get(3).getAccountList().get(0),0));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),160));
			trans.add(new Transaction(Transaction.OPEN, new Account(),600));
			trans.add(new Transaction(Transaction.CLOSE, customers.get(3).getAccountList().get(0),0));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),10));
			trans.add(new Transaction(Transaction.DEPOSIT, customers.get(3).getAccountList().get(0),900));
			trans.add(new Transaction(Transaction.VIEWBALANCE, customers.get(3).getAccountList().get(0),0));
			trans.add(new Transaction(Transaction.CLOSE, customers.get(3).getAccountList().get(0),0));
			qm.addQueueElement(customers.get(3), trans);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.DEPOSIT, am.getAccountList().get(1),50));
			qm.addQueueElement(customers.get(1), trans);
		}catch (Exception e) {
			e.printStackTrace();
		}

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
			int ownerRandom = rndGen.nextInt(10)+1;
			if(ownerRandom==1){
				boolean owner=false;
				Account aca = selectRandomAca();
				trans.add(Transaction.generateRandomTransaction(aca, rndGen, owner));
				System.out.println("-");
			}
			else {
				boolean owner=true;
				Account aca = selectRandomAca(customer);
				trans.add(Transaction.generateRandomTransaction(aca, rndGen, owner));
			}
		}
		return trans;
	}

	private Account selectRandomAca() {
		Account account = null;
		ArrayList<Account> accounts = am.getAccountList();
		if(accounts.size()>0){
			account =  accounts.get(
					rndGen.nextInt(accounts.size()));
		}
		return account;
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



	private Customer pickRandomCustomer() {
		Customer result = null;
		boolean success=false;
		while(!success){
			int index = rndGen.nextInt(customers.size());
			if (qm.containsCustomer(customers.get(index))==false && !customers.get(index).isInsideBank()) {
				result=customers.get(index);
				success=true;
			}
		}
		return result;
	}

	private void generateQueueElement(Customer cust){
		int currentQueueNumber=0;
		ArrayList<Transaction> trans = generateTransactions(cust);
		qm.addQueueElement(cust, trans);
		currentQueueNumber=qm.getNextNumber()-1;
		log.addLogEvent(currentQueueNumber, cust, LogEvent.ENTERQUEUE);
		cust.setInsideBank(true);
	}



	public String getFinalReport() {
		return log.toString();
	}


	public void run(){
		int counter = 0;
		for (int i = 0; i < NUMBEROFTELLERS ; i++)
			new Thread(teller[i]).start();
		while(isOpen || !qm.isQueueEmpty()){
			if(isOpen && !isPaused) {
				Customer customer = pickRandomCustomer();
				generateQueueElement(customer);
				counter ++;
				System.out.println(counter);
			}
			try {
				Thread.sleep(customerGenerationDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		MyUtilities.saveStringToFile(log.toString(), "log.txt");
		MyUtilities.saveCustomersToFile(customers, "newCustomers.txt");
		MyUtilities.saveAccountsToFile(am, "newAccounts.txt");
	}

	public boolean isOpen(){
		return isOpen;
	}

	public void setOpen(boolean bool){
		isOpen = bool;

	}

	public void setCustomerGenerationDelay(int customerGenerationDelay) {
		this.customerGenerationDelay = customerGenerationDelay;
	}

	public int getCustomerGenerationDelay() {
		return customerGenerationDelay;
	}

	public void setObserver(Observer o){
		log.addObserver(o);
	}

	public int getTellerGenerationDelay() {
		return Teller.getTellerGenerationDelay();
	}
	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		Teller.setTellerGenerationDelay(tellerGenerationDelay);
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
		Teller.setBankIsPaused(isPaused);
	}

	public boolean isPaused() {
		return isPaused;
	}

	public Teller getTeller(int tellerID) {
		return teller[tellerID];
	}	
}
