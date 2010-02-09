package asePackage;

import java.util.ArrayList;

public class Account {
	private int accountID;
	private ArrayList <Customer> ownerList;
	private long balance;                       //<----maybe it should be double? e.g. 256.70 pounds?
	                                            //and long? haha how RICH this person can be...i wish it was me :)

	public Account (int accountID) {        
		this.accountID = accountID;
		this.ownerList = new ArrayList<Customer> ();
		this.balance = 0;
	}
	public int getId() {
		return accountID;
	}
	public ArrayList<Customer> getOwnerList() {
		return ownerList;
	}
	public long getBalance() {
		return balance;
	}
	/*
	 * public boolean testOwnership(Customer owner) {
	 * TODO
	 * }
	 */
	public void withdrawMoney(long sum) {
		this.balance-=sum;
	}
	public void depositMoney(long sum) {
		this.balance+=sum;
	}
	
	@Override
	public String toString() {
		return "Account [ID=" + accountID + ", Balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountID != other.accountID)
			return false;
		return true;
	}	
}
