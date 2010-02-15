package asePackage;

/**
 * @author Ioan
 * 
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class LogTest {
	private Log log;
	private Log log2;

	@Before
	public void setUp() throws Exception {
		ArrayList<LogEvent> logEvent=new ArrayList<LogEvent>();
		logEvent.add(new LogEvent(1, "open", 1, 1, 1, 0, 0));
		logEvent.add(new LogEvent(1, "deposit", 1, 1, 1, 0, 200));
		logEvent.add(new LogEvent(2, "deposit", 4, 1, 1, 200, 300));
		logEvent.add(new LogEvent(3, "open", 2, 2, 1, 0, 0));
		logEvent.add(new LogEvent(3, "deposit", 2, 2, 1, 0, 50));
		logEvent.add(new LogEvent(4, "withdrawal", 3, 2, 1, 50, 0));
		logEvent.add(new LogEvent(4, "close", 3, 2, 1, 0, 0));
		log=new Log(logEvent);
		log2=new Log();
	}

	@Test
	public void testGetProcessedCustomersNumber() {
		Integer result=4;
		Integer currentValue=log.getProcessedCustomersNumber();
		assertTrue("Wrong Total Customer Number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testGetDepositTotal() {
		Double result=350.0;
		Double currentValue=log.getDepositTotal();
		assertTrue("Wrong deposit total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testGetWithdrawTotal() {
		Double result=50.0;
		Double currentValue=log.getWithdrawalTotal();
		assertTrue("Wrong withdrawal total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testToString1() {
		String result="[QueueNo.: 1][Customer: 1, Account: 1, Transaction: open, Sum: 0.0][tellerID=1]\n"
					 +"[QueueNo.: 1][Customer: 1, Account: 1, Transaction: deposit, Sum: 200.0][tellerID=1]\n"
					 +"[QueueNo.: 2][Customer: 4, Account: 1, Transaction: deposit, Sum: 100.0][tellerID=1]\n"
					 +"[QueueNo.: 3][Customer: 2, Account: 2, Transaction: open, Sum: 0.0][tellerID=1]\n"
					 +"[QueueNo.: 3][Customer: 2, Account: 2, Transaction: deposit, Sum: 50.0][tellerID=1]\n"
					 +"[QueueNo.: 4][Customer: 3, Account: 2, Transaction: withdrawal, Sum: 50.0][tellerID=1]\n"
					 +"[QueueNo.: 4][Customer: 3, Account: 2, Transaction: close, Sum: 0.0][tellerID=1]\n";
		String currentValue=""+log;
		assertTrue("Wrong output log (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testToString2() {
		assertTrue("Wrong output log (Is: "+log2+"; Should be empty)",(""+log2).equals(""));
	}
}