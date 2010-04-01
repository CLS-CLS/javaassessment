package asePackage;

import java.util.ArrayList;

/**
 * Account class contains all the details about an account. It also includes a list of it's 
 * owners. The class also provides methods to perform operations of set and get on the existent
 * data
 */

public class Account 
{
	private int accountID;
	private ArrayList <Customer> ownerList;
	private double balance;      
	private boolean isClosed;

	/**
	 * Creates a new instance of the new account with default values.
	 */
	public Account () 
	{
		this.accountID = 0;
		this.ownerList = new ArrayList<Customer> ();
		this.balance = 0;
		this.isClosed = false;
	}
	
	/**
	 * Creates the new Account instance starting with a value for the account id
	 * and default values for the other informations.
	 * @param accountID new account identifier
	 */
	public Account (int accountID) 
	{        
		this.accountID = accountID;
		this.ownerList = new ArrayList<Customer> ();
		this.balance = 0;
		this.isClosed = false;
	}
	
	/**
	 * Creates the new Account instance starting with a value for the account id
	 * and for the owner. The balance is set as empty.
	 * @param accountID new account identifier
	 * @param owner customer that owns the class
	 */
	public Account (int accountID, Customer owner)
	{        
		this.accountID = accountID;
		this.ownerList = new ArrayList<Customer> ();
		this.ownerList.add(owner);
		this.balance = 0;
		this.isClosed = false;
	}
	
	/**
	 * Creates the new Account instance starting with a value for the account id
	 * and a list of owners. The account balance is set on 0.
	 * @param accountID new account identifier
	 * @param ownerList the list of owner
	 */
	public Account (int accountID, ArrayList<Customer> ownerList) 
	{        
		this.accountID = accountID;
		this.ownerList = ownerList;
		this.balance = 0;
		this.isClosed=false;
	}
	
	/**
	 * Creates the new Account instance starting with a value for the account id
	 * and a list of owners. The last inserted information is the account balance.
	 * @param accountID new account identifier
	 * @param balance the new balance value
	 * @param ownerList the list of owner
	 */
	public Account (int accountID, double balance, ArrayList<Customer> ownerList) 
	{        
		this.accountID = accountID;
		this.balance = balance;
		this.ownerList = ownerList;
		this.isClosed=false;
	}

	/**
	 * Provides the current account id.
	 * @return account id
	 */
	public int getId() 
	{
		return accountID;
	}
	
	/**
	 * Provides the current list of owners of the account.
	 * @return owner list
	 */
	public ArrayList<Customer> getOwnerList() 
	{
		return ownerList;
	}
	
	/**
	 * Returns the current state of the balance. 
	 * @return balance
	 */
	public double getBalance() 
	{
		return balance;
	}
	
	/**
	 * It will add a new owner to the account.
	 * @param owner required owner to be add
	 */
	public void addOwner(Customer owner) 
	{
		this.ownerList.add(owner);
	}
	
	/**
	 * It will remove the required owner of the account.
	 * @param owner required customer to be removed
	 */
	public void removeOwner(Customer owner) 
	{
		this.ownerList.remove(owner);
	}
	
	/**
	 * It decrease the current account balance with the required sum.
	 * @param sum amount of money which will be withdrawn
	 */
	public void withdrawMoney(double sum) 
	{
		this.balance-=sum;
	}
	
	/**
	 * It increase the current account balance with the required sum.
	 * @param sum amount of money which will be deposed
	 */
	public void depositMoney(double sum) 
	{
		this.balance+=sum;
	}
	
	/**
	 * Closes an account by setting a status variable to closed.
	 */
	public void closeAccount() 
	{
		this.isClosed=true;
	}
	
	/**
	 * The method tests if the account has gotten a closed status or not.
	 * @return class status
	 */
	public boolean isClosed() 
	{
		return this.isClosed;
	}
	
	/**
	 * Returns all the information regarding the class into a string.
	 */
	@Override
	public String toString() 
	{
		String result;
		result=accountID + "," + balance;
		for(int i=0;i<ownerList.size();i++) 
		{
			result+=","+ownerList.get(i).getId();
		}

		return result;
	}

	/**
	 * Generates the hash code for the object starting with their hashcode.
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		return result;
	}
	
	/**
	 * The method tests the equality between two account objects
	 * based on their account id.
	 */
	@Override
	public boolean equals(Object obj) 
	{
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
