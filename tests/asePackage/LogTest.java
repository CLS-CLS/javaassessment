package asePackage;

/**
 * @author Ioan
 * 
 */

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class LogTest {
	private Log log;
	private Log log2;
	Customer cust1;
	Customer cust2;
	Class auxClass;

	/**
	 * Initialisation
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ArrayList<LogEvent> logEvent=new ArrayList<LogEvent>();
		cust1=new Customer("Ioan","Covalcic",1);
		cust2=new Customer("Ioan","Test",2);
		Account acc1=new Account(1,cust1);
		Account acc2=new Account(2,cust2);
		logEvent.add(new LogEvent(1,cust1,LogEvent.ENTERQUEUE));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.OPEN,acc1,100, acc2),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.DEPOSIT,acc1,100, acc2),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.WITHDRAWAL,acc1,100, acc2),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.CLOSE,acc1,0, acc2),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.VIEWBALANCE,acc1,0, acc2),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(1,1,cust1,new Transaction(Transaction.CLOSE,acc1,0, acc2),LogEvent.FAIL,"Account not found"));
		logEvent.add(new LogEvent(2,cust2,LogEvent.ENTERQUEUE));
		logEvent.add(new LogEvent(2,1,cust2,new Transaction(Transaction.OPEN,acc2,300, null),LogEvent.SUCCESS,"OK"));
		logEvent.add(new LogEvent(2,1,cust2,new Transaction(Transaction.WITHDRAWAL,acc2,190, acc2),LogEvent.SUCCESS,"OK"));

		log=new Log(logEvent);
		log2=new Log();
	}

	/**
	 * Testing the constructor
	 * @throws Exception
	 */
	@Test
	public void testAddLogEvent() throws Exception{
		log.addLogEventJoinQueue(2, cust1, LogEvent.ENTERQUEUE); 
		Integer result=2;
		
		Integer currentValue=log.getProcessedCustomersNumber();
		assertTrue("Wrong Total Customer Number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	/**
	 * Return the number of processed customers
	 */
	@Test
	public void testGetProcessedCustomersNumber() {
		Integer result=2;
		Integer currentValue=log.getProcessedCustomersNumber();
		assertTrue("Wrong Total Customer Number (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Testing a private method so we can now if it works well.
	 * @throws Exception
	 */
	@Test
	public void testGetDepositTotalCustomer() throws Exception {
		Method aux;
		Double result=200.0;
		auxClass=log.getClass();
		aux = auxClass.getDeclaredMethod("getDepositTotalCustomer",new Class[]{int.class});
		aux.setAccessible(true);

		Object ret = aux.invoke(log, 1);
		

		Double currentValue=(Double) ret;
		assertTrue("Wrong deposit total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}

	/**
	 * Testing a private method to see if it returns the correct value
	 * @throws Exception
	 */
	@Test
	public void testGetWithdrawTotalCustomer() throws Exception {
		Method aux;
		Double result=100.0;
		auxClass=log.getClass();
		aux = auxClass.getDeclaredMethod("getWithdrawTotalCustomer",new Class[]{int.class});
		aux.setAccessible(true);

		Object ret = aux.invoke(log, 1);
		

		Double currentValue=(Double) ret;
		assertTrue("Wrong withdrawal total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	
	/**
	 * Testing the customerWithdrawTotal which returns the total withdraw for the customer
	 */
	@Test
	public void testGetCustomerWithdrawTotal() {
		Double result=190.0;
		Double currentValue=log.getCustomerWithdrawalTotal(cust2);
		assertTrue("Wrong withdrawal total (Is: "+currentValue+"; Should be "+result+")",currentValue.equals(result));
	}
	/**
	 * Testing the toString method for different values
	 */
	@Test
	public void testToString1() {
		int result=151;
		String currentValue=""+log2;
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",currentValue.length()==result);
	}

	@Test
	public void testToString2() {
		int result=1033;
		String currentValue=""+log;
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",result==currentValue.length());
	}
	
	/**
	 * test Creation of the summary
	 */
	@Test
	public void testGetSummary() {
		int result=554;
		String currentValue=""+log.getSummary();
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",result==currentValue.length());
	}
	
	/**
	 * Test creation of tellers summary
	 */
	@Test
	public void testTellersSummary() {
		int result=180;
		String currentValue=""+log.tellersSummary();
		assertTrue("Wrong output log (Is: "+currentValue.length()+"; Should be "+result+")",result==currentValue.length());
	}
}