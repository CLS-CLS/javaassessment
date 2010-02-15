package asePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;



import org.junit.*;
import static org.junit.Assert.*;

public class MyUtilitiesTest {
	
	@Test
	public void saveStringToFileANDloadCustomers() throws FileNotFoundException, NotValidFileTypeException{
		
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
		
		customersFile.delete();
		assertFalse(customersFile.exists());
		
	}
	
	@Test
	public void saveStringToFileAndLoadCustomers() throws FileNotFoundException, NotValidFileTypeException{
		File customersFile = new File("customers.txt");
		customersFile.delete();
		assertFalse(customersFile.exists());
		
		String []str1 = {"Chris", "Lytsikas", "1"};
		MyUtilities.saveStringToFile(str1, "customers.txt");
		ArrayList<Customer> customers = 
			MyUtilities.loadCustomers("customers.txt");
		assertEquals(1,customers.size());
		assertEquals(customers.get(0), new Customer("Chris","Lytsikas",1));
		customersFile.delete();
		assertFalse(customersFile.exists());
		
	}
	
	@Test
	public void loadAccounts() throws FileNotFoundException, NotValidFileTypeException{
		String customer1 = "Chris,Lytsikas,001";
		String customer2 = "Eleni,Sideri,002";
		String customer3 = "Padelis,Lytsikas,003";
		String strToSave = customer1+"\n"+customer2+"\n"+customer3;
		
		File custFile = new File("customers.txt");
		custFile.delete();
		assertFalse(custFile.exists());
		
		MyUtilities.saveStringToFile(strToSave, "customers.txt");
		
		String account1 = "001,250.00,001,002";
		String account2 = "002,500,002,003";
		strToSave = account1+"\n"+account2;
		
		File acaFile = new File("accounts.txt");
		acaFile.delete();
		assertFalse(acaFile.exists());
		
		MyUtilities.saveStringToFile(strToSave, "accounts.txt");
		ArrayList<Account> accounts =  MyUtilities.loadAccounts(
				"accounts.txt", MyUtilities.loadCustomers("customers.txt"));
		
		assertEquals(2, accounts.size());
		assertEquals("The owners of this aca should be 2",
				accounts.get(0).getOwnerList().size(),2);
		
		assertEquals("The owner of the second aca is Padels",
				accounts.get(1).getOwnerList().get(1).getFirstName(),"Padelis");
		
		acaFile.delete();
		custFile.delete();
		assertFalse(acaFile.exists());
		assertFalse(custFile.exists());
	}
	
	

}
