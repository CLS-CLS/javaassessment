package asePackage;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private Random rndGen;
	private QueueManager qm;
	private Teller teller;
	private ArrayList<Customer> customers;
	private ArrayList<Account> accounts;
	private AccountManager am;
	
	
	public void initializer(){
		qm = new QueueManager();
		teller = new Teller(qm);
		customers = new ArrayList<Customer>();
		accounts = new ArrayList<Account>();
		am = new AccountManager();
		try{
			customers = MyUtilities.loadCustomers("");//TODO file with customers
			accounts = MyUtilities.loadAccounts("",customers);//TODO file with accounts
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
			
		}
		ArrayList<Customer> selectedCustomers = pickRandomCustomers(); 
		
		for (Customer customer:selectedCustomers){
			int numberOfTransactions = rndGen.nextInt(2)+1;
			for (int i = 0;i < numberOfTransactions;i++){
				Account aca = selectRandomAca();
				//TODO generate transactions
			}
		}
		
	}
	private Account selectRandomAca() {
		// TODO Auto-generated method stub
		return null;
	}
	private ArrayList<Customer> pickRandomCustomers() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		Bank bank = new Bank();
		
		
		
		

	}

}
