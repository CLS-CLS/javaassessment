package asePackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ali
 *
 */
public class AccountTest 
{
	private Account account1;
	private Account account2;
	private Account account3;
	private Account account4;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		ArrayList<Customer> customerTest4 = new ArrayList<Customer>();
		account1 = new Account();
		account2 = new Account(1);
		account2.addOwner(new Customer("Ali", "Alshbel", 1));
		account3 = new Account(2, new Customer("Ali", "Alshbel", 1));
		account3.depositMoney(100);
		customerTest4.add(new Customer("Ali", "Alshbel", 1));
		customerTest4.add(new Customer("Monica", "Farrow", 2));
		account4 = new Account(3, customerTest4);
		account4.depositMoney(200);
		account4.withdrawMoney(100);
	}

	/**
	 * Test method for {@link asePackage.Account#hashCode()}.
	 */
	@Test
	public void testHashCode() 
	{
		Integer result = 34;
		Integer currentValue = account4.hashCode();
		assertTrue("Wrong hash code (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#Account()}.
	 */
	@Test
	public void testAccount() 
	{
		String result = "0,0.0";
		String currentValue = "" + account1;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#Account(int)}.
	 */
	@Test
	public void testAccount2() 
	{
		String result = "1,0.0,1";
		String currentValue = "" + account2;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#Account(int, asePackage.Customer)}.
	 */
	@Test
	public void testAccount3() 
	{
		String result = "2,100.0,1";
		String currentValue = "" + account3;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#Account(int, java.util.ArrayList)}.
	 */
	@Test
	public void testAccount4() 
	{
		String result = "3,100.0,1,2";
		String currentValue = "" + account4;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#getId()}.
	 */
	@Test
	public void testGetId() 
	{
		Integer result = 3;
		Integer currentValue = account4.getId();
		assertTrue("Wrong account id (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#getOwnerList()}.
	 */
	@Test
	public void testGetOwnerList() 
	{
		ArrayList<Customer> result = new ArrayList<Customer>();
		ArrayList<Customer> currentValue = account4.getOwnerList();
		
		result.add(new Customer("Ali", "Alshbel", 1));
		result.add(new Customer("Monica", "Farrow", 2));

		assertTrue("Wrong owner list (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.containsAll(result));
	}

	/**
	 * Test method for {@link asePackage.Account#getBalance()}.
	 */
	@Test
	public void testGetBalance() 
	{
		Double result = 100.0;
		Double currentValue = account4.getBalance();
		assertTrue("Wrong balance (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#addOwner(asePackage.Customer)}.
	 */
	@Test
	public void testAddOwner() 
	{
		ArrayList<Customer> result = new ArrayList<Customer>();
		result.add(new Customer("Ali", "Alshbel", 1));
		result.add(new Customer("Monica", "Farrow", 2));
		
		account2.addOwner(new Customer("Monica", "Farrow", 2));
		ArrayList<Customer> currentValue = account2.getOwnerList();
		assertTrue("Wrong owner list (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#removeOwner(asePackage.Customer)}.
	 */
	@Test
	public void testRemoveOwner() 
	{
		ArrayList<Customer> result=new ArrayList<Customer>();
		result.add(new Customer("Ali", "Alshbel", 1));
		
		account2.removeOwner(new Customer("Monica", "Farrow", 2));
		ArrayList<Customer> currentValue = account2.getOwnerList();
		assertTrue("Wrong owner list (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#withdrawMoney(double)}.
	 */
	@Test
	public void testWithdrawMoney() 
	{
		String result = "3,0.0,1,2";
		account4.withdrawMoney(100);
		String currentValue = "" + account4;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#depositMoney(double)}.
	 */
	@Test
	public void testDepositMoney() 
	{
		String result = "3,200.0,1,2";
		account4.depositMoney(100);
		String currentValue = "" + account4;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#closeAccount()}.
	 */
	@Test
	public void testCloseAccount() 
	{
		String result = "true";
		account4.closeAccount();
		String currentValue = "" + account4.isClosed();
		assertTrue("Wrong account status (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#accountIsClosed()}.
	 */
	@Test
	public void testAccountIsClosed() 
	{
		String result = "false";
		String currentValue = "" + account4.isClosed();
		assertTrue("Wrong account status (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#toString()}.
	 */
	@Test
	public void testToString() 
	{
		String result = "3,100.0,1,2";
		String currentValue = "" + account4;
		assertTrue("Wrong Output (Is: " + currentValue + 
				"; Should be " + result + ")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.Account#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals1() 
	{
		Boolean result =! account4.equals(null);
		assertTrue("Wrong equal result (Is: " + result +
				"; Should be false)",result);
	}
	
	/**
	 * Test method for {@link asePackage.Account#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals2() 
	{
		Boolean result =! account4.equals(account3);
		assertTrue("Wrong equal result (Is: " + result + 
				"; Should be false)",result);
	}
	
	/**
	 * Test method for {@link asePackage.Account#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals3() 
	{
		Boolean result = account4.equals(account4);
		assertTrue("Wrong equal result (Is: " + result + 
				"; Should be true)",result);
	}
	
	/**
	 * Test method for {@link asePackage.Account#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals4() 
	{
		Boolean result =! account4.equals(3);
		assertTrue("Wrong equal result (Is: " + result + 
				"; Should be false)",result);
	}
	
	/**
	 * Test method for {@link asePackage.Account#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals5() 
	{
		Boolean result = account2.equals(new Account(1, new Customer("Mohammed", "Ali", 1)));
		assertTrue("Wrong equal result (Is: " + result + 
				"; Should be true)",result);
	}
}