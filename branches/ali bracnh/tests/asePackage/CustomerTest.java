package asePackage;

import org.junit.*;
import static org.junit.Assert.*;



public class CustomerTest {

	//test the constructor
	@Test
	public void customer(){
		Customer c1 = new Customer(1,"Christos Lytsikas");
		assertEquals("Christos Lytsikas", c1.getFullName());
		assertEquals(1, c1.getId());
	}
}


