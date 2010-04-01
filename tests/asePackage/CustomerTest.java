package asePackage;

import org.junit.*;
import static org.junit.Assert.*;
/**
 * @author Ioan
 *
 */

public class CustomerTest 
{

/**
 * tests the constructor, getters and setters
 */
	@Test
	public void customer()
	{
		Customer c1 = new Customer("Ali", "Alshbel", 1);
		assertEquals("Ali", c1.getFirstName());
		assertEquals("Alshbel", c1.getLastName());
		assertEquals(1, c1.getId());
		c1.setFirstName("Monica");
		c1.setLastName("Farrow");
		c1.setId(2);
		assertEquals("Monica", c1.getFirstName());
		assertEquals("Farrow", c1.getLastName());
		
		assertEquals(2, c1.getId());
	}
	
	@Test
	public void equals()
	{
		Customer c1 = new Customer("Ali", "Alshbel", 2);
		Customer c2 = new Customer("Monica","Alshbel",1);
		Customer c3 = new Customer("Ali", "Alshbel", 2);
		assertTrue(c1.equals(c1));
		assertFalse(c1.equals(c2));
		assertTrue(c1.equals(c3));
	}
}