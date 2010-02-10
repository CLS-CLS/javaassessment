package asePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;



import org.junit.*;
import static org.junit.Assert.*;

public class MyUtilitiesTest {
	
	@Test
	public void saveStringToFile() throws FileNotFoundException, NotValidFileTypeException{
		
		//Test to check the save - load methods of customers
		
		//delete already existed file and check that it doesn't exist
		File customersFile = new File("customers.txt");
		customersFile.delete();
		assertEquals(false, customersFile.exists());
		
		String customerStr = "Chris,Lytsikas,1";
		String customerStr2 = customerStr+"\nAlex,Papadopoulos,002";
		MyUtilities.saveStringToFile(customerStr, "customers.txt");
		
		assertEquals(true, customersFile.exists());
		ArrayList<Customer> customers = 
			MyUtilities.loadCustomers("customers.txt");
		assertEquals(1,customers.size());
		assertEquals(customers.get(0), new Customer("Chris","Lytsikas",1));
		
		MyUtilities.saveStringToFile(customerStr2, "customers.txt");
		
		customers.clear();
		customers = MyUtilities.loadCustomers("customers.txt");
		
		assertEquals(2,customers.size());
		assertEquals(customers.get(0), new Customer("Chris","Lytsikas",1));
		assertEquals(customers.get(1), new Customer("Alex","Papadopoulos",2));
		
		
		
		
	}

}
