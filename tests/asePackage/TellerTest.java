package asePackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;


public class TellerTest {
	
	
	AccountManager am;
	QueueManager qm;
	Customer customer;
	Customer customer2;
	ArrayList<Transaction> trans;
	Teller teller;
			
	@Before
	public void setUp() throws Exception{
		qm = QueueManager.getInstance();
		
		customer = new Customer("Chris","Lytsikas",1);
		customer2 = new Customer("Chris","Test",2);
		
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		customerList.add(customer);
		customerList.add(customer2);
		Account account = new Account(2, 1000, customerList);
		ArrayList<Account> accountList = new ArrayList<Account>();
		accountList.add(account);
		customer.addAccount(account);
		am = new AccountManager();
		am.addAcounts(accountList);
		trans = new ArrayList<Transaction>();
		trans.add(new Transaction(Transaction.OPEN, new Account(), 100, null));
		trans.add(new Transaction(Transaction.CLOSE, account, 100, null));
		Queue queue = new Queue(customer, trans, 1);
		ArrayList<Queue> queueList = new ArrayList<Queue>();
		queueList.add(queue);
	}
		
	@Test
	public void doTransaction() throws Exception{
		/*
		//test the OPEN transaction
		teller = new Teller(am, new Log(),1, null);
		assertEquals(customer.getFirstName(), "Chris");
		assertEquals(1,customer.getNumberOfAccounts());
		teller.getNextCustomer();
		teller.doTransaction();
		assertEquals(1, customer.getNumberOfAccounts());
		
		//test CLOSE transaction;
		teller = new Teller(am, new Log(),1, null);
		assertEquals(customer.getFirstName(), "Chris");
		assertEquals(1,customer.getNumberOfAccounts());
		teller.getNextCustomer();
		teller.doTransaction();
		assertEquals(0, customer.getNumberOfAccounts());
*/		
	}

}
