package asePackage;

import java.util.ArrayList;

/**
 * @author Ioan
 *
 */

public class AccountManager {
	private final static int FIRSTACCOUNTID=100000;
	
	private ArrayList <Account> accountList;
	private int nextAccountId;
	
	public AccountManager() {
		this.accountList=new ArrayList<Account>();
		this.nextAccountId=FIRSTACCOUNTID;
	}
	/*
	 * now, when we create the object we can give a list of accounts as input and it will
	 * search automatically which is the last account value from the list and will create
	 * the next possible value by adding one
	 */	
	public AccountManager(ArrayList<Account> accountList) {
		this.accountList=new ArrayList<Account>(accountList);
		this.nextAccountId=this.accountList.get(this.accountList.size()-1).getId()+1;
	}
	public Account addAccount(Customer owner) {
		Account newAccount = new Account(nextAccountId, owner);
		this.accountList.add(newAccount);
		this.nextAccountId++;
		return newAccount;
	}
	
	
	public void deleteAccount(Account accountToDelete){
		ArrayList<Customer> customers = accountToDelete.getOwnerList();
		for(Customer customer: customers) customer.removeAccount(accountToDelete);
		this.accountList.remove(accountToDelete);
		
	}
	
	public int getAvaibleAccountId() {
		return nextAccountId;
	}
	
	public ArrayList<Account> getAccountList() {
		return accountList;
	}
	

}
