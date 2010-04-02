package asePackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import clockUtils.TimeObserver;

public class Bank extends Thread implements TimeObserver {
	private static final String CUSTOMERFILE = "customers.txt";
	private static final String ACCOUNTSFILE = "accounts.txt";

	private static final int INITIALCUSTOMERDELAY = 700;
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
	private int customerGenerationDelay;
	private QueueManager qm;
	private Teller[] tellers = new Teller[numberOfTellers];
	private ArrayList<Customer> customers;
	private boolean proofOfAccurateTransactions = false;
	/**
	 * used to determine when all the tellers have finished their work so the
	 * program can proceed to generate statistics
	 */
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
		loadData();

	}

	public static int getInitialcustomerdelay() {
		return INITIALCUSTOMERDELAY;
	}

	public QueueManager getQm() {
		return qm;
	}

	private void loadData() {
		ArrayList<Account> accounts = new ArrayList<Account>();

		//loads customers and accounts and connects the accounts
		//with the customers
		try{
			if(customers.isEmpty())
				customers = MyUtilities.loadCustomers(CUSTOMERFILE);
			if(accounts.isEmpty()) {
				accounts = MyUtilities.loadAccounts(ACCOUNTSFILE,customers);
				am.addAcounts(accounts);   //adds the account to the account manager
			}
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

	/**
	 * generates random transactions for the given customer
	 * The transaction may be 1 or 2. So we can create Transfer or
	 * Deposit to foreign account transaction we generated a new foreign
	 * account which to use when creating the new transaction.
	 * @param customer the customer who will get a random transaction
	 * @return an arraylist of the transactions generated for the given customer
	 */
	private ArrayList<Transaction> generateTransactions(Customer customer) {
		int numberOfTransactions = rndGen.nextInt(2)+1;
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		for (int i = 0;i < numberOfTransactions;i++){
			Account account = selectRandomAccountFromCustomer(customer);
			Account foreignAccount = selectRandomForeignAccount(account);
			trans.add(Transaction.generateRandomTransaction(account, foreignAccount, rndGen));
		}
		return trans;
	}

	/**
	 * To generate a new foreign account is needed to find a new account
	 * different from one selected already. We try to generate random the new
	 * account until a different account is generated. The new account may have
	 * the same owner as the previous one.
	 * @param account account required to be different
	 * @return new foreign account
	 */
	private Account selectRandomForeignAccount(Account account) {
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
	private Account selectRandomAccountFromCustomer(Customer customer) {
		Account account = null;
		ArrayList<Account> accounts = customer.getAccountList();
		if(accounts.size()>0){
			account =  accounts.get(
					rndGen.nextInt(customer.getNumberOfAccounts()));
		}
		return account;
	}


	/**
	 * Get a new customer to be added to the queue if he is not already in the
	 * bank(queue or served by a teller)
	 * @return the new customer
	 */
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

	/**
	 * Creates a new queue element with a given customer for which
	 * a new set of transactions is generated. The log event for adding
	 * a customer is also inserted here.
	 * @param cust the new customer for which the element is created
	 */
	private void generateQueueElement(Customer cust){
		int currentQueueNumber=0;
		ArrayList<Transaction> trans = generateTransactions(cust);
		qm.addQueueElement(cust, trans);
		currentQueueNumber=qm.getNextNumber()-1;
		log.addLogEventJoinQueue(currentQueueNumber, cust, LogEvent.ENTERQUEUE);
		cust.setInsideBank(true);
	}

	/**
	 * Provides the final report from the log
	 * @return report text
	 */
	public String getFinalReport() {
		return log.toString();
	}


	public void run(){
		//create the teller threads
		for (int i = 0; i < numberOfTellers; i++)
			tellers[i].start();

		//Verify if the proofOfAccurateTransaction is set. In that case
		//we get the default values
		if(proofOfAccurateTransactions)		
			proofOfAccurateTransactions();		
		else
			while(isOpen){
				Customer customer = pickRandomCustomer();
				generateQueueElement(customer);
				try {Thread.sleep(customerGenerationDelay);}
				catch (InterruptedException e) {e.printStackTrace();}
			}

		// awakes the tellers that may be waiting for a customer in the queue
		//as there is no new customers going to be added 
		qm.awakeAllThreads();

		//we close the bank after the queue is generated for the case of proof of accurate trans
		if(proofOfAccurateTransactions)
			setOpen(false);

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

	/**
	 * sets the bank open or close, also communicating this information
	 * to all the tellers
	 * @param bool if the bank is open
	 */
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

	public Log getLog(){
		return log;
	}

	public int getTellerGenerationDelay() {
		return Teller.getTellerGenerationDelay();
	}
	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		Teller.setTellerGenerationDelay(tellerGenerationDelay);
	}

	public int getNumberOfTellers() {
		return numberOfTellers;
	}

	/**
	 * sets the number of teller in the Bank. Also updates the teller maxtix
	 * to correspond its size to the number of tellers and creates a  countDown latch
	 * with the specific number of tellers
	 * @param numberOfTellers 
	 */
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

	///////////////////PROOF OF ACCURATE TRASNACTIONS/////////////////////////////////////

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
		}catch (NotCorrectConstructorArgumentsException e) {
			e.printStackTrace();
		}

	}

	//////////////////////////////// ALI'S ADDITION - LOAD CUSTOMER / ACCOUNTS ////////////
	public void loadCustomers(File file) throws Exception {
		customers = MyUtilities.loadCustomers(file.getAbsolutePath());
	}
	public void loadAccounts(File file) throws Exception {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String fileName = file.getAbsolutePath();
		System.out.println(fileName);
		accounts = MyUtilities.loadAccounts(fileName, customers);
		am.addAcounts(accounts);
	}

	////////////////IMPLEMENTED METHODS OF THE TimeObserver INTERFACE ///////////////////////
	/**
	 * if the time is up closes the bank
	 */
	public void endOfTime() {
		setOpen(false);
	}
}
