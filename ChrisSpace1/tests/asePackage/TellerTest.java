package asePackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class TellerTest {
	
	
	AccountManager am;
	QueueManager qm;
	Customer customer;
	Transaction trans;
	Teller teller;
			
	@Before
	public void setUp() throws Exception{
		customer = new Customer("Chris","Lytsikas",1);
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		customerList.add(customer);
		Account account = new Account(2, 1000, customerList);
		ArrayList<Account> accountList = new ArrayList<Account>();
		accountList.add(account);
		customer.addAccount(account);
		am = new AccountManager(accountList);
		trans = new Transaction(Transaction.OPEN, account, 100);
		Queue queue = new Queue(customer, trans, 1);
		ArrayList<Queue> queueList = new ArrayList<Queue>();
		queueList.add(queue);
		qm = new QueueManager(queueList,50);
	}
	
	public void setUp2() throws Exception{
		customer = new Customer("Chris","Lytsikas",1);
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		customerList.add(customer);
		Account account = new Account(2, 1000, customerList);
		ArrayList<Account> accountList = new ArrayList<Account>();
		accountList.add(account);
		customer.addAccount(account);
		am = new AccountManager(accountList);
		trans = new Transaction(Transaction.CLOSE, account,0);
		Queue queue = new Queue(customer, trans, 1);
		ArrayList<Queue> queueList = new ArrayList<Queue>();
		queueList.add(queue);
		qm = new QueueManager(queueList,50);
	}
		
	
	
	@Test
	public void doTransaction() throws Exception{
		
		//test the OPEN transaction
		teller = new Teller(qm, am, new Log());
		assertEquals(customer.getFirstName(), "Chris");
		assertEquals(1,customer.getNumberOfAccounts());
		teller.getNextCustomer();
		teller.doTransaction();
		assertEquals(2, customer.getNumberOfAccounts());
		assertTrue(100 == customer.getAccountList().get(1).getBalance());
		
		//test CLOSE transaction;
		setUp2();
		teller = new Teller(qm, am, new Log());
		assertEquals(customer.getFirstName(), "Chris");
		assertEquals(1,customer.getNumberOfAccounts());
		teller.getNextCustomer();
		teller.doTransaction();
		assertEquals(0, customer.getNumberOfAccounts());
		
	}

}
