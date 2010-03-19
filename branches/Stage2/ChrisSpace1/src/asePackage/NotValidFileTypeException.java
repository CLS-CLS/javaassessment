package asePackage;

/**
 * 
 * @author Chris
 * It is used by the loadCustomers , loadAccounts methods in
 * the class MyUtilities. It is thrown when the files to be loaded
 * does not contain the correct data
 * 
 */
@SuppressWarnings("serial")
public class NotValidFileTypeException extends Exception {
    /**
     *  the message given from the method that throws the
     *  exception
     */
	String typeOfObject;
	
    public NotValidFileTypeException(){
    	super();
    }
    
    public NotValidFileTypeException(String string) {
    	super();
    	typeOfObject = string;
	}

	public String getTypeOfObject() {
		return typeOfObject;
	}
    
 }
