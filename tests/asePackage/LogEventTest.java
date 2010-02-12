package asePackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ioan
 *
 */
public class LogEventTest {

	private LogEvent event1;
	private LogEvent event2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		event1=new LogEvent();
		event2=new LogEvent(2, "deposit", 4, 1, 1, 200, 300);
	}

	/**
	 * Test method for {@link asePackage.LogEvent#LogEvent()}.
	 */
	@Test
	public void testLogEventConstructor() {
		String result="[QueueNo.: 0][Customer: 0, Account: 0, Transaction: null, Sum: 0.0][tellerID=0]";
		String currentValue=""+event1;
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getQueueNumber()}.
	 */
	@Test
	public void testGetQueueNumber() {
		Integer result=2;
		Integer currentValue=event2.getQueueNumber();
		assertTrue("Wrong queue number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getTransactionType()}.
	 */
	@Test
	public void testGetTransactionType() {
		String result="deposit";
		String currentValue=event2.getTransactionType();
		assertTrue("Wrong transaction type (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getCustomerID()}.
	 */
	@Test
	public void testGetCustomerID() {
		Integer result=4;
		Integer currentValue=event2.getCustomerID();
		assertTrue("Wrong customer id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getAccountID()}.
	 */
	@Test
	public void testGetAccountID() {
		Integer result=1;
		Integer currentValue=event2.getAccountID();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getTellerID()}.
	 */
	@Test
	public void testGetTellerID() {
		Integer result=1;
		Integer currentValue=event2.getTellerID();
		assertTrue("Wrong teller id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getOldBalance()}.
	 */
	@Test
	public void testGetOldBalance() {
		Double result=200.0;
		Double currentValue=event2.getOldBalance();
		assertTrue("Wrong old balance (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getNewBalance()}.
	 */
	@Test
	public void testGetNewBalance() {
		Double result=300.0;
		Double currentValue=event2.getNewBalance();
		assertTrue("Wrong new balance (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getTransactionSum()}.
	 */
	@Test
	public void testGetTransactionSum() {
		Double result=100.0;
		Double currentValue=event2.getTransactionSum();
		assertTrue("Wrong sum (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#toString()}.
	 */
	@Test
	public void testToString() {
		String result="[QueueNo.: 2][Customer: 4, Account: 1, Transaction: deposit, Sum: 100.0][tellerID=1]";
		String currentValue=""+event2;
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

}
