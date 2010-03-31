package asePackage;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import asePackage.Controller.TellerSlideListener;

/**
 * 
 * @author Chris
 *
 */
public class Bank extends Thread{
	private static final int INITIALCUSTOMERDELAY = 700;
	private static final int OCCURANCEDEPOSITFOREIGNACCOUNT = 10;
	private int numberOfTellers = 3;
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
	private Teller[] tellers = new Teller[numberOfTellers];
	private ArrayList<Customer> customers;
	private boolean proofOfAccurateTransactions = false;
	CountDownLatch countDown;

	/*
	 * holds the log of the bank
	 */
	private static Log log;

	/*
	 * manipulates the accounts
	 */
	private AccountManager am;


	public Bank(){
		countDown = new CountDownLatch(numberOfTellers);
		customerGenerationDelay= INITIALCUSTOMERDELAY;
		rndGen = new Random();
		log = new Log();
		qm = QueueManager.getInstance();
		customers = new ArrayList<Customer>();
		am = new AccountManager();
		
		createTellers();
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
		//proofOfAccurateTransactions should be set as true for full testing of//
		//transactions                                                         //
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//

		loadData(proofOfAccurateTransactions);

	}

	public static int getInitialcustomerdelay() {
		return INITIALCUSTOMERDELAY;
	}

	public QueueManager getQm() {
		return qm;
	}

