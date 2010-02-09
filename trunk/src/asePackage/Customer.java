package asePackage;
import java.util.ArrayList;

/**
 * Customer class holds details about a customer 
 */
public class Customer extends Person
{	
	/**
	 * instance variables
	 */
	public static final int MAXACCOUNTS = 2;
	private String customerFirstName;
	private String customerLastName;
	private int customerID;
	private ArrayList <Account> accountList;

	/**
	 * constructor to create a customer
	 * @param customerFirstName
	 * @param customerLastName
	 * @param customerID
	 */
	public Customer(String customerFirstName,String customerLastName, int customerID)
	{        
		super(customerFirstName,customerLastName);
		this.customerID = customerID;
		accountList = new ArrayList<Account>();
	}

	/**
	 * get method to get the first name of the customer
	 * @return
	 */
	public String getCustomerFirstName()
	{
		return customerFirstName;	
	}

	/**
	 * get method to get the last name of the customer
	 * @return
	 */
	public String getCustomerLastName()
	{
		return customerLastName;
	}

	/**
	 * get method to get the ID of the customer
	 * @return
	 */
	public int getCustomerID()
	{
		return customerID;
	}

	/**
	 * set method to modify the first name of the customer
	 * @param customerFirstName
	 */
	public void setCustomerFirstName(String customerFirstName)
	{
		this.customerFirstName = customerFirstName;
	}

	/**
	 * set method to modify the last name of the customer
	 * @param customerLastName
	 */
	public void setCustomerLastName(String customerLastName)
	{
		this.customerLastName = customerLastName;
	}

	/**
	 * set method to modify the ID of the customer
	 * @param customerID
	 */
	public void setCustomerID(int customerID)
	{
		this.customerID = customerID;
	}
	
	public void addAccount(Account aca){
		accountList.add(aca);
	}
	
	/**
	 * Removes the selected account from the ownership of the client. For this assessment 
	 * an account is removed from the customer if the account is closed.
	 * @param accountId the account id of the account to be removed
	 * @return true if the account is removed false if this account does not exist
	 */
	public boolean removeAccount(int accountId){
		boolean isRemoved = false;
		int index=-1;
		for (Account aca: accountList){
			if (aca.getId()== accountId)index = accountList.indexOf(aca);
		}
		if(index!=-1){
			accountList.remove(index);
			isRemoved = true;
		}
		return isRemoved;
	}
	
	public boolean removeAccount(Account aca){
		return accountList.remove(aca);
	}
}