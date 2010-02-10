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

}
