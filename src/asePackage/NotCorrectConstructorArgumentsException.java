package asePackage;

public class NotCorrectConstructorArgumentsException  extends Exception{
	private static final long serialVersionUID = 1L;
	String[] str;
	
	public NotCorrectConstructorArgumentsException(String[] str){
		this.str =  new String[str.length];
	}

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
