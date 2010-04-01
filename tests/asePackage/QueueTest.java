/**
 * 
 */
package asePackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ioan
 *
 */
public class QueueTest {
	private Queue queue1;
	private Queue queue2;
	private Queue queue3;
	
	/**
	 * Initialisation
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ArrayList<Transaction> transactionTest=new ArrayList<Transaction>();
		transactionTest.add(new Transaction("deposit", new Account(1), 150, null));
		transactionTest.add(new Transaction("withdrawal", new Account(2), 100, null));
		
		queue1=new Queue();
		queue2=new Queue(new Customer("Ioan", "Eu", 1),new Transaction("deposit", new Account(2), 100, null),1);
		queue3=new Queue(new Customer("Ioan", "Eu", 1),transactionTest,2);
	}

	/**
	 * Tests if the initialisation of the current queue number as a test for initialisation
	 * Test method for {@link asePackage.Queue#Queue()}.
	 */
	@Test
	public void testQueue1() {
		Integer result=0;
		Integer currentValue=queue1.getQueueNumber();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	
	/**
	 * Tests if the initialisation of the current queue number as a test for initialisation
	 * Test method for {@link asePackage.Queue#Queue(asePackage.Customer, asePackage.Transaction, int)}.
	 */
	@Test
	public void testQueue2() {
		Integer result=1;
		Integer currentValue=queue2.getQueueNumber();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	
	/**
	 * Tests if the initialisation of the current queue number as a test for initialisation
	 * Test method for {@link asePackage.Queue#Queue(asePackage.Customer, java.util.ArrayList, int)}.
	 */
	@Test
	public void testQueue3() {
		Integer result=2;
		Integer currentValue=queue3.getQueueNumber();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	
	/**
	 * Tests the get value for a customer
	 * Test method for {@link asePackage.Queue#getCustomer()}.
	 */
	@Test
	public void testGetCustomer() {
		String result="Ioan";
		String currentValue=""+queue2.getCustomer().getFirstName();
		assertTrue("Wrong first name (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Tests get transaction list for a customer.
	 * Test method for {@link asePackage.Queue#getTransactionList()}.
	 */
	@Test
	public void testGetTransactionList() {
		Integer result=2;
		Integer currentValue=queue3.getTransactionList().size();
		assertTrue("Wrong transactions number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test if a queue number is return corect
	 * Test method for {@link asePackage.Queue#getQueueNumber()}.
	 */
	@Test
	public void testGetQueueNumber() {
		Integer result=1;
		Integer currentValue=queue2.getQueueNumber();
		assertTrue("Wrong number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

}
