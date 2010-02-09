package asePackage;
import java.util.ArrayList;

/**Ali Ali Ali.....
 * I have changed 95% of your code!!! :)
 * Just comment all the methods ..you know.. what a method does what parameters it takes etc.. i have added 2 methods in the end
 * of the class with comments so you can see how you should comment the other things. Also you have written the code
 * twice .. one for person and one for customer... The customer extends person,,, that means you dont have to rewrite the get/Set First/Last name
 * methods.
 * Plus, just to know.. the names you are using are not so good.. for example you said customerFullName. 
 * You are already in the Customer class... So you should say just fullName. 
 * 
 * 
 * Customer class holds details about a customer ... (What details exactly?)
 */
public class Customer extends Person
{	
	
	public static final int MAXACCOUNTS = 2;
	
	private int id;
	private ArrayList <Account> accountList;

	
	public Customer(String firstName,String lastName, int id)
	{        
		super(firstName,lastName);
		this.id = id;
		accountList = new ArrayList<Account>();
	}

		
	public int getId()
	{
		return id;
	}

		
	public void setId(int customerId)
	{
		this.id = customerId;
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
	
	/**
	 * Removes the selected account from the ownership of the client. For this assessment 
	 * an account is removed from the customer if the account is closed.
	 * @param aca the account to be removed
	 * @return true if the account is removed false if this account does not exist.
	 */
	public boolean removeAccount(Account aca){
		return accountList.remove(aca);
	}
}