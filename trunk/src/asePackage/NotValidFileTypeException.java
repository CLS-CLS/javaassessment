package asePackage;

public class NotValidFileTypeException extends Exception {
    private static final long serialVersionUID = 1L;
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
