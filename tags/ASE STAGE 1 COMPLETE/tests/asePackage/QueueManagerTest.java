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
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager=new QueueManager();
	}

	/**
	 * Test method for {@link asePackage.QueueManager#QueueManager()}.
	 */
	@Test
	public void testQueueManager() {
		Integer result=1;
		Integer currentValue=manager.getNextNumber();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.QueueManager#addQueueElement(asePackage.Customer, java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddQueueElement() throws Exception {
		Boolean result=false;
		ArrayList<Transaction> transactionTest=new ArrayList<Transaction>();
		transactionTest.add(new Transaction("deposit", new Account(1), 150));
		manager.addQueueElement(new Customer("ioan","test",2), transactionTest);
		Boolean currentValue=manager.isQueueEmpty();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.QueueManager#removeQueueElement()}.
	 * @throws Exception 
	 */
	@Test
	public void testRemoveQueueElement() throws Exception {
		Boolean result=true;
		ArrayList<Transaction> transactionTest=new ArrayList<Transaction>();
		transactionTest.add(new Transaction("deposit", new Account(1), 150));
		manager.addQueueElement(new Customer("ioan","test",2), transactionTest);
		manager.removeQueueElement();
		Boolean currentValue=manager.isQueueEmpty();
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.QueueManager#isQueueEmpty()}.
	 */
	@Test
	public void testIsQueueEmpty() {
		Boolean result=true;
		Boolean currentValue=manager.isQueueEmpty();
		assertTrue("Wrong value (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

}
