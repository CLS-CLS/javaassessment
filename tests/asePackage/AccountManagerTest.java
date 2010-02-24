package asePackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ioan
 *
 */
public class AccountManagerTest {
	private AccountManager manager1;
	private AccountManager manager2;
	private ArrayList<Account> accountTest;
	private ArrayList<Customer> cust;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		accountTest=new ArrayList<Account>();
		accountTest.add(new Account(1));
		accountTest.add(new Account(2));
		
		manager1=new AccountManager();
		manager2=new AccountManager(accountTest);
		
		cust= new ArrayList<Customer>();
		cust.add(new Customer("Ioan", "Covalcic",1));
		cust.add(new Customer("Ioan2", "Covalcic2",2));
		
	}

	/**
	 * Test method for {@link asePackage.AccountManager#AccountManager()}.
	 */
	@Test
	public void testAccountManager1() {
		Integer result=100000;
		Integer currentValue=manager1.getAvaibleAccountId();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.AccountManager#AccountManager(java.util.ArrayList)}.
	 */
	@Test
	public void testAccountManager2() {
		Integer result=3;
		Integer currentValue=manager2.getAvaibleAccountId();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.AccountManager#addAccount(asePackage.Customer)}.
	 */
	@Test
	public void testAddAccount() {
		Integer result=1;
		manager1.addAccount(new Customer("test", "test", 5));
		Integer currentValue=manager1.getAccountList().size();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testAddAcounts() {
		Integer result=2;
		manager1.addAcounts(accountTest);
		Integer currentValue=manager1.getAccountList().size();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.AccountManager#deleteAccount(asePackage.Account)}.
	 */
	@Test
	public void testDeleteAccount() {
		Integer result=0;
		manager2.deleteAccount(new Account(1));
		manager2.deleteAccount(new Account(2,cust));
		Integer currentValue=manager2.getAccountList().size();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.AccountManager#getAvaibleAccountId()}.
	 */
	@Test
	public void testGetAvaibleAccountId() {
		Integer result=3;
		Integer currentValue=manager2.getAvaibleAccountId();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	
	/**
	 * Test method for {@link asePackage.AccountManager#getAccountList()}.
	 */
	@Test
	public void testGetAccountList() {
		Integer result=2;
		Integer currentValue=manager2.getAccountList().size();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
}