	private void loadData(boolean proofOfAccurateTransactions) {
		ArrayList<Account> accounts = new ArrayList<Account>();

		if(proofOfAccurateTransactions == true) proofOfAccurateTransactions();
		else {
			//loads customers and accounts and connects the accounts
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



	
	/**
	 * generates random transactions for the given customer
	 * The transaction may be 1 or 2
	 * @param customer the customer who will get a random transaction
	 * @return an arraylist of the transactions generated for the given customer
	 */
	/*
	 * MODIFIED
	 * IOAN 27.03 //TODO comments
	 * 
	 */
	private ArrayList<Transaction> generateTransactions(Customer customer) {
		int numberOfTransactions = rndGen.nextInt(2)+1;
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		for (int i = 0;i < numberOfTransactions;i++){
				Account account = selectRandomAccount(customer);
				Account foreignAccount = selectRandomAccountQM(account);
				
				trans.add(Transaction.generateRandomTransaction(account, foreignAccount, rndGen));
		}
		return trans;
	}
	/*
	 * IOAN 27.03
	 */
	private Account selectRandomAccountQM(Account account) {
		boolean success=false;
		ArrayList<Account> accounts = am.getAccountList();
		int randomValue;
		Account newAccount = null;
		
		if(accounts.size()>0){
			while(!success){
				randomValue = rndGen.nextInt(accounts.size());
				if(!accounts.get(randomValue).equals(account)) {
					newAccount =  accounts.get(randomValue);
					success = true;
				}
			}
		}
		return newAccount;
	}

	/**
	 * selects a random account from the accounts that a customer has
	 * @param customer
	 * @return a random account, null if the customer has no accounts
	 */
	private Account selectRandomAccount(Customer customer) {
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
		log.addLogEventJoinQueue(currentQueueNumber, cust, LogEvent.ENTERQUEUE);
		cust.setInsideBank(true);
	}



	public String getFinalReport() {
		return log.toString();
	}


public void run(){
		
		for (int i = 0; i < numberOfTellers; i++)
			tellers[i].start();
		
		while(isOpen){
			Customer customer = pickRandomCustomer();
			generateQueueElement(customer);
			try {Thread.sleep(customerGenerationDelay);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		// awakes the tellers that may be waiting for a customer in the queue
		//as there is no new customers going to be added 
		qm.awakeAllThreads();
		
		//waits until all the tellers are done their work
		//(all the customers are served)
		try {
			countDown.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MyUtilities.saveStringToFile(log.toString(), "log.txt");
		MyUtilities.saveStringToFile(log.getSummary(), "summary.txt");
		MyUtilities.saveCustomersToFile(customers, "newCustomers.txt");
		MyUtilities.saveAccountsToFile(am, "newAccounts.txt");
	}

	public boolean isOpen(){
		return isOpen;
	}

	public void setOpen(boolean bool){
		isOpen = bool;
		for (Teller t : tellers){
			t.setBankClosed(!bool);
		}

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
	public void removeObserver(Observer o) {
		log.deleteObserver(o);
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

	public int getNumberOfTellers() {
		return numberOfTellers;
	}

	public void setNumberOfTellers(int numberOfTellers) {
		this.numberOfTellers = numberOfTellers;
		countDown = new CountDownLatch(numberOfTellers);
		tellers = new Teller[numberOfTellers];
		
	}

	public void createTellers() {
		for (int i = 0; i < numberOfTellers; i++){
			tellers[i] = new Teller(am,log,i+1,countDown);
		}
		
	}

	public boolean isProofOfAccurateTransactions() {
		return proofOfAccurateTransactions;
	}

	public void setProofOfAccurateTransactions(boolean proofOfAccurateTransactions) {
		this.proofOfAccurateTransactions = proofOfAccurateTransactions;
	}

	
	
	
	
	////////////////////////////////////////////////////////////////////////////////
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
		
		try {
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(0), LogEvent.ENTERQUEUE);
			trans.add(new Transaction(Transaction.OPEN, new Account(), 200, null));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(0).getAccountList().get(0),100, null));		
			qm.addQueueElement(customers.get(0), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(3), LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),100, null));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),50, null));
			trans.add(new Transaction(Transaction.DEPOSIT, customers.get(3).getAccountList().get(0),250, null));
			trans.add(new Transaction(Transaction.VIEWBALANCE, customers.get(3).getAccountList().get(0),0, null));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),160, null));
			trans.add(new Transaction(Transaction.OPEN, new Account(),600, null));
			qm.addQueueElement(customers.get(3), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(2), LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.OPEN, new Account(),500, null));
			qm.addQueueElement(customers.get(2), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(),customers.get(3),LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.CLOSE, customers.get(3).getAccountList().get(0),0, null));
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(3).getAccountList().get(0),10, null));
			trans.add(new Transaction(Transaction.DEPOSIT, customers.get(3).getAccountList().get(0),900, null));
			trans.add(new Transaction(Transaction.VIEWBALANCE, customers.get(3).getAccountList().get(0),0, null));
			trans.add(new Transaction(Transaction.CLOSE, customers.get(3).getAccountList().get(0),0, null));
			trans.add(new Transaction(Transaction.OPEN, new Account(), 200, null));
			trans.add(new Transaction(Transaction.OPEN, new Account(), 400, null));
			qm.addQueueElement(customers.get(3), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(1), LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.DEPOSIT, am.getAccountList().get(1),50, null));
			trans.add(new Transaction(Transaction.TRANSFER, customers.get(1).getAccountList().get(0),200, am.getAccountList().get(4)));
			trans.add(new Transaction(Transaction.TRANSFER, customers.get(1).getAccountList().get(0),700, am.getAccountList().get(2)));
			qm.addQueueElement(customers.get(1), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(4), LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.TRANSFER, am.getAccountList().get(4),50, am.getAccountList().get(1)));
			trans.add(new Transaction(Transaction.DEPOSITFOREIGNACCOUNT, null,50, am.getAccountList().get(1)));
			trans.add(new Transaction(Transaction.DEPOSITFOREIGNACCOUNT, null,50, am.getAccountList().get(0)));
			qm.addQueueElement(customers.get(4), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(0), LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.WITHDRAWAL, customers.get(0).getAccountList().get(1),150, null));
			trans.add(new Transaction(Transaction.TRANSFER, customers.get(0).getAccountList().get(0),300, customers.get(0).getAccountList().get(1)));
			qm.addQueueElement(customers.get(0), trans);
			
			log.addLogEventJoinQueue(qm.getNextNumber(), customers.get(5), LogEvent.ENTERQUEUE);
			trans=new ArrayList<Transaction>();
			trans.add(new Transaction(Transaction.DEPOSITFOREIGNACCOUNT, null,150, am.getAccountList().get(3)));
			trans.add(new Transaction(Transaction.OPEN, new Account(),300, customers.get(0).getAccountList().get(1)));
			qm.addQueueElement(customers.get(5), trans);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
