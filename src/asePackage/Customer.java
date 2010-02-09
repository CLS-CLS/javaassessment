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
	private String customerFirstName;
	private String customerLastName;
	private String customerID;
	private ArrayList <Account> accountList;

	/**
	 * constructor to create a customer
	 * @param customerFirstName
	 * @param customerLastName
	 * @param customerID
	 */
	public Customer(String customerFirstName,String customerLastName, String customerID)
	{        
		super(customerFirstName,customerLastName);
		this.customerID = customerID;
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
	public String getCustomerID()
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
	public void setCustomerID(String customerID)
	{
		this.customerID = customerID;
	}
}