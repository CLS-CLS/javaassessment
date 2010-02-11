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
	public void addAccount(Customer owner) {
		this.accountList.add(new Account(nextAccountId, owner));
		this.nextAccountId++;
	}
	public void deleteAccount(Account accountToDelete) {
		this.accountList.remove(accountToDelete);
		//we should announce account's owners that is deleted
	}
	/*
	 * NO IDEA WHAT IS DOING
	 * 	public void verifyAccountNumber(Account accountToVerify) {
	 *	}
	 */
	
	/** I know what you mean... When an account is closed it should be removed 
	 * from every owner. The 2 solutions i have though of are like that. An account
	 * that is closed is NOT DELETED from the accounts list but instead it has a 
	 * boolean field isClosed. Then when a customer goes to the teller to do a 
	 * transaction (deposit or withdrawl) then the teller removes the acount from the account list of
	 * the client if it already closed. Also if the client wants to open an aca and has already 2 it checks if both the acas 
	 * are opened. So bottomline is that the acount are not removed as from the acount manager class but only from the 
	 * customers account list becuase what happens if a aca is removed from 
	 * the list(of the account manager) and there is another client connected to that aca? 
	 * Another solution is when an account is closed (and deleted) to check ALL the clients one by one
	 * and remove that account from their ownership(that will be done from the teller).
	 * You choose what you prefer and tell me. 
	 * 
	 * Also the accounts id are not just fixed numbers( i see that you have as a starting id the number 10000
	 * and then you add +1. I think this is not correct becuase we are going to have an already existing file with 
	 * account , hence already existing ids. Also when a new aca is opened what id should take? The id should be not the same
	 * with an already existing aca . So you must search which id values are avaible. 
	 */
	
	public int getAvaibleAccountId() {
		return nextAccountId;
	}
	

}
