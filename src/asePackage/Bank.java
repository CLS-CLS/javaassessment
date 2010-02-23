package asePackage;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private Random rndGen;
	private QueueManager qm;
	private Teller teller;
	private ArrayList<Customer> customers;
	private static Log log;
	private AccountManager am;
	
	
	public Bank(){
		rndGen = new Random();
		log = new Log();
		qm = new QueueManager();
		customers = new ArrayList<Customer>();
		ArrayList<Account> accounts = new ArrayList<Account>();
		am = new AccountManager();
		try{
			customers = MyUtilities.loadCustomers("customers.txt");
			accounts = MyUtilities.loadAccounts("accounts.txt",customers);
			am.addAcounts(accounts);
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		//Add the accounts to the customer
		for (Account aca: am.getAccountList()){
			for(Customer customer: aca.getOwnerList()){
				customer.
				addAccount(aca);
			}
		}
		
		//pick some random customers
		ArrayList<Customer> selectedCustomers = pickRandomCustomers(); 
		for (Customer customer:selectedCustomers){
			qm.addQueueElement(customer,generateTransactions(customer));
			log.addLogEvent(qm.getNextNumber()-1, customer, LogEvent.ENTERQUEUE);
		}
		
		teller = new Teller(qm,am,log);
		
		
		
	}
	
	private ArrayList<Transaction> generateTransactions(Customer customer) {
		int numberOfTransactions = rndGen.nextInt(2)+1;
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		for (int i = 0;i < numberOfTransactions;i++){
			Account aca = selectRandomAca(customer);
			trans.add(Transaction.generateRandomTransaction(aca, rndGen));
		}
		return trans;
	}

	
	private Account selectRandomAca(Customer customer) {
		Account account = null;
		ArrayList<Account> accounts = customer.getAccountList();
		if(accounts.size()>0){
			account =  accounts.get(
					rndGen.nextInt(customer.getNumberOfAccounts()));
		}
		return account;
	}
	
	private ArrayList<Customer> pickRandomCustomers() {
		ArrayList<Customer> subList = new ArrayList<Customer>();
//		while(subList.size()<10){
//			int rnd = rndGen.nextInt(customers.size());
//			Customer customer = customers.get(rnd);
//			if (!subList.contains(customer))subList.add(customer);
//		}
		subList.add(new Customer("NO","Account",1000));
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
		System.out.println(log);
		MyUtilities.saveStringToFile(log.toString(), "log.txt");
		MyUtilities.saveCustomersToFile(customers, "newCustomers.txt");
		MyUtilities.saveAccountsToFile(am, "newAccounts.txt");
	}
}
