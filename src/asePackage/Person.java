package asePackage;
/**
 * Person class holds details about a person 
 */
public class Person
{
	/**
	 * instance variables
	 */
	private String personFirstName;
	private String personLastName;

	/**
	 * constructor to create a person object with a first and last name
	 * @param personFirstName
	 * @param personLastName
	 */
	public Person (String personFirstName, String personLastName)
	{        
		this.personFirstName = personFirstName;
		this.personLastName = personLastName;
	}

	/**
	 * get method to get the first name of the person
	 * @return
	 */
	public String getPersonFirstName() 
	{
		return personFirstName;
	}

	/**
	 * get method to get the last name of the person
	 * @return
	 */
	public String getPersonLastName() 
	{
		return personLastName;
	}
	/**
	 * set method to modify the first name of the person
	 */
	public void setPersonFirstName(String personFirstName) 
	{
		this.personFirstName = personFirstName;
	}

	/**
	 * set method to modify the last name of the person
	 */
	public void setPersonLastName(String personLastName) 
	{
		this.personLastName = personLastName;
	}	
}