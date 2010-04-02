package asePackage;
/**
 * Can be used by the constructor when the arguments they are provided does not match
 * the expected .
 */
@SuppressWarnings("serial")
public class NotCorrectConstructorArgumentsException  extends Exception{
	
	String[] str;  //the message will be displayed
	
	public NotCorrectConstructorArgumentsException(String[] str){
		this.str =  new String[str.length];
	}
    
	/**
	 * prints the stack trace and a custom message
	 */
	@Override
	public void printStackTrace() {
		super.printStackTrace();
		StringBuilder strb = new StringBuilder();
		for (String s : str){
			strb.append(s);
			strb.append(" , ");
		}
		System.out.println("The arguments " + strb.toString()+ "are not valid" );
	}
	
	
	
	

}
