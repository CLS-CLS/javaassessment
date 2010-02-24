package asePackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Chris
 * Provides general load save methods as well as more specific load save methods
 * concerning account and customers records.
 */
public class MyUtilities<E> {
	
	/**
	 * Saves a given string to a given file
	 * @param data the string to be saved
	 * @param fileName the file where the string will be saved in
	 */
	public static void saveStringToFile(String data, String fileName) {
		File f = new File(fileName);
		
		try {
			BufferedWriter bw;
			bw = new BufferedWriter(new PrintWriter(f));
			try {
				bw.append(data);
			}finally{
				bw.close();
			}
		}
		catch (IOException ioe) {
			System.out.println("Error while saving file : ");
			ioe.printStackTrace();
		}
	}
	/**
	 * @deprecated 
	 * @param data
	 * @param fileName
	 */
	public static void saveStringToFile(String[] data,String fileName){
		File f = new File(fileName);
		String stringToAppend = new String();
		for (String str: data){
			stringToAppend +=str+",";
		}
		
		
		try {
			BufferedWriter bw;
			bw = new BufferedWriter(new PrintWriter(f));
			try {
				bw.append(stringToAppend);
			}finally{
				bw.close();
			}
		}
		catch (IOException ioe) {
			System.out.println("Error while saving file : ");
			ioe.printStackTrace();
		}
		
	}
	
	/**
	 * Loads data from the given file. The data is extracted as follows:
	 * each line of the file is divided in substrings separated by commas and saved
	 * in an arraylist.
	 * @param fileName the file name of the file that contains the data
	 * @return a list of strings containing the data. 
	 */
	private  static ArrayList<String[]> loadFile(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		ArrayList<String[]> returnList = new ArrayList<String[]>();
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){
			String currentLine  = sc.nextLine();
			String[] elements = currentLine.split(",");
			returnList.add(elements);
		}
		sc.close();
	    return returnList;
	}
	
	/**
	 * 
	 * @param fileName the filename that contains the account data
	 * @param customers the arraylist of all the customers
	 * @return an arraylist of all the accounts objects 
	 * @throws FileNotFoundException if the file does not exist
	 * @throws NotValidFileTypeException if the data is not accounts
	 */
	public static ArrayList<Account> loadAccounts(String fileName,ArrayList<Customer> customers) throws FileNotFoundException,
	NotValidFileTypeException{
		
		ArrayList<String[]> data = loadFile(fileName);
		ArrayList<Account> accounts = new ArrayList<Account>();
		ArrayList<Customer> ownerList = new ArrayList<Customer>();
		
		for(String[] str :data){
			if (str.length <=2 || str.length > 4)throw new NotValidFileTypeException("Acccount");
			
			try{
				int accountID = Integer.parseInt(str[0]);
				double balance = Double.parseDouble(str[1]);
				int customer1ID = Integer.parseInt(str[2]);
				int customer2ID;
				
				Customer customer1 = findCustomerFromID(customer1ID,customers);
				Customer customer2 = null;
				ownerList.add(customer1); 
				//if the string array is more than 3 this means that the account is owned
				//by 2 customers
				if(str.length > 3){
					customer2ID = Integer.parseInt(str[3]);
					customer2 = findCustomerFromID(customer2ID,customers);
					ownerList.add(customer2);
				}
				Account account = new Account(accountID, balance, ownerList);
				accounts.add(account);
			}
			catch(NumberFormatException mfe){
				throw new NotValidFileTypeException("Customer");
			}
		}
		//if the there are not loaded accounts then the file is empty so it throws not
		//validFileTypeException
		if (accounts.size()==0) throw new NotValidFileTypeException("Accounts");
		return accounts;
	}
	
	
	/**
	 * finds the customer with the given customerID
	 * @param customerId 
	 * @param customers the list of the customers will search into
	 * @return the customer with specific customerID ,null if there is not one.
	 */

	private static Customer findCustomerFromID(int customerId,
			ArrayList<Customer> customers) {
		for (Customer customer:customers){
			if(customer.getId() == customerId)return customer;
		}
		return null;
	}
     
	
	
	public static ArrayList<Customer> loadCustomers(String fileName) throws FileNotFoundException,
	NotValidFileTypeException{
		ArrayList<String[]> data = loadFile(fileName);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		for(String[] str :data){
			if (str.length!=3)throw new NotValidFileTypeException("Customer");
			try{
				Customer customer = new Customer(str[0], str[1], Integer.parseInt(str[2]));
				customers.add(customer);
			}
			catch(NumberFormatException mfe){
				throw new NotValidFileTypeException("Customer");
			}
		}
		if (customers.size()==0) throw new NotValidFileTypeException("Customer");
		return customers;
	}
	
	
	public static void saveCustomersToFile(ArrayList<Customer> customers, String fileName) {
		String result="";
		for(int i=0; i<customers.size(); i++) {
			result+=customers.get(i)+"\n";
		}
		saveStringToFile(result, fileName);
	}
	
	
	public static void saveAccountsToFile(AccountManager am, String fileName) {
		String result="";
		for(int i=0; i<am.getAccountList().size(); i++) {
			result+=am.getAccountList().get(i)+"\n";
		}
		saveStringToFile(result, fileName);
	}

}
