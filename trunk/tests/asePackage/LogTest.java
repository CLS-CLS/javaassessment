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
		Customer cust1=new Customer("Ioan","Covalcic",1);
		Account acc1=new Account(1,cust1);
		logEvent.add(new LogEvent(1,cust1,LogEvent.ENTERQUEUE));
		logEvent.add(new LogEvent(1,cust1,new Transaction(Transaction.OPEN,acc1,100),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,cust1,new Transaction(Transaction.DEPOSIT,acc1,100),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,cust1,new Transaction(Transaction.WITHDRAWAL,acc1,100),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,cust1,new Transaction(Transaction.CLOSE,acc1,0),LogEvent.SUCCESS,"OK"));
		log=new Log(logEvent);
		log2=new Log();
	}

	@Test
	public void testGetProcessedCustomersNumber() {
		Integer result=1;
		Integer currentValue=log.getProcessedCustomersNumber();
		assertTrue("Wrong Total Customer Number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testGetDepositTotal() {
		Double result=200.0;
		Double currentValue=log.getDepositTotal();
		assertTrue("Wrong deposit total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testGetWithdrawTotal() {
		Double result=100.0;
		Double currentValue=log.getWithdrawalTotal();
		assertTrue("Wrong withdrawal total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testToString1() {
		int result=116;
		String currentValue=""+log2;
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",currentValue.length()==result);
	}

	@Test
	public void testToString2() {
		int result=868;
		String currentValue=""+log;
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",result==currentValue.length());
	}
}