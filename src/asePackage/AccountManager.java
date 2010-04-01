package asePackage;

import java.util.ArrayList;
/**
 * AccountManager class retains a list of accounts and what will be the next available id.
 * It also provides the methods necessary for managing a list of accounts.
 */

public class AccountManager {
	private final static int FIRSTACCOUNTID = 100000;
	private ArrayList <Account> accountList;
	private int nextAccountId;
	
	/**
	 * The constructor for the case when we don't have previous data.
	 */
	public AccountManager() {
		this.accountList=new ArrayList<Account>();
		this.nextAccountId=FIRSTACCOUNTID;
	}
	/**
	 * The constructor for the case in which we have an existent
	 * list of accounts.
	 * @param accountList the old list of accounts
	 */
	public AccountManager(ArrayList<Account> accountList) {
		int lastID=0;
		this.accountList=new ArrayList<Account>(accountList);
		for(int i=0; i<accountList.size(); i++) {
			if(accountList.get(i).getId()>lastID) {
				lastID=accountList.get(i).getId();
			}
		}
		this.nextAccountId=lastID+1;
	}
	/**
	 * Add a new list of accounts to the already existent one
	 * @param accounts the new additional list of accounts
	 */
	public void addAcounts(ArrayList<Account> accounts) {
		int lastID=0;
		this.accountList = accounts;
		for(int i=0; i<accountList.size(); i++) {
			if(accountList.get(i).getId()>lastID) {
				lastID=accountList.get(i).getId();
			}
		}
		if(lastID!=0 && nextAccountId==FIRSTACCOUNTID)
			this.nextAccountId=lastID+1;
	}	
	/**
	 * Add a new account to our list
	 * @param owner the owner of the new account
	 * @return the new account
	 */
	public Account addAccount(Customer owner) {
		Account newAccount = new Account(nextAccountId, owner);
		owner.addAccount(newAccount);
		this.accountList.add(newAccount);
		this.nextAccountId++;
		return newAccount;
	}
	/**
	 * Delete the requested account
	 * @param accountToDelete the account which will be deleted
	 */
	public void deleteAccount(Account accountToDelete){
		ArrayList<Customer> customers = accountToDelete.getOwnerList();
		accountToDelete.closeAccount();
		for(Customer customer: customers) customer.removeAccount(accountToDelete);
		
		this.accountList.remove(accountToDelete);
	}
	/**
	 * Get the next available unique id for an account
	 * @return the next free account id
	 */
	public int getAvaibleAccountId() {
		return nextAccountId;
	}
	/**
	 * Get all the accounts
	 * @return the account list
	 */
	public ArrayList<Account> getAccountList() {
		return accountList;
	}
}
