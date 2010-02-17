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
	
	
	public void initializer(){
		rndGen = new Random();
		log = new Log();
		qm = new QueueManager(log);
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
		}
		
		teller = new Teller(qm,am,log);
		
		
		
	}
	
	public void runBank(){
		while(!qm.isQueueEmpty()){
			teller.getNextCustomer();
			teller.doTransaction();
		}
		
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
		return customer.getAccountList().get(
				rndGen.nextInt(customer.getNumberOfAccounts()));
	}
	
	private ArrayList<Customer> pickRandomCustomers() {
		ArrayList<Customer> subList = new ArrayList<Customer>();
		for (int i = 0 ; i <10 ; i++){
			subList.add(customers.get(i));
		}
		return subList;
		
	}
	
	
	public String getLog() {
		return log.toString();
	}
	
	public static void main(String[] args) {
		Bank bank = new Bank();
		bank.initializer();
		bank.runBank();
		
		System.out.println(log);
		MyUtilities.saveStringToFile(log.toString(), "log.txt");
	}

}