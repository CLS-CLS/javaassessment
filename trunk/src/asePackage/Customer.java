package asePackage;
import java.util.ArrayList;

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
}