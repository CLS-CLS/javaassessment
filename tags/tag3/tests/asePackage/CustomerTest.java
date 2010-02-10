package asePackage;

import org.junit.*;
import static org.junit.Assert.*;



public class CustomerTest {

	//test the constructor and getters setters
	@Test
	public void customer(){
		Customer c1 = new Customer("Chris", "Lytsikas", 1);
		assertEquals("Chris", c1.getFirstName());
		assertEquals("Lytsikas", c1.getLastName());
		assertEquals(1, c1.getId());
		c1.setFirstName("Alex");
		c1.setLastName("Uknown");
		c1.setId(2);
		assertEquals("Alex", c1.getFirstName());
		assertEquals("Uknown", c1.getLastName());
		
		assertEquals(2, c1.getId());
		
		
		
	}
}


