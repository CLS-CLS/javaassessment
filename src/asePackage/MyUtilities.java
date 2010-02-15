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
	
	public static ArrayList<Account> loadAccounts(String fileName,ArrayList<Customer> customers) throws FileNotFoundException,
	NotValidFileTypeException{
		ArrayList<String[]> data = loadFile(fileName);
		ArrayList<Account> accounts = new ArrayList<Account>();
		for(String[] str :data){
			if (str.length!=4)throw new NotValidFileTypeException("Acccount");
			try{
				int accountID = Integer.parseInt(str[0]);
				double balance = Double.parseDouble(str[1]);
				int customer1ID = Integer.parseInt(str[2]);
				int customer2ID = Integer.parseInt(str[3]);
				Customer customer1 = findCustomerFromID(customer1ID,customers);
				Customer customer2 = findCustomerFromID(customer2ID,customers);
				ArrayList<Customer> ownerList = new ArrayList<Customer>();
				ownerList.add(customer1); 
				ownerList.add(customer2);
				Account account = new Account(accountID, balance, ownerList);
				accounts.add(account);
			}
			catch(NumberFormatException mfe){
				throw new NotValidFileTypeException("Customer");
			}
		}
		return accounts;
	}
	
	
	

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
		return customers;
	}
	
	

}
