package asePackage;

public class NotValidFileTypeException extends Exception {
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
