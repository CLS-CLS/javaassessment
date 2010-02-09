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
	private static void saveStringToFile(String data, String fileName) {
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
	    return returnList;
	}
	
	
	public static ArrayList<String[]> loadAccounts(String fileName) throws FileNotFoundException{
		ArrayList<String[]> accounts = loadFile(fileName);
		return accounts;
	}
	
	public static ArrayList<String[]> loadCustomers(String fileName) throws FileNotFoundException{
		ArrayList<String[]> customers = loadFile(fileName);
		return customers;
	}

}
