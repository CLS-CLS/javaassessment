package asePackage;
import java.util.ArrayList;

//import org.omg.CORBA.portable.CustomValue;


/**
 * Customer class that holds details about a customer (first name, last name,
 * allowed number of accounts, his/her identification number, and his/her account(s))
 * and methods to get and set the identification number, methods to add an account to
 * the account list and remove an account from the account list 
 */
public class Customer extends Person
{	
	/**
	 * Instance variables
	 */
	public static final int MAXACCOUNTS = 2;
	private int id;
	private ArrayList <Account> accountList;
	private boolean insideBank;


	/**
	 * Creates a customer with first name, last name, id, and his/her account(s)
	 * @param firstName the first name of the customer
	 * @param lastName the last name of the customer
	 * @param id the identification number of the customer
	 */
	public Customer(String firstName, String lastName, int id)
	{        
		super(firstName,lastName);
		this.id = id;
		accountList = new ArrayList<Account>();
		setInsideBank(false);
	}
	/**
	 * Gets the identification number of the customer
	 * @return the identification number of the customer
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * Modifies the identification number of the customer 
	 * @param customerId the identification number of the customer that has to be modified
	 */
	public void setId(int customerId)
	{
		this.id = customerId;
	}
	/**
	 * Adds an account to the account list
	 * @param aca the account to be added
	 */
	public void addAccount(Account aca)
	{
		accountList.add(aca);
	}

	/**
	 * Removes the selected account from the ownership of the client. For this assessment 
	 * an account is removed from the customer if the account is closed.
	 * @param accountId the account id of the account to be removed
	 * @return true if the account is removed false if this account does not exist
	 */
	public boolean removeAccount(int accountId)
	{
		boolean isRemoved = false;
		int index = -1;
		for (Account aca: accountList)
		{
			if (aca.getId() == accountId)
				index = accountList.indexOf(aca);
		}
		if(index!=-1)
		{
			accountList.remove(index);
			isRemoved = true;
		}
		return isRemoved;
	}

	/**
	 * Removes the selected account from the ownership of the client. For this assessment 
	 * an account is removed from the customer if the account is closed.
	 * @param aca the account to be removed
	 * @return true if the account is removed false if this account does not exist.
	 */
	public boolean removeAccount(Account aca)
	{
		return accountList.remove(aca);
	}

	/**
	 * Checks if the customer is owner of the given account
	 * @param aca the account to be checked
	 * @return true if the account is owned by the customer false otherwise
	 */
	public boolean hasAccount(Account aca){

		return accountList.contains(aca);
	}

	/**
	 * Tests the equality between two customers objects
	 * based on their first names, last names, and IDs.
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if(!(obj instanceof Customer ))return false;
		else {
			Customer c = (Customer)obj;
			if(c.getFirstName().equals(this.getFirstName())&&
					c.getLastName().equals(this.getLastName())&&
					c.getId()== this.getId())return true;
		}
		return false;
	}

	/**
	 * Gets the number of accounts
	 * @return accountList.size the number of accounts 
	 */
	public int getNumberOfAccounts() 
	{
		return accountList.size();
	}

	/**
	 * gets the account list
	 * @return accountList the account list
	 */
	public ArrayList<Account> getAccountList() 
	{
		return accountList;
	}

	/**
	 * Modifies the account list
	 * @param accountList the account list
	 */
	public void setAccountList(ArrayList<Account> accountList)
	{
		this.accountList = accountList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	public void setInsideBank(boolean insideBank) {
		this.insideBank = insideBank;
	}
	public boolean isInsideBank() {
		return insideBank;
	}
	/**
	 * Returns all the information regarding the class into a string
	 */
	@Override
	public String toString() 
	{
		return super.getFirstName() + "," + super.getLastName() + "," + id;
	}

}