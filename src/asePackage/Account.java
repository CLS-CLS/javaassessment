package asePackage;

import java.util.ArrayList;

/**
 * @author Ioan
 * a minor change
 *
 */

public class Account {
	private int accountID;
	private ArrayList <Customer> ownerList;
	private double balance;      
	private boolean isClosed;

	public Account () {
		this.accountID = 0;
		this.ownerList = new ArrayList<Customer> ();
		this.balance = 0;
		this.isClosed=false;
	}	
	public Account (int accountID) {        
		this.accountID = accountID;
		this.ownerList = new ArrayList<Customer> ();
		this.balance = 0;
		this.isClosed=false;
	}
	public Account (int accountID, Customer owner) {        
		this.accountID = accountID;
		this.ownerList = new ArrayList<Customer> ();
		this.ownerList.add(owner);
		this.balance = 0;
		this.isClosed=false;
	}
	public Account (int accountID, ArrayList<Customer> ownerList) {        
		this.accountID = accountID;
		this.ownerList = ownerList;
		this.balance = 0;
		this.isClosed=false;
	}

	public int getId() {
		return accountID;
	}
	public ArrayList<Customer> getOwnerList() {
		return ownerList;
	}
	public double getBalance() {
		return balance;
	}
	/*
	 * public boolean testOwnership(Customer owner) {
	 * TODO
	 * }
	 */
	public void addOwner(Customer owner) {
		this.ownerList.add(owner);
	}
	public void removeOwner(Customer owner) {
		this.ownerList.remove(owner);
	}
	public void withdrawMoney(double sum) {
		this.balance-=sum;
	}
	public void depositMoney(double sum) {
		this.balance+=sum;
	}
	public void closeAccount() {
		this.isClosed=true;
	}
	public boolean accountIsClosed() {
		return this.isClosed;
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
