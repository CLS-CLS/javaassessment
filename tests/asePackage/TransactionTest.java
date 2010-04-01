package asePackage;
/**
 * @author Ioan
 *
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;



import org.junit.Before;
import org.junit.Test;



public class TransactionTest {
	Customer cust1;
	Customer cust2;
	ArrayList<Customer> customers = new ArrayList<Customer>();
	Account aca;
	Account aca2;
	@Before
	public void setUp(){
		cust1 = new Customer("Chris","Lytsikas",1);
		cust2 = new Customer("Chris","Lytsikas",2);
		customers.add(cust1);
		customers.add(cust2);
		aca = new Account(1, 100, customers);
		aca2 = new Account(2, 200, customers);
	}
	
	//testing if running
	@Test
	public void genRandomTrans(){
		Transaction trns = Transaction.generateRandomTransaction(aca, aca2, new Random());
		assertEquals(aca.getId(), trns.getAccount().getId());
	}
	
	

}
