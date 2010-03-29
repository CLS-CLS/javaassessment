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
	Customer cust1;
	Customer cust2;

	@Before
	public void setUp() throws Exception {
		ArrayList<LogEvent> logEvent=new ArrayList<LogEvent>();
		cust1=new Customer("Ioan","Covalcic",1);
		cust2=new Customer("Ioan","Test",2);
		Account acc1=new Account(1,cust1);
		Account acc2=new Account(2,cust2);
		logEvent.add(new LogEvent(1,cust1,LogEvent.ENTERQUEUE));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.OPEN,acc1,100),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.DEPOSIT,acc1,100),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.WITHDRAWAL,acc1,100),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.CLOSE,acc1,0),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.VIEWBALANCE,acc1,0),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.CLOSE,acc1,0),LogEvent.FAIL,"Account not found"));
		logEvent.add(new LogEvent(2,cust2,LogEvent.ENTERQUEUE));
		logEvent.add(new LogEvent(2,1,cust2,new Transaction(Transaction.OPEN,acc2,300),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(2,1,cust2,new Transaction(Transaction.WITHDRAWAL,acc2,190),LogEvent.SUCCESS,"OK"));

		log=new Log(logEvent);
		log2=new Log();
	}

	@Test
	public void testAddLogEvent(){
		log.addLogEventJoinQueue(2, cust1, LogEvent.ENTERQUEUE); 
		Integer result=2;
		Integer currentValue=log.getProcessedCustomersNumber();
		assertTrue("Wrong Total Customer Number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	@Test
	public void testGetProcessedCustomersNumber() {
		Integer result=2;
		Integer currentValue=log.getProcessedCustomersNumber();
		assertTrue("Wrong Total Customer Number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testGetDepositTotal() {
		Double result=500.0;
		Double currentValue=log.getDepositTotal();
		assertTrue("Wrong deposit total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	@Test
	public void testGetWithdrawTotal() {
		Double result=290.0;
		Double currentValue=log.getWithdrawalTotal();
		assertTrue("Wrong withdrawal total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	
	@Test
	public void testGetCustomerWithdrawTotal() {
		Double result=190.0;
		Double currentValue=log.getCustomerWithdrawalTotal(cust2);
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
		int result=1536;
		String currentValue=""+log;
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",result==currentValue.length());
	}
}