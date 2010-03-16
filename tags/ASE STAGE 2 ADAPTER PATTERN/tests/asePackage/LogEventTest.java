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
	private LogEvent event3;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		event1=new LogEvent();
		event3=new LogEvent(3,new Customer("Chris", "Lytsikas", 1), new Transaction(Transaction.OPEN, new Account(), 100), "Fail", "errormessage");
	}

	/**
	 * Test method for {@link asePackage.LogEvent#LogEvent()}.
	 */
	@Test
	public void testLogEventConstructor() {
		String result="";
		String currentValue=""+event1;
		assertTrue("Wrong Output (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getQueueNumber()}.
	 */
	@Test
	public void testGetQueueNumber() {
		Integer result=3;
		Integer currentValue=event3.getQueueNumber();
		assertTrue("Wrong queue number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getTransactionType()}.
	 */
	@Test
	public void testGetTransactionType() {
		String result="open";
		String currentValue=event3.getTransactionType();
		assertTrue("Wrong transaction type (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getCustomerID()}.
	 */
	@Test
	public void testGetCustomerID() {
		Integer result=1;
		Integer currentValue=event3.getCustomerID();
		assertTrue("Wrong customer id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getAccountID()}.
	 */
	@Test
	public void testGetAccountID() {
		Integer result=0;
		Integer currentValue=event3.getAccountID();
		assertTrue("Wrong account id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getTellerID()}.
	 */
	@Test
	public void testGetTellerID() {
		Integer result=1;
		Integer currentValue=event3.getTellerID();
		assertTrue("Wrong teller id (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getOldBalance()}.
	 */
	@Test
	public void testGetOldBalance() {
		Double result=0.0;
		Double currentValue=event3.getOldBalance();
		assertTrue("Wrong old balance (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getNewBalance()}.
	 */
	@Test
	public void testGetNewBalance() {
		Double result=0.0;
		Double currentValue=event3.getNewBalance();
		assertTrue("Wrong new balance (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Test method for {@link asePackage.LogEvent#getTransactionSum()}.
	 */
	@Test
	public void testGetTransactionSum() {
		Double result=100.0;
		Double currentValue=event3.getTransactionSum();
		assertTrue("Wrong sum (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
}
