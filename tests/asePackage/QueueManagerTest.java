package asePackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ioan
 *
 */
public class QueueManagerTest {
	private QueueManager manager;
	
	/**
	 * Initialisation
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager=QueueManager.getInstance();
	}

	/**
	 * Verify initialisation by getting the next available queue number
	 * Test method for {@link asePackage.QueueManager#QueueManager()}.
	 */
	@Test
	public void testQueueManager() {
		Integer result=1;
		Integer currentValue=manager.getNextNumber();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Verify if a queue element is added with success in the queue manager
	 * Test method for {@link asePackage.QueueManager#addQueueElement(asePackage.Customer, java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddQueueElement() throws Exception {
		Boolean result=false;
		ArrayList<Transaction> transactionTest=new ArrayList<Transaction>();
		transactionTest.add(new Transaction("deposit", new Account(1), 150, null));
		manager.addQueueElement(new Customer("ioan","test",2), transactionTest);
		Boolean currentValue=manager.isQueueEmpty();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test the removal from the queue for a queue element
	 * Test method for {@link asePackage.QueueManager#removeQueueElement()}.
	 * @throws Exception 
	 */
	@Test
	public void testRemoveQueueElement() throws Exception {
		Boolean result=false;
		ArrayList<Transaction> transactionTest=new ArrayList<Transaction>();
		transactionTest.add(new Transaction("deposit", new Account(1), 150, null));
		manager.addQueueElement(new Customer("ioan","test",2), transactionTest);
		manager.removeQueueElement();
		Boolean currentValue=manager.isQueueEmpty();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Verify if the queue is empty. False because we use a single instance of queue manager
	 * Test method for {@link asePackage.QueueManager#isQueueEmpty()}.
	 */
	@Test
	public void testIsQueueEmpty() {
		Boolean result=false;
		Boolean currentValue=manager.isQueueEmpty();
		assertTrue("Wrong value (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	
	/**
	 * Verify if a customer is in the queue.
	 * Test method for {@link asePackage.QueueManager#containsCustomer()}.
	 */
	@Test
	public void testContainsCustomer() {
		Boolean result=true;
		Boolean currentValue=manager.containsCustomer(new Customer("ioan","test",2));
		assertTrue("Wrong value (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Verify the response from queueCustomerToString method
	 * Test method for {@link asePackage.QueueManager#containsCustomer()}.
	 */
	@Test
	public void testQueueCustomerToString() {
		String result="1) ioan,test,2\n";
		String currentValue=manager.queueCustomersToString();
		assertTrue("Wrong value (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
}
